package com.example.easydelivery.model;

public class Product {
    private String name;
    private long price;
    private String photo;


    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }



    public Product() {
    }

    public Product(String name, long price, String photo) {
        this.name = name;
        this.price = price;
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }
}
