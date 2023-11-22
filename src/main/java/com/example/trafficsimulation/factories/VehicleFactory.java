package com.example.trafficsimulation.factories;

import com.example.trafficsimulation.controllers.VehicleController;
import com.example.trafficsimulation.models.Vehicle;

public interface VehicleFactory {
    VehicleController createVehicle(double width, double height);
}
