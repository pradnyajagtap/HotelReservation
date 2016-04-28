package Controller;


import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class RateTypeController {

    @SuppressWarnings("unchecked")
    public List<String> getRateTypeList(String fileStr) {
        Yaml yamlObj;
        InputStream inputStreamObj = null;
        List<String> rateTypeList = null;
        try {
            yamlObj = new Yaml(new Constructor(List.class));
            inputStreamObj = new FileInputStream(new File(fileStr));
            rateTypeList = (List<String>) yamlObj.load(inputStreamObj);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStreamObj != null)
                try {
                    inputStreamObj.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        return rateTypeList;
    }

}
