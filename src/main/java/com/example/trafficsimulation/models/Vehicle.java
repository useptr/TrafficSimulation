package com.example.trafficsimulation.models;

public class Vehicle {
    private VehicleState state = VehicleState.MOVING;
    private final double WIDTH, HEIGHT; // высота и ширина автомобиля
    private double x, y; // координаты автомобиля
    private double mainlineSpeed = 100; // стандартная скорость м/с
    private double speed = mainlineSpeed; // текущая скорость м/с
    private double accelerationTimeLeft = 0; // текущее время разгона с
    private double accelerationTime = 2; // время разгона с
    private double acceleration = mainlineSpeed/ accelerationTime; // ускорение м/с
    // S=ut+0.5at^2
    // S - расстояние,
    // u - начальная скорость,
    // t - время,
    // a - ускорение.
    public Vehicle(double width, double height) {
        WIDTH = width;
        HEIGHT = height;
    }
    public double acceleration() {
        return acceleration;
    }
    public void setAccelerationTimeLeft(double accelerationTimeLeft) {
        this.accelerationTimeLeft = accelerationTimeLeft;
    }
    public void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }
    public void setState(VehicleState state) {
        this.state = state;
    }
    public void move(double timeLeft) {
        x += timeLeft*speed;
    }
    public void accelerate(double timeLeft) {
        accelerationTimeLeft += timeLeft;
        if (accelerationTimeLeft >= accelerationTime) {
            state = VehicleState.MOVING;
            speed = mainlineSpeed;
            accelerationTimeLeft = 0;
        } else {
            speed = acceleration * accelerationTimeLeft;
            if (speed >= mainlineSpeed)
                speed =mainlineSpeed;
        }
        x += timeLeft*speed;
    }
    public void update(double timeLeft) {
        if (state == VehicleState.MOVING) {
            move(timeLeft);
        } else if (state == VehicleState.ACCELERATING) {
            accelerate(timeLeft);
        } else if (state == VehicleState.SLOWING) {
//            slow(timeLeft);
        }
    }
    public VehicleState state() {
        return state;
    }
    public double width() {
        return WIDTH;
    }
    public void setSpeed(double speed) {
        this.speed = speed;
    }
    public double speed( ) {
        return speed;
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
