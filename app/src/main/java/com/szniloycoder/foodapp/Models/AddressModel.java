package com.szniloycoder.foodapp.Models;

public class AddressModel {

    String Latitude;
    String Longitude;
    String addNewAddrs;
    String addrTitle;
    String key;

    public AddressModel(String addNewAddrs2, String addrTitle2, String key2, String latitude, String longitude) {
        this.addNewAddrs = addNewAddrs2;
        this.addrTitle = addrTitle2;
        this.key = key2;
        this.Latitude = latitude;
        this.Longitude = longitude;
    }

    public AddressModel() {
    }

    public String getAddNewAddrs() {
        return this.addNewAddrs;
    }

    public void setAddNewAddrs(String addNewAddrs2) {
        this.addNewAddrs = addNewAddrs2;
    }

    public String getAddrTitle() {
        return this.addrTitle;
    }

    public void setAddrTitle(String addrTitle2) {
        this.addrTitle = addrTitle2;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key2) {
        this.key = key2;
    }

    public String getLatitude() {
        return this.Latitude;
    }

    public void setLatitude(String latitude) {
        this.Latitude = latitude;
    }

    public String getLongitude() {
        return this.Longitude;
    }

    public void setLongitude(String longitude) {
        this.Longitude = longitude;
    }
}