package lwn;

import junit.framework.TestCase;
import model.Hotel;

public class AppTest extends TestCase {

    public void testGetCheapestHotel() throws Exception {
        String inputStr,outputHotelName;
        App appObj = new App();
        inputStr= "regular:16Mar2009(mon),17Mar2009(tue),18Mar2009(wed)";
        appObj.setInput(inputStr);
        outputHotelName = appObj.getCheapestHotel().getHotelName();
        assertEquals("Invalid Ouput", "SWN Star Hotel", outputHotelName);

        inputStr= "Regular:20Mar2009(fri),21Mar2009(sat),22Mar2009(sun)";
        appObj.setInput(inputStr);
        outputHotelName = appObj.getCheapestHotel().getHotelName();
        assertEquals("Invalid Ouput", "SWN Star Hotel", outputHotelName);

        inputStr = "Rewards:26Mar2009(thu),27Mar2009(fri),28Mar2009(sat)";
        appObj.setInput(inputStr);
        outputHotelName = appObj.getCheapestHotel().getHotelName();
        assertEquals("Invalid Ouput", "SWN Star Hotel", outputHotelName);

        inputStr = "rewards:15Mar2009(sun),14Mar2009(sat)";
        appObj.setInput(inputStr);
        outputHotelName = appObj.getCheapestHotel().getHotelName();
        assertEquals("Invalid Ouput", "SWN Deluxe Hotel", outputHotelName);

        inputStr = "non exist customer type:15Mar2009(sun),14Mar2009(sat)";
        appObj.setInput(inputStr);
        Hotel hotelObj = appObj.getCheapestHotel();
        assertNull(hotelObj);
    }
}