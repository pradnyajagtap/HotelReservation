package lwn;

import Controller.CustomerController;
import Controller.HotelController;
import model.Customer;
import model.Hotel;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;


public class App {
    private String customerType;
    private HashSet<Date> reserveDates;
    private Hotel cheapestHotel;

    /**
     * Start Point of application,which accepts user input of customer type and dates to reserve as a string
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        App appObj = new App();
        if (args.length > 0) {
            appObj.setInput(args[0]);
        } else {
            appObj.getInputFromConsole();
        }
        appObj.getCheapestHotel();
        appObj.displayResult();
    }

    /**
     * Gets input from user using scanner
     */
    private void getInputFromConsole() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Customer Type and Dates To Reserve [Format **** regular:15Mar2009(sun),14Mar2009(sat) ****]: ");
        String input = scanner.next();
        setInput(input);
    }

    /**
     * Check whether input string is in correct format or not and Converts input string to customer Type and reserve dates list
     *
     * @param inputStr
     */
    void setInput(String inputStr) {
        String inputArray[] = inputStr.split(":");
        if (inputArray.length > 1) {
            customerType = inputArray[0];
            reserveDates = getDates(inputArray[1]);
            if (reserveDates == null) {
                System.out.println("Invalid Date Input...Please Enter Again!!!!!!");
                getInputFromConsole();
            } else if (!checkValidCustomerType(customerType)) {
                System.out.println("\n**** "+customerType+ " Is Invalid Customer Type...Please Enter Again!!!!!!\n");
                getInputFromConsole();
            }
        } else {
            System.out.println("Invalid Input...Please Enter Again!!!!!!");
            getInputFromConsole();
        }
    }

    private boolean checkValidCustomerType(String customerType) {
        CustomerController customerConmtrollerObj = new CustomerController();
        List<Customer> customerList = customerConmtrollerObj.getCustomerList("customers.yml");
        return customerConmtrollerObj.isValidCustomer(customerList, customerType);

    }


    /**
     * Parse and Converts input date string to date list
     *
     * @param inputDate
     * @return Hashset of reserve dates if date string parsed correctly or else returns null
     */
    private HashSet<Date> getDates(String inputDate) {
        HashSet<Date> dateSet = new HashSet<>();
        String dateArray[] = inputDate.split(",");
        SimpleDateFormat formatterObj = new SimpleDateFormat("ddMMMyyyy(EEE)");
        for (String date : dateArray) {
            try {
                Date newDate = formatterObj.parse(date);
                dateSet.add(newDate);
            } catch (ParseException e) {
                return null;
            }
        }
        return dateSet;
    }

    /**
     * According to customer type and reserve dates, will find cheapest hotel for that user
     *
     * @return Cheapest Hotel details
     */
    Hotel getCheapestHotel() {
        if (reserveDates != null) {
            HotelController hotelControllerObj = new HotelController();
            List<Hotel> hotelList = hotelControllerObj.getHotelList("hotels.yml");
            hotelControllerObj.setTotalRate(customerType, reserveDates, hotelList);
            hotelControllerObj.displayHotelList(hotelList);
            cheapestHotel = hotelControllerObj.getCheapHotel(hotelList);
        }
        return cheapestHotel;
    }

    /**
     * Displays cheapest hotel details
     */
    private void displayResult() {
        System.out.println("\n***For Customer : " + customerType);
        System.out.println("***For dates : " + reserveDates);
        if (cheapestHotel != null)
            System.out.println("***Cheapest Hotel Details Are : " + cheapestHotel);
        else
            System.out.println("No Hotel Details Found!!!!!!!!!!!!!");
    }

}