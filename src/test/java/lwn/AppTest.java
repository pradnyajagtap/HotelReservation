package lwn;

import junit.framework.TestCase;
import model.Hotel;

import java.util.Date;
import java.util.HashSet;

public class AppTest extends TestCase {

    public void testGetCheapestHotel() throws Exception {
        String inputCustomer, outputHotelName;
        HashSet<Date> inputDates;
        App appObj = new App();
        inputCustomer = "regular";
        inputDates = appObj.getDates("16Mar2009(mon),17Mar2009(tue),18Mar2009(wed)");
        appObj.setInput(inputCustomer, inputDates);
        outputHotelName = appObj.getCheapestHotel().getHotelName();
        assertEquals("Invalid Ouput", "SWN Star Hotel", outputHotelName);

        inputCustomer = "Regular";
        inputDates = appObj.getDates("20Mar2009(fri),21Mar2009(sat),22Mar2009(sun)");
        appObj.setInput(inputCustomer, inputDates);
        outputHotelName = appObj.getCheapestHotel().getHotelName();
        assertEquals("Invalid Ouput", "SWN Star Hotel", outputHotelName);

        inputCustomer = "Rewards";
        inputDates = appObj.getDates("26Mar2009(thu),27Mar2009(fri),28Mar2009(sat)");
        appObj.setInput(inputCustomer, inputDates);
        outputHotelName = appObj.getCheapestHotel().getHotelName();
        assertEquals("Invalid Ouput", "SWN Star Hotel", outputHotelName);

        inputCustomer = "rewards";
        inputDates = appObj.getDates("15Mar2009(sun),14Mar2009(sat)");
        appObj.setInput(inputCustomer, inputDates);
        outputHotelName = appObj.getCheapestHotel().getHotelName();
        assertEquals("Invalid Ouput", "SWN Deluxe Hotel", outputHotelName);

        inputCustomer = "non exist customer type";
        inputDates = appObj.getDates("15Mar2009(sun),14Mar2009(sat)");
        appObj.setInput(inputCustomer, inputDates);
        Hotel hotelObj = appObj.getCheapestHotel();
        assertNull(hotelObj);
    }
}