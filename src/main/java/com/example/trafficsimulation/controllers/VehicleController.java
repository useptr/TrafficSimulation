package com.example.trafficsimulation.controllers;

import com.example.trafficsimulation.events.Event;
import com.example.trafficsimulation.events.EventListener;
import com.example.trafficsimulation.models.Vehicle;
import com.example.trafficsimulation.views.VehicleView;

public class VehicleController implements EventListener {
    public Vehicle vehicle;
    public VehicleView view;
    public VehicleController(Vehicle vehicle, VehicleView view) {
        this.vehicle = vehicle;
        this.view = view;
    }
    public void update(double timeLeft) {
        vehicle.update(timeLeft);
        view.setPosition(vehicle.x(),vehicle.y());
    }
    @Override
    public void handle(String eventType) {

    }
}
