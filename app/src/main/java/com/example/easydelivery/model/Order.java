package com.example.easydelivery.model;

import java.util.ArrayList;

public class Order {
    private String userId;
    private ArrayList<Product> products;
    private String deliveryDate;
    private String deliveryAddress;


    private int status = 0;

    public Order(String userId, ArrayList<Product> products, String deliveryDate, String deliveryAddress) {
        this.userId = userId;
        this.products = products;
        this.deliveryDate = deliveryDate;
        this.deliveryAddress = deliveryAddress;
    }

    public Order() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}
