package com.szniloycoder.foodapp.Models;

public class FeaturedModel {

    String coupon;
    String imageUrl;
    String key;
    String title;


    public FeaturedModel() {

    }

    public FeaturedModel(String coupon2, String imageUrl2, String key2, String title2) {
        this.coupon = coupon2;
        this.imageUrl = imageUrl2;
        this.key = key2;
        this.title = title2;
    }

    public String getCoupon() {
        return this.coupon;
    }

    public void setCoupon(String coupon2) {
        this.coupon = coupon2;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public void setImageUrl(String imageUrl2) {
        this.imageUrl = imageUrl2;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key2) {
        this.key = key2;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title2) {
        this.title = title2;
    }
}