package com.example.trafficsimulation.models;

public class Vehicle {
    private VehicleState state = VehicleState.MOVING;
    private final double WIDTH, HEIGHT;
    private double x, y;
    private double accelerationTime = 3, decelerationTime = accelerationTime/2; // с
    private double mainlineSpeed = 8, speed = mainlineSpeed; // м/с
    private double brakingDistance;
    public Vehicle(double width, double height, double x, double y) {
        WIDTH = width;
        HEIGHT = height;
        this.x = x;
        this.y = y;
//        decelerationTime = (weight*speed)/Friction force
    }
    public void move() {
        x += 2;
    }
    public void accelerate() {
        double offset = speed/1000;
        x += 2;
    }
    public void slow() {

    }
    public void update(double timeLeft) {
        if (state == VehicleState.MOVING) {
            move();
        } else if (state == VehicleState.ACCELERATING) {
            accelerate();
        } else if (state == VehicleState.SLOWING) {
            slow();
        }
    }

    public double width() {
        return WIDTH;
    }

    public double height() {
        return HEIGHT;
    }

    public double x() {
        return x;
    }

    public double y() {
        return y;
    }
}
