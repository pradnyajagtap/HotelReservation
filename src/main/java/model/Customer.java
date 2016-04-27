package model;

public class Customer {
   private String customerType;

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    @Override
    public String toString() {
         return   "Customer Type : " + customerType;
    }


}
