package com.example.easydelivery.model;

import java.util.ArrayList;

public class Mission {
    private String driverEmail;
    private boolean isAcceptdeByDriver=false;
    private ArrayList<Order> route = new ArrayList<>();

    public Mission() {
    }

    public Mission(String driver, ArrayList<Order> route) {
        this.driverEmail = driver;
        this.route = route;
    }


    public String getDriverEmail() {
        return driverEmail;
    }

    public void setDriverEmail(String driverEmail) {
        this.driverEmail = driverEmail;
    }

    public boolean isAcceptdeByDriver() {
        return isAcceptdeByDriver;
    }

    public void setAcceptdeByDriver(boolean acceptdeByDriver) {
        isAcceptdeByDriver = acceptdeByDriver;
    }

    public ArrayList<Order> getRoute() {
        return route;
    }

    public void setRoute(ArrayList<Order> route) {
        this.route = route;
    }
}
