package com.example.easydelivery.model;

import java.util.ArrayList;

public class Mission {

    private transient String missionId;
    private String driverEmail;
    private Status status= Status.EN_ATTENTE;
    private ArrayList<Order> route = new ArrayList<>();


    public String getMissionId() {
        return missionId;
    }

    public void setMissionId(String missionId) {
        this.missionId = missionId;
    }
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }





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



    public ArrayList<Order> getRoute() {
        return route;
    }

    public void setRoute(ArrayList<Order> route) {
        this.route = route;
    }


    public enum Status {
        EN_ATTENTE, ACCEPTEE, EN_COURS, REFUSEE, TERMINE
    }
}
