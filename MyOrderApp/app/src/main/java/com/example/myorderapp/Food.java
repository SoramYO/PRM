package com.example.myorderapp;

public class Food {
    private String name;
    private String description;
    private String price;
    private int imageResId; // lưu id của ảnh drawable

    public Food(String name, String description, String price, int imageResId) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageResId = imageResId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getPrice() {
        return price;
    }

    public int getImageResId() {
        return imageResId;
    }
}
