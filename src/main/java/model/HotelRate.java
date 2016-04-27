package model;

import java.util.List;

public class HotelRate {

    private Customer customerObj;
    private List<Rate> rateObj;

    public Customer getCustomerObj() {
        return customerObj;
    }

    public void setCustomerObj(Customer customerObj) {
        this.customerObj = customerObj;
    }

    public List<Rate> getRateObj() {
        return rateObj;
    }

    public void setRateObj(List<Rate> rateObj) {
        this.rateObj = rateObj;
    }

    @Override
    public String toString() {
        return "Rate For " + customerObj + "\n"+rateObj+"\n";
    }
}
