package com.example.easydelivery.service.entity;
public class RouteStep {
    private double distance; // Distance en kilomètres
    private double duration; // Durée en minutes

    public RouteStep(double distance, double duration) {
        this.distance = distance;
        this.duration = duration;
    }

    // Getters
    public double getDistance() {
        return distance;
    }

    public double getDuration() {
        return duration;
    }

}
