package model;

public class Rate {

    private String rateType;
    private int rateValue;

    public String getRateType() {
        return rateType;
    }

    public void setRateType(String rateType) {
        this.rateType = rateType;
    }

    public int getRateValue() {
        return rateValue;
    }

    public void setRateValue(int rateValue) {
        this.rateValue = rateValue;
    }

    @Override
    public String toString() {
        return  rateType + " Rate : " + rateValue;
    }
}
