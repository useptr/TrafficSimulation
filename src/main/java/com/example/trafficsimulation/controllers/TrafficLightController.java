package com.example.trafficsimulation.controllers;

import com.example.trafficsimulation.events.EventListener;
import com.example.trafficsimulation.models.TrafficLight;
import com.example.trafficsimulation.views.TrafficLightView;
import javafx.scene.layout.StackPane;

public class TrafficLightController implements EventListener {
    public TrafficLightView view = new TrafficLightView();
    public TrafficLight trafficLight = new TrafficLight();
    public TrafficLightController() {
        trafficLight.events.subscribe("red light is on",this);
        trafficLight.events.subscribe( "red and yellow light is on",this);
        trafficLight.events.subscribe("yellow light is on", this);
        trafficLight.events.subscribe( "green light is on", this);
    }
    @Override
    public void handle(String eventType) {
//        System.out.println("DS");
        if (eventType.equals("red light is on")) {
            view.turnOnRedLight();
        } else if (eventType.equals("red and yellow light is on")) {
            view.turnOnRedYellowLight();
        } else if (eventType.equals("yellow light is on")) {
            view.turnOnYellowLight();
        } else if (eventType.equals("green light is on")) {
            view.turnOnGreenLight();
        }
    }
    public void subscribe(String event, EventListener listener) {
        trafficLight.events.subscribe(event,listener);
    }
    public StackPane view() {
        return view.view();
    }
}
