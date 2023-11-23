package com.example.trafficsimulation.models;

public class Vehicle {
    private VehicleState state = VehicleState.MOVING;
    private final double WIDTH, HEIGHT;
    private double x, y;
    private double accelerationTime = 2, decelerationTime = accelerationTime/2; // с
    private double accelerationTimeLeft = 0, decelerationTimeLeft = 0;
    private double mainlineSpeed = 100, speed = 0; // м/с mainlineSpeed
    private double brakingDistance;
    public Vehicle(double width, double height) {
//        System.out.println(getNewSpeed(0, 0, decelerationTime, mainlineSpeed, 0));
//        System.out.println(getNewSpeed(decelerationTime, 0, decelerationTime, mainlineSpeed, 0));
        WIDTH = width;
        HEIGHT = height;

//        decelerationTime = (weight*speed)/Friction force
    }
    public void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }
    public void setState(VehicleState state) {
        this.state = state;
    }
    public void move(double timeLeft) {
        x += timeLeft*mainlineSpeed;
//        accelerationTimeLeft = decelerationTimeLeft = 0;
    }
    public void accelerate(double timeLeft) {
        accelerationTimeLeft += timeLeft;
        if (accelerationTimeLeft >= accelerationTime) {
            state = VehicleState.MOVING;
            speed = mainlineSpeed;
            accelerationTimeLeft = 0;
        } else {
//            if (speed == 0) {
//                System.out.println("0");
//            }
            speed = getNewSpeedWithAccelerate(accelerationTimeLeft, 0, accelerationTime, 0, mainlineSpeed);
        }
        x += timeLeft*speed;

    }
    public double getNewSpeedWithAccelerate(double old, double oldMin, double oldMax, double newMin, double newMax) {
        double oldRange = oldMax - oldMin;
        double newRange = newMax - newMin;
        double converted = (((old - oldMin) * newRange) / oldRange) + newMin;
        return converted;
    }
    public double getNewSpeedWithSlow(double old, double oldMin, double oldMax, double newMin, double newMax) {
        double oldRange = oldMax - oldMin;
        double newRange = newMin - newMax;
        double converted = (((old - oldMin) * newRange) / oldRange) - newMin;
        return -converted;
    }
    public void slow(double timeLeft) {
//        System.out.println(speed);
        decelerationTimeLeft += timeLeft;
        if (decelerationTimeLeft >= decelerationTime) {
            decelerationTimeLeft = 0;
            state = VehicleState.STANDING;
            speed = 0;
        } else {
            speed = getNewSpeedWithSlow(decelerationTimeLeft, 0, decelerationTime, mainlineSpeed, 0);
            System.out.println(speed);
//            System.out.println(speed);
            x += timeLeft * speed;
        }
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
