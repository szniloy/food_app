package com.szniloycoder.foodapp.Models;

import java.util.ArrayList;

public class CartModel {

    String catId;
    String deliveryTime;
    String foodCategory;
    String foodDescription;
    ArrayList<String> foodImage;
    String foodIngredients;
    String foodName;
    String foodPrice;
    int foodQuantity;
    String itemId;
    String key;
    String ratings;
    String size;
    double totalPrice;

    public CartModel(String catId2, String deliveryTime2, String foodCategory2, String foodDescription2, ArrayList<String> foodImage2, String foodIngredients2, String foodName2, String foodPrice2, int foodQuantity2, String itemId2, String key2, String ratings2, String size2, double totalPrice2) {
        this.catId = catId2;
        this.deliveryTime = deliveryTime2;
        this.foodCategory = foodCategory2;
        this.foodDescription = foodDescription2;
        this.foodImage = foodImage2;
        this.foodIngredients = foodIngredients2;
        this.foodName = foodName2;
        this.foodPrice = foodPrice2;
        this.foodQuantity = foodQuantity2;
        this.itemId = itemId2;
        this.key = key2;
        this.ratings = ratings2;
        this.size = size2;
        this.totalPrice = totalPrice2;
    }

    public CartModel() {
    }

    public String getCatId() {
        return this.catId;
    }

    public void setCatId(String catId2) {
        this.catId = catId2;
    }

    public String getDeliveryTime() {
        return this.deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime2) {
        this.deliveryTime = deliveryTime2;
    }

    public String getFoodCategory() {
        return this.foodCategory;
    }

    public void setFoodCategory(String foodCategory2) {
        this.foodCategory = foodCategory2;
    }

    public String getFoodDescription() {
        return this.foodDescription;
    }

    public void setFoodDescription(String foodDescription2) {
        this.foodDescription = foodDescription2;
    }

    public ArrayList<String> getFoodImage() {
        return this.foodImage;
    }

    public void setFoodImage(ArrayList<String> foodImage2) {
        this.foodImage = foodImage2;
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

    public int getFoodQuantity() {
        return this.foodQuantity;
    }

    public void setFoodQuantity(int foodQuantity2) {
        this.foodQuantity = foodQuantity2;
    }

    public String getItemId() {
        return this.itemId;
    }

    public void setItemId(String itemId2) {
        this.itemId = itemId2;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key2) {
        this.key = key2;
    }

    public String getRatings() {
        return this.ratings;
    }

    public void setRatings(String ratings2) {
        this.ratings = ratings2;
    }

    public String getSize() {
        return this.size;
    }

    public void setSize(String size2) {
        this.size = size2;
    }

    public double getTotalPrice() {
        return this.totalPrice;
    }

    public void setTotalPrice(double totalPrice2) {
        this.totalPrice = totalPrice2;
    }
}
