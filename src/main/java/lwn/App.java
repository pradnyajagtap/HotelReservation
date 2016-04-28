package lwn;

import Controller.CustomerController;
import Controller.HotelController;
import model.Customer;
import model.Hotel;
import model.ReserveHotel;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;


public class App {

    /**
     * Start Point of application,which accepts user input of customer type and dates to reserve as a string
     * @param args string array containing input string
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        App appObj = new App();
        ReserveHotel reserveHotelObj;
        if (args.length > 0) {
            reserveHotelObj = appObj.checkInput(args[0]);
        } else {
            reserveHotelObj = appObj.getInputFromConsole();
        }
        appObj.getCheapestHotel(reserveHotelObj);
        appObj.displayResult(reserveHotelObj);
    }

    /**
     *  Gets input from user using scanner object and returns input in the form of Reserve Hotel object
     * @return Input in format of reserve hotel object
     */
    private ReserveHotel getInputFromConsole() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Customer Type and Dates To Reserve [Format **** regular:15Mar2009(sun),14Mar2009(sat) ****]: ");
        String input = scanner.next();
        return checkInput(input);
    }

    /**
     * Check whether input string is in correct format or not and Converts input string to customer Type and reserve dates list
     * @param inputStr string containing customer type and reserve dates
     * @return Input in format of reserve hotel object
     */
    private ReserveHotel checkInput(String inputStr) {
        String inputArray[] = inputStr.split(":");
        if (inputArray.length > 1) {
            String inputCustomer = inputArray[0];
            HashSet<Date> inputDates = getDates(inputArray[1]);
            if (inputDates == null) {
                System.out.println("Invalid Date Input...Please Enter Again!!!!!!");
                getInputFromConsole();
            } else if (!checkValidCustomerType(inputCustomer)) {
                System.out.println("\n**** " + inputCustomer + " Is Invalid Customer Type...Please Enter Again!!!!!!\n");
                getInputFromConsole();
            } else {
                return setInput(inputCustomer, inputDates);
            }
        } else {
            System.out.println("Invalid Input...Please Enter Again!!!!!!");
            getInputFromConsole();
        }
        return null;
    }

    /**
     * Create reserve hotel object using input customer Type and reserve dates list
     *  @param inputCustomer string of customer type
     * @param inputDates  hash set containing reserve date list
     * @return Created reserve hotel object
     */
    ReserveHotel setInput(String inputCustomer, HashSet<Date> inputDates) {
        return (new ReserveHotel(inputCustomer, inputDates));
    }

    /**
     * Check whether user entered customer type in valid or not
     *
     * @param customerType String containing customer type to check
     * @return True if customer type is valid or else False
     */
    private boolean checkValidCustomerType(String customerType) {
        CustomerController customerControllerObj = new CustomerController();
        List<Customer> customerList = customerControllerObj.getCustomerList("customers.yml");
        return customerControllerObj.isValidCustomer(customerList, customerType);

    }


    /**
     * Parse and Converts input date string to date list
     * @param inputDate String to be converted in to list Ex.ddMMMyyyy(EEE),ddMMMyyyy(EEE),ddMMMyyyy(EEE)
     * @return Hash set of reserve dates if date string parsed correctly or else returns null
     */
    HashSet<Date> getDates(String inputDate) {
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
     * According to customer type and reserve dates, will find cheapest hotel for that ReserveHotel object
     * @param reserveHotelObj Details for which want to find cheapest detail
     */
    void getCheapestHotel(ReserveHotel reserveHotelObj) {
        if (reserveHotelObj.getReserveDates() != null) {
            HotelController hotelControllerObj = new HotelController();
            List<Hotel> hotelList = hotelControllerObj.getHotelList("hotels.yml");
            hotelControllerObj.setTotalRate(reserveHotelObj.getCustomerType(), reserveHotelObj.getReserveDates(), hotelList);
            hotelControllerObj.displayHotelList(hotelList);
            reserveHotelObj.setCheapestHotel(hotelControllerObj.getCheapHotel(hotelList));
        }
    }

    /**
     *  Displays cheapest hotel details of input ReserveHotel object
     * @param reserveHotelObj Input of reserve hotel for which want to display cheapest hotel details
     */
    private void displayResult(ReserveHotel reserveHotelObj) {
        System.out.println("\n***For Customer : " + reserveHotelObj.getCustomerType());
        System.out.println("***For dates : " + reserveHotelObj.getReserveDates());
        Hotel cheapestHotel = reserveHotelObj.getCheapestHotel();
        if (cheapestHotel != null)
            System.out.println("***Cheapest Hotel Details Are : " + cheapestHotel);
        else
            System.out.println("No Hotel Details Found!!!!!!!!!!!!!");
    }

}