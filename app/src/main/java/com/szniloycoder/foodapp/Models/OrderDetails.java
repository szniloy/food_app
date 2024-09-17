package com.szniloycoder.foodapp.Models;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class OrderDetails implements Parcelable {


    public static final Creator<OrderDetails> CREATOR = new Creator<OrderDetails>() {
        public OrderDetails createFromParcel(Parcel in) {
            return new OrderDetails(in);
        }

        public OrderDetails[] newArray(int size) {
            return new OrderDetails[size];
        }
    };
    Boolean Accept = false;
    Boolean Cancel = false;
    Boolean Cooking = false;
    Boolean Delivered = false;
    Boolean OutForDelivery = false;
    Boolean Processing = true;
    String address;
    String addressTitle;
    ArrayList<String> catId;
    ArrayList<String> deliveryTime;
    ArrayList<String> foodCategory;
    ArrayList<String> foodImages;
    ArrayList<String> foodItemId;
    ArrayList<String> foodNames;
    ArrayList<Integer> foodQuantity;
    ArrayList<String> foodSize;
    String itemPushKey;
    String paymentMethod;
    String phone;
    String time;
    double totalAmount;
    ArrayList<Integer> totalPrices;
    String userId;
    String userName;

    public OrderDetails() {
    }

    public OrderDetails(Boolean accept, String address2, String addressTitle2, Boolean cancel, ArrayList<String> catId2, Boolean cooking, Boolean delivered, ArrayList<String> deliveryTime2, ArrayList<String> foodCategory2, ArrayList<String> foodImages2, ArrayList<String> foodItemId2, ArrayList<String> foodNames2, ArrayList<Integer> foodQuantity2, ArrayList<String> foodSize2, String itemPushKey2, Boolean outForDelivery, String paymentMethod2, String phone2, Boolean processing, String time2, double totalAmount2, ArrayList<Integer> totalPrices2, String userId2, String userName2) {
        this.Accept = accept;
        this.address = address2;
        this.addressTitle = addressTitle2;
        this.Cancel = cancel;
        this.catId = catId2;
        this.Cooking = cooking;
        this.Delivered = delivered;
        this.deliveryTime = deliveryTime2;
        this.foodCategory = foodCategory2;
        this.foodImages = foodImages2;
        this.foodItemId = foodItemId2;
        this.foodNames = foodNames2;
        this.foodQuantity = foodQuantity2;
        this.foodSize = foodSize2;
        this.itemPushKey = itemPushKey2;
        this.OutForDelivery = outForDelivery;
        this.paymentMethod = paymentMethod2;
        this.phone = phone2;
        this.Processing = processing;
        this.time = time2;
        this.totalAmount = totalAmount2;
        this.totalPrices = totalPrices2;
        this.userId = userId2;
        this.userName = userName2;
    }

    public Boolean getAccept() {
        return Accept;
    }

    public void setAccept(Boolean accept) {
        Accept = accept;
    }

    public Boolean getCancel() {
        return Cancel;
    }

    public void setCancel(Boolean cancel) {
        Cancel = cancel;
    }

    public Boolean getCooking() {
        return Cooking;
    }

    public void setCooking(Boolean cooking) {
        Cooking = cooking;
    }

    public Boolean getDelivered() {
        return Delivered;
    }

    public void setDelivered(Boolean delivered) {
        Delivered = delivered;
    }

    public Boolean getOutForDelivery() {
        return OutForDelivery;
    }

    public void setOutForDelivery(Boolean outForDelivery) {
        OutForDelivery = outForDelivery;
    }

    public Boolean getProcessing() {
        return Processing;
    }

    public void setProcessing(Boolean processing) {
        Processing = processing;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddressTitle() {
        return addressTitle;
    }

    public void setAddressTitle(String addressTitle) {
        this.addressTitle = addressTitle;
    }

    public ArrayList<String> getCatId() {
        return catId;
    }

    public void setCatId(ArrayList<String> catId) {
        this.catId = catId;
    }

    public ArrayList<String> getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(ArrayList<String> deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public ArrayList<String> getFoodCategory() {
        return foodCategory;
    }

    public void setFoodCategory(ArrayList<String> foodCategory) {
        this.foodCategory = foodCategory;
    }

    public ArrayList<String> getFoodImages() {
        return foodImages;
    }

    public void setFoodImages(ArrayList<String> foodImages) {
        this.foodImages = foodImages;
    }

    public ArrayList<String> getFoodItemId() {
        return foodItemId;
    }

    public void setFoodItemId(ArrayList<String> foodItemId) {
        this.foodItemId = foodItemId;
    }

    public ArrayList<String> getFoodNames() {
        return foodNames;
    }

    public void setFoodNames(ArrayList<String> foodNames) {
        this.foodNames = foodNames;
    }

    public ArrayList<Integer> getFoodQuantity() {
        return foodQuantity;
    }

    public void setFoodQuantity(ArrayList<Integer> foodQuantity) {
        this.foodQuantity = foodQuantity;
    }

    public ArrayList<String> getFoodSize() {
        return foodSize;
    }

    public void setFoodSize(ArrayList<String> foodSize) {
        this.foodSize = foodSize;
    }

    public String getItemPushKey() {
        return itemPushKey;
    }

    public void setItemPushKey(String itemPushKey) {
        this.itemPushKey = itemPushKey;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public ArrayList<Integer> getTotalPrices() {
        return totalPrices;
    }

    public void setTotalPrices(ArrayList<Integer> totalPrices) {
        this.totalPrices = totalPrices;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @SuppressLint("NewApi")
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeDouble(this.totalAmount);
        parcel.writeString(this.phone);
        parcel.writeString(this.itemPushKey);
        parcel.writeString(this.time);
        parcel.writeString(this.userName);
        parcel.writeString(this.addressTitle);
        parcel.writeString(this.address);
        parcel.writeString(this.paymentMethod);
        parcel.writeString(this.userId);
        parcel.writeStringList(this.foodNames);
        parcel.writeStringList(this.catId);
        parcel.writeStringList(this.foodCategory);
        parcel.writeStringList(this.foodImages);
        parcel.writeStringList(this.foodSize);
        parcel.writeStringList(this.foodItemId);
        parcel.writeStringList(this.deliveryTime);
        parcel.writeList(this.totalPrices);
        parcel.writeList(this.foodQuantity);
        parcel.writeBoolean(this.Processing);
        parcel.writeBoolean(this.Accept);
        parcel.writeBoolean(this.Delivered);
        parcel.writeBoolean(this.Cancel);
        parcel.writeBoolean(this.OutForDelivery);
        parcel.writeBoolean(this.Cooking);
    }

    @SuppressLint("NewApi")
    protected OrderDetails(Parcel in) {
        this.totalAmount = in.readDouble();
        this.phone = in.readString();
        this.itemPushKey = in.readString();
        this.time = in.readString();
        this.userName = in.readString();
        this.addressTitle = in.readString();
        this.address = in.readString();
        this.paymentMethod = in.readString();
        this.userId = in.readString();
        this.foodNames = in.createStringArrayList();
        this.catId = in.createStringArrayList();
        this.foodCategory = in.createStringArrayList();
        this.foodImages = in.createStringArrayList();
        this.foodSize = in.createStringArrayList();
        this.deliveryTime = in.createStringArrayList();
        this.foodItemId = in.createStringArrayList();
        this.totalPrices = in.readArrayList(Integer.class.getClassLoader());
        this.foodQuantity = in.readArrayList(Integer.class.getClassLoader());
        this.Processing = in.readBoolean();
        this.Accept = in.readBoolean();
        this.Cooking = in.readBoolean();
        this.Delivered = in.readBoolean();
        this.OutForDelivery = in.readBoolean();
        this.Cancel = in.readBoolean();
    }

    public int describeContents() {
        return 0;
    }
}
