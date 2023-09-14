package com.example.quickpick.api_structure;

public class GetCostResponse {
    private String vehicle;
    private Double duration;
    private Double distance;
    private Double cost;

    public GetCostResponse(String vehicle, Double duration, Double distance, Double cost) {
        this.vehicle = vehicle;
        this.duration = duration;
        this.distance = distance;
        this.cost = cost;
    }

    public String getVehicle() {
        return vehicle;
    }

    public void setVehicle(String vehicle) {
        this.vehicle = vehicle;
    }

    public Double getDuration() {
        return duration;
    }

    public void setDuration(Double duration) {
        this.duration = duration;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }
}
