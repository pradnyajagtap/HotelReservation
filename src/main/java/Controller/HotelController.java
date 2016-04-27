package Controller;


import model.Hotel;
import model.HotelRate;
import model.Rate;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class HotelController {

    /**
     * Read Yaml file and parse it to generate Hotel List
     * @param fileStr Name of file which contains hotel list details
     * @return Generated Hotel List
     */
    @SuppressWarnings("unchecked")
    public List<Hotel> getHotelList(String fileStr) {
        Yaml yamlObj;
        InputStream inputStramObj = null;
        List<Hotel> hotelList = null;

        try {
            yamlObj = new Yaml();
            inputStramObj = new FileInputStream(new File(fileStr));
            hotelList = (List<Hotel>) yamlObj.load(inputStramObj);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStramObj != null)
                try {
                    inputStramObj.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        return hotelList;
    }

    /**
     * Displays generated hotel list details
     * @param hotelList List to display
     */
    public void displayHotelList(List<Hotel> hotelList) {
        if (hotelList != null) {
            System.out.println("\nExisting Hotels Are ::  ");
            hotelList.forEach(System.out::println);
        }
    }


    /**
     * Depending upon the customer - customerType and reserve date list - allDates, it will calculate total rate for each hotel conainting in hotel list - allHotels
     * @param customerType Ex. Regular,Rewards Etc
     * @param allDates Hashset of reserve dates
     * @param allHotels Existing hotel list
     * @return Existing hotel list with their respective calculated total rates
     */
    public List<Hotel> setTotalRate(String customerType, HashSet<Date> allDates, List<Hotel> allHotels) {
        int totalWeekday = 0, totalWeekend = 0;
        for (Date currentDate : allDates) {
            Calendar c = Calendar.getInstance();
            c.setTime(currentDate);
            int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
            if (dayOfWeek >= Calendar.MONDAY && dayOfWeek <= Calendar.FRIDAY) {
                totalWeekday++;
            } else {
                totalWeekend++;
            }
        }
        for (Hotel hotelObj : allHotels) {
            int totalRate = calculateTotalRate(hotelObj, customerType, totalWeekday, totalWeekend);
            hotelObj.setTotalRate(totalRate);
        }
        return allHotels;
    }


    /**
     * Calculate total rate for input Hotel, for specific customer with input total weekdays count and toal weekends count
     * @param hotelObj Hotel for which want to calculate total rate
     * @param customerType customer type to get specific rate for that customer
     * @param totalWeekday count of weekdays
     * @param totalWeekend count of weekends
     * @return Total rate for Hotel for specific customer, if specific customer type not found , will return -1
     */
    private int calculateTotalRate(Hotel hotelObj, String customerType, int totalWeekday, int totalWeekend) {
        int weekDayRateValue, weekEndRateValue;
        HotelRate hr = hotelObj.getHotelRate().stream().filter(hotelRate -> hotelRate.getCustomerObj().getCustomerType().equalsIgnoreCase(customerType)).findFirst().orElse(null);
        if (hr != null) {
            weekDayRateValue = getSpecificRateForHotel(hr, "WeekDay");
            weekEndRateValue = getSpecificRateForHotel(hr, "WeekEnd");
            return totalWeekday * weekDayRateValue + totalWeekend * weekEndRateValue;
        } else {
            return -1;
        }
    }

    /**
     * From input hotelRate of hotel, will return rate value for input RateType
     * @param hr all rates of specific hotel
     * @param RateType string rate type of which you want rate value
     * @return rate value of input rate type of specific hotel
     */
    private int getSpecificRateForHotel(HotelRate hr, String RateType) {
        Rate rateObj = hr.getRateObj().stream().filter(rate -> rate.getRateType().equalsIgnoreCase(RateType)).findFirst().orElse(null);
        if (rateObj != null)
            return rateObj.getRateValue();
        else
            return 0;
    }

    /**
     * Compares the hotels by total rate, to get cheapest hotel, if total rate are same, will return hotel having highest rating
     * @param hotelList List of all hotels
     * @return cheapest hotel detials
     */
    public Hotel getCheapHotel(List<Hotel> hotelList) {
        Hotel hotelObj = null;
        final Comparator<Hotel> rateComparator = (o1, o2) ->  {
            if (o1.getTotalRate() == o2.getTotalRate())
                return o2.getHotelRating() - o1.getHotelRating();
            else
                return o1.getTotalRate() - o2.getTotalRate();
        };
        Optional<Hotel> optionalHotel = hotelList.stream().min(rateComparator);
        if (optionalHotel.isPresent())
            hotelObj = optionalHotel.get();
        if (hotelObj != null && hotelObj.getTotalRate() != -1)
            return hotelObj;
        else
            return null;
    }
}