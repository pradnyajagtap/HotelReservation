package model;

import java.util.List;

public class Hotel {
    private int hotelRating;
    private String hotelName;
    private List<HotelRate> hotelRate;
    private int totalRate;

    public int getHotelRating() {
        return hotelRating;
    }

    public void setHotelRating(int hotelRating) {
        this.hotelRating = hotelRating;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public List<HotelRate> getHotelRate() {
        return hotelRate;
    }

    public void setHotelRate(List<HotelRate> hotelRate) {
        this.hotelRate = hotelRate;
    }

    public int getTotalRate() {
        return totalRate;
    }

    public void setTotalRate(int totalRate) {
        this.totalRate = totalRate;
    }

    @Override
    public String toString() {
        return "\nHotel Rating : " + hotelRating + "\nHotel Name : '" + hotelName + '\'' + "\nTotal Rate : " + totalRate + "\nHotel Rate : \n" + hotelRate;
    }
}
