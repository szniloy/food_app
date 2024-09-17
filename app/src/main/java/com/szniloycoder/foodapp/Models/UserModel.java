package com.szniloycoder.foodapp.Models;

public class UserModel {

    String address;
    String addressTitle;
    String email;
    String id;
    String imageUrl;
    String onlineStatus;
    String phone;
    String userName;

    public UserModel() {

    }


    public UserModel(String address2, String addressTitle2, String email2, String id2, String imageUrl2, String onlineStatus2, String phone2, String userName2) {
        this.address = address2;
        this.addressTitle = addressTitle2;
        this.email = email2;
        this.id = id2;
        this.imageUrl = imageUrl2;
        this.onlineStatus = onlineStatus2;
        this.phone = phone2;
        this.userName = userName2;
    }


    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address2) {
        this.address = address2;
    }

    public String getAddressTitle() {
        return this.addressTitle;
    }

    public void setAddressTitle(String addressTitle2) {
        this.addressTitle = addressTitle2;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email2) {
        this.email = email2;
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

    public String getOnlineStatus() {
        return this.onlineStatus;
    }

    public void setOnlineStatus(String onlineStatus2) {
        this.onlineStatus = onlineStatus2;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone2) {
        this.phone = phone2;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName2) {
        this.userName = userName2;
    }

}
