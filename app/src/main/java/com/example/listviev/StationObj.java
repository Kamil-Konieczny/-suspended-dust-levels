package com.example.listviev;

import java.io.Serializable;

public class StationObj implements Serializable {
    private String name;
    private Double data;
    private boolean wybor;
    public StationObj(String name, Double data) {
        this.name = name;
        this.data = data;
    }

    public boolean isWybor() {
        return wybor;
    }

    public void setWybor(boolean wybor) {
        this.wybor = wybor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getData() {
        return data;
    }

    public void setData(Double data) {
        this.data = data;
    }


    @Override
    public String toString() {
        return name  +
                "   stężenie: '" + data;
    }
}
