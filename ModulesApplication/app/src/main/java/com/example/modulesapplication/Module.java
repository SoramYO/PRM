package com.example.modulesapplication;

public class Module {
    private int imageResId;
    private String title;
    private String description;
    private String platform;

    public Module(int imageResId, String title, String description, String platform) {
        this.imageResId = imageResId;
        this.title = title;
        this.description = description;
        this.platform = platform;
    }

    public int getImageResId() {
        return imageResId;
    }

    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }
}
