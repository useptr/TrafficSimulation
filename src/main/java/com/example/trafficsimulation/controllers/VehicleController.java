package com.example.trafficsimulation.controllers;

import com.example.trafficsimulation.models.Vehicle;
import com.example.trafficsimulation.models.VehicleState;
import com.example.trafficsimulation.trafficlight.TrafficLightsControl;
import com.example.trafficsimulation.trafficlight.interfaces.TrafficLightEventListener;
import com.example.trafficsimulation.trafficlight.models.TrafficLightEvent;
import com.example.trafficsimulation.views.VehicleView;

import java.util.LinkedList;

public class VehicleController implements TrafficLightEventListener {
//    public LinkedList<VehicleController> vehicleControllers = new LinkedList<>(); // список всех автомобилей
    public double nextTrafficLightPosX; // позиция светофора
    public boolean passed = false; // проехал ли автомобиль светофор
    private boolean sameTimeAcceleration = false; // включина ли стратегия одновременного разгона
    private TrafficLightsControl.Event state = TrafficLightsControl.Event.RED; // состояние светофора
    public Vehicle vehicle; // модель
    public VehicleView view; // представление
    public VehicleController(Vehicle vehicle, VehicleView view) {
        this.vehicle = vehicle;
        this.view = view;
    }
    public boolean trafficLightNotGreenAndTooClose() {
        double brakingDistance = vehicle.width();
        boolean trafficLightTooClose = vehicle.x() + vehicle.width() < nextTrafficLightPosX && vehicle.x() + vehicle.width() + brakingDistance > nextTrafficLightPosX;
        boolean notGreenState = state == TrafficLightsControl.Event.RED || state == TrafficLightsControl.Event.YELLOW || state == TrafficLightsControl.Event.RED_YELLOW;
        if (trafficLightTooClose && notGreenState)
            return true;
        return false;
    } // проверяет должен ли автомобиль остановиться перед светофором
    public void checkObstaclesWidthSameAcceleration(LinkedList<VehicleController> vehicleControllers) {
        if (trafficLightNotGreenAndTooClose()) {
            vehicle.setState(VehicleState.SLOWING);
            return;
        }
        double brakingDistance = vehicle.width();
        boolean existObstacle = false;
        for (VehicleController controller : vehicleControllers) { // если машина догнала другой автомобиль
            Vehicle other = controller.vehicle;
            // vehicle.x() x y левый верхний угол прямоугольника
            boolean nextVehicleTooClose = other.x() > vehicle.x() + vehicle.width() && vehicle.x() + vehicle.width() + brakingDistance > other.x();
            if (nextVehicleTooClose) {
                if (state == TrafficLightsControl.Event.GREEN || vehicle.x() + vehicle.width() > nextTrafficLightPosX) {
                    vehicle.setState(VehicleState.ACCELERATING);
                    vehicle.setSpeed(other.speed());
                    vehicle.setAccelerationTimeLeft(vehicle.speed()/vehicle.acceleration());
                    // accelerationTimeLeft = speed / acceleration
                }
                else {
                    vehicle.setState(VehicleState.SLOWING);
                    existObstacle = true;
                }
            }
        }
        if (!existObstacle && vehicle.state() == VehicleState.SLOWING || vehicle.state() == VehicleState.STANDING)
            vehicle.setState(VehicleState.ACCELERATING);
    } // останавливает/разгоняет автомобиль в зависимости от того есть ли препядствия
    // при включеной стратегией одновременного разгона
    public void checkObstacles(LinkedList<VehicleController> vehicleControllers) {
        if (existObstacles(vehicleControllers)) {
            if (vehicle.state() == VehicleState.ACCELERATING || vehicle.state() == VehicleState.MOVING)
                vehicle.setState(VehicleState.SLOWING);
        } else {
            if (vehicle.state() == VehicleState.SLOWING || vehicle.state() == VehicleState.STANDING)
                vehicle.setState(VehicleState.ACCELERATING);
        }
    } // останавливает/разгоняет автомобиль в зависимости от того есть ли препядствия
    public boolean existObstacles(LinkedList<VehicleController> vehicleControllers) {
        double brakingDistance = vehicle.width();
        for (VehicleController controller : vehicleControllers) { // если машина догнала другой автомобиль
            Vehicle other = controller.vehicle;
            // vehicle.x() x y левый верхний угол прямоугольника
            boolean nextVehicleTooClose = other.x() > vehicle.x() + vehicle.width() && vehicle.x() + vehicle.width() + brakingDistance > other.x();
            if (nextVehicleTooClose) {
                return true;
            }
        }
        if (trafficLightNotGreenAndTooClose()) {
            return true;
        }
        return false;
    } // проверяет есть ли у автомобиля препядствия
    public void update(double timeLeft, LinkedList<VehicleController> vehicleControllers) {
        if (sameTimeAcceleration)
            checkObstaclesWidthSameAcceleration(vehicleControllers);
        else {
            checkObstacles(vehicleControllers);
        }
        vehicle.update(timeLeft/1000);
        view.setPosition(vehicle.x(),vehicle.y());
    } // обновляет состояние модели и представления
    public void setNextTrafficLight(double x) {
        nextTrafficLightPosX = x;
    }
    @Override
    public void update(TrafficLightsControl.Event eventType, TrafficLightEvent event) {
        state = eventType;
    } // слушает события светофора
    public void setSameTimeAcceleration(boolean sameTimeAcceleration) {
        this.sameTimeAcceleration = sameTimeAcceleration;
    }
//    public LinkedList<VehicleController> vehicleControllers() {
//        return vehicleControllers;
//    }
}
