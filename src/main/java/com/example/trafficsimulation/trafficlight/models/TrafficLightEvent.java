package com.example.trafficsimulation.trafficlight.models;

public class TrafficLightEvent {
    public String message;
    public int timeLeft;
    public TrafficLightEvent(String message, int timeLeft) {
        this.message = message;
        this.timeLeft = timeLeft;
    }
}
