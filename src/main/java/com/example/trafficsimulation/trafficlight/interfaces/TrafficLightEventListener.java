package com.example.trafficsimulation.trafficlight.interfaces;

import com.example.trafficsimulation.trafficlight.TrafficLightsControl;
import com.example.trafficsimulation.trafficlight.models.TrafficLightEvent;

public interface TrafficLightEventListener {
    void update(TrafficLightsControl.Event eventType, TrafficLightEvent event);
    // метод вызывающийся у всех слушателей после уведомления о событии
}
