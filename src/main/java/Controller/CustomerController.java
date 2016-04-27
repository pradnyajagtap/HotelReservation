package Controller;


import model.Customer;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class CustomerController {

    @SuppressWarnings("unchecked")
    public List<Customer> getCustomerList(String fileStr) {
        Yaml yamlObj;
        InputStream inputStramObj = null;
        List<Customer> customerList = null;

        try {
            yamlObj = new Yaml(new Constructor(List.class));
            inputStramObj = new FileInputStream(new File(fileStr));
            customerList = (List<Customer>) yamlObj.load(inputStramObj);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStramObj != null)
                try {
                    inputStramObj.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        return customerList;
    }

    public Boolean isValidCustomer(List<Customer> customerList, String customerType) {
        Customer existingCustomer = customerList.stream().filter(customer -> customer.getCustomerType().equalsIgnoreCase(customerType)).findAny().orElse(null);
        if (existingCustomer != null)
            return true;
        else {
            displayCustomerList(customerList);
            return false;
        }
    }


    public void displayCustomerList(List<Customer> customerList) {
        if (customerList != null)
            System.out.println("\nExisting Customer Types Are ::  ");
            customerList.forEach(System.out::println);
    }

}
