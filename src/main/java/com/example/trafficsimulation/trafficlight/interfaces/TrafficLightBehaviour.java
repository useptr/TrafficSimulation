package com.example.trafficsimulation.trafficlight.interfaces;

import com.example.trafficsimulation.trafficlight.TrafficLightsControl;

public interface TrafficLightBehaviour {
    void on();
    void reset();
    void setState(TrafficLightsControl.Event state);
    TrafficLightsControl.Event state(); // получить текущее состояние
    void setStateDuration(TrafficLightsControl.Event state, int duration);
}
