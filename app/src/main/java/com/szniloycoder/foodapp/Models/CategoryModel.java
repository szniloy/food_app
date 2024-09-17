package com.szniloycoder.foodapp.Models;

public class CategoryModel {

    String Title;
    String id;
    String imageUrl;
    String key;

    public CategoryModel(String id2, String imageUrl2, String key2, String title) {
        this.id = id2;
        this.imageUrl = imageUrl2;
        this.key = key2;
        this.Title = title;
    }

    public CategoryModel() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id2) {
        this.id = id2;
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
        return this.Title;
    }

    public void setTitle(String title) {
        this.Title = title;
    }
}
