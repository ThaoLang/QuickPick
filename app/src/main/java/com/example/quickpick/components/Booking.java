package com.example.quickpick.components;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Booking {
    private String id;
    private String customer_id;

    private String order_date;
    private String driver_id;
    private int price;
    private int rating;

    private int status;
    private String pickup_location;
    private String destination;
    private int type_transport;

    public Booking(String id, String customer_id, int price, String pickup_location, String destination, int type_transport) {
        String order_date=getCurrentDate();

        this.id = id;
        this.customer_id = customer_id;
        this.driver_id = null;
        this.order_date=order_date;
        this.price = price;
        this.rating = 0;
        this.status = 0;
        this.pickup_location = pickup_location;
        this.destination = destination;
        this.type_transport = type_transport;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getOrder_date() {
        return order_date;
    }

    public void setOrder_date(String order_date) {
        this.order_date = order_date;
    }

    public String getDriver_id() {
        return driver_id;
    }

    public void setDriver_id(String driver_id) {
        this.driver_id = driver_id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getPickup_location() {
        return pickup_location;
    }

    public void setPickup_location(String pickup_location) {
        this.pickup_location = pickup_location;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public int getType_transport() {
        return type_transport;
    }

    public void setType_transport(int type_transport) {
        this.type_transport = type_transport;
    }

    private String getCurrentDate(){
        LocalDateTime currentDateTime = LocalDateTime.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        String formattedDateTime = currentDateTime.format(formatter);

        return formattedDateTime;
    }
}
