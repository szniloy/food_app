package com.szniloycoder.foodapp.Models;

import java.util.ArrayList;

public class FoodsModel {

    String catId;
    String foodCategory;
    String foodDeliveryTme;
    String foodDescription;
    String foodIngredients;
    String foodName;
    String foodPrice;
    ArrayList<String> imageUrls;
    String itemId;
    String ratings;
    ArrayList<String> sizes;

    public FoodsModel() {
    }

    public FoodsModel(String catId2, String foodCategory2, String foodDeliveryTme2, String foodDescription2, String foodIngredients2, String foodName2, String foodPrice2, ArrayList<String> imageUrls2, String itemId2, String ratings2, ArrayList<String> sizes2) {
        this.catId = catId2;
        this.foodCategory = foodCategory2;
        this.foodDeliveryTme = foodDeliveryTme2;
        this.foodDescription = foodDescription2;
        this.foodIngredients = foodIngredients2;
        this.foodName = foodName2;
        this.foodPrice = foodPrice2;
        this.imageUrls = imageUrls2;
        this.itemId = itemId2;
        this.ratings = ratings2;
        this.sizes = sizes2;
    }

    public String getCatId() {
        return this.catId;
    }

    public void setCatId(String catId2) {
        this.catId = catId2;
    }

    public String getFoodCategory() {
        return this.foodCategory;
    }

    public void setFoodCategory(String foodCategory2) {
        this.foodCategory = foodCategory2;
    }

    public String getFoodDeliveryTme() {
        return this.foodDeliveryTme;
    }

    public void setFoodDeliveryTme(String foodDeliveryTme2) {
        this.foodDeliveryTme = foodDeliveryTme2;
    }

    public String getFoodDescription() {
        return this.foodDescription;
    }

    public void setFoodDescription(String foodDescription2) {
        this.foodDescription = foodDescription2;
    }

    public String getFoodIngredients() {
        return this.foodIngredients;
    }

    public void setFoodIngredients(String foodIngredients2) {
        this.foodIngredients = foodIngredients2;
    }

    public String getFoodName() {
        return this.foodName;
    }

    public void setFoodName(String foodName2) {
        this.foodName = foodName2;
    }

    public String getFoodPrice() {
        return this.foodPrice;
    }

    public void setFoodPrice(String foodPrice2) {
        this.foodPrice = foodPrice2;
    }

    public ArrayList<String> getImageUrls() {
        return this.imageUrls;
    }

    public void setImageUrls(ArrayList<String> imageUrls2) {
        this.imageUrls = imageUrls2;
    }

    public String getItemId() {
        return this.itemId;
    }

    public void setItemId(String itemId2) {
        this.itemId = itemId2;
    }

    public String getRatings() {
        return this.ratings;
    }

    public void setRatings(String ratings2) {
        this.ratings = ratings2;
    }

    public ArrayList<String> getSizes() {
        return this.sizes;
    }

    public void setSizes(ArrayList<String> sizes2) {
        this.sizes = sizes2;
    }
}
