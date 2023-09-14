package com.example.quickpick.api_structure;

import java.util.List;

public class Geometry {
    private List<Coordinate> coordinates;
    private String types;

    public Geometry(List<Coordinate> coordinates, String types) {
        this.coordinates = coordinates;
        this.types = types;
    }

    public List<Coordinate> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<Coordinate> coordinates) {
        this.coordinates = coordinates;
    }

    public String getTypes() {
        return types;
    }

    public void setTypes(String types) {
        this.types = types;
    }
}
