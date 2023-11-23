package com.example.trafficsimulation.controllers;

import com.example.trafficsimulation.events.Event;
import com.example.trafficsimulation.events.EventListener;
import com.example.trafficsimulation.events.TrafficLightChangeStageEvent;
import com.example.trafficsimulation.models.TrafficLightsStage;
import com.example.trafficsimulation.models.Vehicle;
import com.example.trafficsimulation.models.VehicleState;
import com.example.trafficsimulation.views.VehicleView;

import java.util.LinkedList;

public class VehicleController implements EventListener {
    public static LinkedList<VehicleController> vehicleControllers = new LinkedList<>();
    public double nextTrafficLightPosX;
    private TrafficLightsStage lastStage = TrafficLightsStage.GREEN;
//    private Vehicle nextVehicle=null;
    public Vehicle vehicle;
    public VehicleView view;
    public VehicleController(Vehicle vehicle, VehicleView view) {
        this.vehicle = vehicle;
        this.view = view;
    }
    public void checkObstacles() {
        double brakingDistance = vehicle.width()*1.2;
            for (VehicleController controller : vehicleControllers) {
                Vehicle other = controller.vehicle;
                // vehicle.x() x y левый верхний угол прямоугольника
                boolean nextVehicleTooClose = other.x() > vehicle.x()+ vehicle.width() && vehicle.x()+ vehicle.width() + brakingDistance > other.x();
                if (nextVehicleTooClose) {
                    vehicle.setState(VehicleState.SLOWING);
                    return;
                }
            }
        boolean trafficLightTooClose = vehicle.x() + vehicle.width() < nextTrafficLightPosX && vehicle.x()+ vehicle.width()+brakingDistance > nextTrafficLightPosX;
        boolean StageNeedToStop = lastStage == TrafficLightsStage.RED || lastStage == TrafficLightsStage.YELLOW || lastStage == TrafficLightsStage.RED_YELLOW;
        if (trafficLightTooClose && StageNeedToStop) {
            vehicle.setState(VehicleState.SLOWING);
            return;
        }

        if (vehicle.state() == VehicleState.SLOWING || vehicle.state() == VehicleState.STANDING) {
            double minDistance = 1000;
            for (VehicleController controller : vehicleControllers) {
                Vehicle other = controller.vehicle;
                if (other.x() > vehicle.x() && other.x() < minDistance) {
                    minDistance = other.x();
                }
            }
            if (minDistance > brakingDistance) {
                vehicle.setState(VehicleState.ACCELERATING);
                return;
            }
        }

        if (vehicle.x() < nextTrafficLightPosX) {
            if (lastStage == TrafficLightsStage.GREEN && vehicle.state() == VehicleState.SLOWING || vehicle.state() == VehicleState.STANDING) {
                vehicle.setState(VehicleState.ACCELERATING);
                return;
            }
        }
//        boolean
//        if (vehicle.)
//        } else {
//            if (vehicle.x() < nextVehicle.x()-vehicle.x() && (vehicle.state() == VehicleState.SLOWING || vehicle.state() == VehicleState.STANDING)) {
//                vehicle.setState(VehicleState.ACCELERATING);
//                return;
//            }
//        }
    }
    public void update(double timeLeft) {
        checkObstacles();
        vehicle.update(timeLeft/1000);
        view.setPosition(vehicle.x(),vehicle.y());
//        if (vehicle.state() == VehicleState.ACCELERATING || vehicle.state() == VehicleState.MOVING)
//            System.out.println(vehicle.state());
    }
    @Override
    public void handle(String eventType, Event event) {

        TrafficLightChangeStageEvent stageEvent = (TrafficLightChangeStageEvent)event;
        lastStage = stageEvent.stage;
        if (vehicle.x() < nextTrafficLightPosX) {
            if (eventType.equals("red light is on")) {
//                view.turnOnRedLight();
            } else if (eventType.equals("red and yellow light is on")) {
//                view.turnOnRedYellowLight();
            } else if (eventType.equals("yellow light is on")) {
//                view.turnOnYellowLight();
            } else if (eventType.equals("green light is on")) {
//                view.turnOnGreenLight();
            }
        }
    }
    public void setNextTrafficLight(double x) {
//        System.out.println(x);
        nextTrafficLightPosX = x;
    }

//    public void setNextVehicle(Vehicle nextVehicle) {
//        if (nextVehicle!=null)
//            this.nextVehicle = nextVehicle;
//    }
}
