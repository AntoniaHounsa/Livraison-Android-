package com.example.easydelivery.model;

import java.util.ArrayList;

public class Cart {
    long id;
    String userId;
    ArrayList<Product> products = new ArrayList<>();

    public Cart() {
    }

    public Cart(long id, String userId, ArrayList<Product> products) {
        this.id = id;
        this.userId = userId;
        this.products = products;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
}
