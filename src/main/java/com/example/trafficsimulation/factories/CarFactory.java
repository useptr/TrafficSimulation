package com.example.trafficsimulation.factories;

import com.example.trafficsimulation.controllers.VehicleController;
import com.example.trafficsimulation.models.Vehicle;
import com.example.trafficsimulation.views.VehicleView;
import javafx.scene.paint.Color;

import java.util.Random;

public class CarFactory implements VehicleFactory {
    private static final int colorsSize = 6;
    private static final Color[] colors = {Color.BLUE, Color.GREEN, Color.YELLOW, Color.RED, Color.PINK, Color.PURPLE};
    private Random random = new Random();
    @Override
    public VehicleController createVehicle(double width, double height) {
        int index = random.nextInt(colorsSize-1);
        Vehicle vehicle = new Vehicle(width, height);
        VehicleView view = new VehicleView(width, height, colors[index]);
        return new VehicleController(vehicle, view);
    }
}
