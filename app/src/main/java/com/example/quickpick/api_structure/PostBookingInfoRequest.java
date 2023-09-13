package com.example.quickpick.api_structure;

public class PostBookingInfoRequest {
    public static String NAME="name";
    public static String PHONE="phone";
    public static String VEHICLE="vehicle";
    public static String TIME="time";
    public static String COST="cost";
    public static String DURATION="duration";
    public static String DISTANCE="distance";

    public static String ID_TOKEN="idToken";
    public static String USER_ID="userId";
    public static String SYSTEM_KEY="systemKey";
    public static String START_ADDRESS="startAddress";
    public static String END_ADDRESS="endAddress";

    private String idToken;
    private String userId;
    private String systemKey;
    private String startAddress;
    private String endAddress;
    private String name;
    private String phone;
    private String vehicle;
    private String time;
    private String cost;
    private String duration;
    private String distance;


    public PostBookingInfoRequest(String idToken, String userId, String systemKey, String startAddress, String endAddress, String name, String phone, String vehicle, String time, String cost, String duration, String distance) {
        this.idToken = idToken;
        this.userId = userId;
        this.systemKey = systemKey;
        this.startAddress = startAddress;
        this.endAddress = endAddress;
        this.name = name;
        this.phone = phone;
        this.vehicle = vehicle;
        this.time = time;
        this.cost = cost;
        this.duration = duration;
        this.distance = distance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getVehicle() {
        return vehicle;
    }

    public void setVehicle(String vehicle) {
        this.vehicle = vehicle;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getIdToken() {
        return idToken;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSystemKey() {
        return systemKey;
    }

    public void setSystemKey(String systemKey) {
        this.systemKey = systemKey;
    }

    public String getStartAddress() {
        return startAddress;
    }

    public void setStartAddress(String startAddress) {
        this.startAddress = startAddress;
    }

    public String getEndAddress() {
        return endAddress;
    }

    public void setEndAddress(String endAddress) {
        this.endAddress = endAddress;
    }


}
