package model;


import java.util.Date;
import java.util.HashSet;

public class ReserveHotel {
    private String customerType;
    private HashSet<Date> reserveDates;
    private Hotel cheapestHotel;

    public ReserveHotel(String customerType, HashSet<Date> reserveDates) {
        this.customerType = customerType;
        this.reserveDates = reserveDates;
    }

    public String getCustomerType() {
        return customerType;
    }

    public HashSet<Date> getReserveDates() {
        return reserveDates;
    }

    public Hotel getCheapestHotel() {
        return cheapestHotel;
    }

    public void setCheapestHotel(Hotel cheapestHotel) {
        this.cheapestHotel = cheapestHotel;
    }
}
