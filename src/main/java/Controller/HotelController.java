package Controller;


import model.Hotel;
import model.HotelRate;
import model.Rate;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class HotelController {

    /**
     * Read Yaml file and parse it to generate Hotel List
     *
     * @param fileStr Name of file which contains hotel list details
     * @return Generated Hotel List
     */
    @SuppressWarnings("unchecked")
    public List<Hotel> getHotelList(String fileStr) {
        Yaml yamlObj;
        InputStream inputStreamObj = null;
        List<Hotel> hotelList = null;

        try {
            yamlObj = new Yaml();
            inputStreamObj = new FileInputStream(new File(fileStr));
            hotelList = (List<Hotel>) yamlObj.load(inputStreamObj);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStreamObj != null)
                try {
                    inputStreamObj.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        return hotelList;
    }

    /**
     * Displays generated hotel list details
     *
     * @param hotelList List to display
     */
    public void displayHotelList(List<Hotel> hotelList) {
        if (hotelList != null) {
            System.out.println("\nExisting Hotels Are ::  ");
            hotelList.forEach(System.out::println);
        }
    }


    /**
     * Depending upon the customer - customerType and reserve date list - allDates, it will calculate total rate for each hotel containing in hotel list - allHotels
     *
     * @param customerType Ex. Regular,Rewards Etc
     * @param allDates     Hash set of reserve dates
     * @param allHotels    Existing hotel list
     */
    public void setTotalRate(String customerType, HashSet<Date> allDates, List<Hotel> allHotels) {
        HashMap<String, Integer> dateTypeCounts = getCountOfAllDays(allDates);
        for (Hotel hotelObj : allHotels) {
            calculateTotalRate(hotelObj, customerType, dateTypeCounts);
        }
    }

    private HashMap<String, Integer> getCountOfAllDays(HashSet<Date> allDates) {
        List<String> dayTypeList = getAllDayTypeOfHotels();
        HashMap<String, Integer> dateTypeCounts = new HashMap<>();
        dayTypeList.forEach(dayType -> dateTypeCounts.put(dayType, 0));
        for (Date currentDate : allDates) {
            Calendar c = Calendar.getInstance();
            c.setTime(currentDate);
            int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
            if (dayOfWeek == Calendar.SUNDAY) {
                Integer current = dateTypeCounts.get("Discount");
                dateTypeCounts.put("Discount", current + 1);
            } else if (dayOfWeek >= Calendar.MONDAY && dayOfWeek <= Calendar.FRIDAY) {
                Integer current = dateTypeCounts.get("WeekDay");
                dateTypeCounts.put("WeekDay", current + 1);
            } else {
                Integer current = dateTypeCounts.get("WeekEnd");
                dateTypeCounts.put("WeekEnd", current + 1);
            }
        }
        return dateTypeCounts;

    }

    private List<String> getAllDayTypeOfHotels() {
        RateTypeController rateTypeControllerObj = new RateTypeController();
        return rateTypeControllerObj.getRateTypeList("rateTypes.yml");
    }


    /**
     * Calculate total rate for input Hotel, for specific customer with input total weekdays count and total weekends count
     *
     * @param hotelObj     Hotel for which want to calculate total rate
     * @param customerType customer type to get specific rate for that customer
     * @param dateTypeCounts Total count of days of particular date types
     */
    private void calculateTotalRate(Hotel hotelObj, String customerType, HashMap<String, Integer> dateTypeCounts) {
        HotelRate hr = hotelObj.getHotelRate().stream().filter(hotelRate -> hotelRate.getCustomerObj().getCustomerType().equalsIgnoreCase(customerType)).findFirst().orElse(null);
        if (hr != null) {
            dateTypeCounts.forEach((dayType, dayCount) ->
            {
                int rateValue = getSpecificRateForHotel(hr, dayType);
                int newRate = (hotelObj.getTotalRate()) + (rateValue * dayCount);
                hotelObj.setTotalRate(newRate);
            });
        } else {
            hotelObj.setTotalRate(-1);
        }
    }

    /**
     * From input hotelRate of hotel, will return rate value for input string Rate Type
     *
     * @param hr       all rates of specific hotel
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
     *
     * @param hotelList List of all hotels
     * @return cheapest hotel details
     */
    public Hotel getCheapHotel(List<Hotel> hotelList) {
        Hotel hotelObj = null;
        final Comparator<Hotel> rateComparator = (o1, o2) -> {
            if (o1.getTotalRate() != o2.getTotalRate())
                return Integer.compare(o1.getTotalRate(), o2.getTotalRate());
            else
                return Integer.compare(o2.getHotelRating(), o1.getHotelRating());
        };
        Optional<Hotel> optionalHotel = hotelList.stream().filter(hotel -> hotel.getTotalRate() > 0).min(rateComparator);
        if (optionalHotel.isPresent())
            hotelObj = optionalHotel.get();
        if (hotelObj != null && hotelObj.getTotalRate() != -1)
            return hotelObj;
        else
            return null;
    }
}