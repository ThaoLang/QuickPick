package com.example.quickpick.components;

import com.example.quickpick.constants.GlobalAvatarURL;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MyCustomer extends User implements Serializable
{
    protected AccountType type_customer;
    protected List<SavedPlace> saved_locations;
    protected List<Booking> bookingList;

    public List<Booking> getBookingList() {
        return bookingList;
    }

    protected String fcm_token;

    public String getFcm_token() {
        return fcm_token;
    }

    public void setFcm_token(String fcm_token) {
        this.fcm_token = fcm_token;
    }

    public void setBookingList(List<Booking> bookingList) {
        this.bookingList = bookingList;
    }

    public MyCustomer(String email, String phone_number, String username,String fcm_token){
        this.setName(username);
        this.setType_customer(AccountType.Standard);
        this.setEmail(email);
        this.setPhoneNumber(phone_number);
        this.saved_locations=new ArrayList<SavedPlace>();
        this.bookingList=new ArrayList<Booking>();
        this.fcm_token=fcm_token;

        Random rng=new Random();
        String random_avatar_url= GlobalAvatarURL.default_avatar_urls[rng.nextInt(GlobalAvatarURL.default_avatar_urls.length)];
        this.setAvatar(random_avatar_url);
    }

    public AccountType getType_customer() {
        return type_customer;
    }

    public void setType_customer(AccountType type_customer) {
        this.type_customer = type_customer;
    }

    public List<SavedPlace> getSaved_locations() {
        return saved_locations;
    }

    public void setSaved_locations(List<SavedPlace> saved_locations) {
        this.saved_locations = saved_locations;
    }

    @Override
    public String toString() {
        return "MyCustomer{" +
                "type_customer=" + type_customer +
                ", saved_locations=" + saved_locations +
                ", name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", avatar='" + avatar + '\'' +
                '}';
    }
}
