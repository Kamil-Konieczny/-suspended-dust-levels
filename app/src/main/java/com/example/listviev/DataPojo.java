
package com.example.listviev;

import java.util.List;

public class DataPojo {


    private String key;
    private List<Value> values = null;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public List<Value> getValues(int i) {
        return values;
    }

    public void setValues(List<Value> values) {
        this.values = values;
    }

}