package com.example.easydelivery.model;

import com.google.firebase.Timestamp;

import java.util.ArrayList;

public class Order {
    private String userId;
    private ArrayList<Product> products;
    private Timestamp deliveryDate;
    private String deliveryAddress;
    private transient boolean isSelected= false;

    private boolean isAllocated = false;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }



    public Order(String userId, ArrayList<Product> products, Timestamp deliveryDate, String deliveryAddress) {
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

    public Timestamp getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Timestamp deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public boolean getIsAllocated() {
        return isAllocated;
    }

    public void setIsAllocated(boolean isAllocated) {
        this.isAllocated = isAllocated;
    }

}
