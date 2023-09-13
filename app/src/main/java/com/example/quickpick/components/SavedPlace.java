package com.example.quickpick.components;

public class SavedPlace {
    private String name;
    private String location_address;

    public SavedPlace(String name, String location_address) {
        this.name = name;
        this.location_address = location_address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation_address() {
        return location_address;
    }

    public void setLocation_address(String location_address) {
        this.location_address = location_address;
    }
}
