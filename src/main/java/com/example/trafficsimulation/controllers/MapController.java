package com.example.trafficsimulation.controllers;

import com.example.trafficsimulation.factories.CarFactory;
import com.example.trafficsimulation.models.Vehicle;
import com.example.trafficsimulation.trafficlight.TrafficLightsControl;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.LinkedList;

public class MapController {
    private final static CarFactory carFactory = new CarFactory();
    private final double WIDTH=500, HEIGHT=300;
    private final double DURATION = 10; // 10 millis - 100 fps
//    private TrafficLightView trafficLight = new TrafficLightView();

//    private TrafficLightController trafficLightController = new TrafficLightController();
    private TrafficLightsControl trafficLightsControl = new TrafficLightsControl();
    private LinkedList<VehicleController> vehicleControllers = VehicleController.vehicleControllers;
//    private ArrayList<VehicleView> vehicles = new ArrayList<>();
    private AnchorPane map = new AnchorPane();
    // 10 millis - 100 fps
    private Timeline timeline = new Timeline(new KeyFrame(Duration.millis(DURATION),this::vehicleHandler));
    private Rectangle road = new Rectangle(WIDTH, HEIGHT/3);
    private Rectangle car = new Rectangle(20, 15);
    public MapController() {
        map.setMaxWidth(WIDTH);
        map.setMaxHeight(HEIGHT);
        map.setPrefWidth(WIDTH);
        map.setPrefHeight(HEIGHT);
        map.setStyle("-fx-background-color: #c8c8c8;");
        map.setClip(null);
        map.setClip(new Rectangle(map.getPrefWidth(), map.getPrefHeight()));

//        System.out.println(map.getLayoutX() + " " + map.getLayoutY());

        road.setFill(Color.web("#949391"));
        road.setLayoutY(HEIGHT-road.getHeight());
        map.getChildren().add(road);

//        map.getChildren().add(trafficLightController.view());
//        trafficLightsControl.setPrefWidth(20);
        trafficLightsControl.setPrefHeight(40);
        map.getChildren().add(trafficLightsControl);
        System.out.println(trafficLightsControl.getPrefWidth());
        System.out.println(trafficLightsControl.getPrefHeight());
        System.out.println(trafficLightsControl.getWidth());
        System.out.println(trafficLightsControl.getHeight());
        trafficLightsControl.setLayoutX(WIDTH/2-trafficLightsControl.getPrefHeight()/2);
        trafficLightsControl.setLayoutY(HEIGHT/3*2-trafficLightsControl.getPrefHeight()*2);
//        System.out.println(trafficLight.view().getWidth());
//        trafficLightController.view().setLayoutX(WIDTH/2-6);
//        trafficLightController.view().setLayoutY(HEIGHT/3*2-70);
//        System.out.println(road.getX() + " " + road.getY());
//        System.out.println(road.getLayoutX() + " " + road.getLayoutY());


//        map.getChildren().add(car);
//        car.setFill(Color.BLUE);
////        System.out.println(car.getX() + " " + car.getY());
////        System.out.println(car.getLayoutX() + " " + car.getLayoutY());
//        car.setLayoutX(-car.getWidth());
//        car.setLayoutY(road.getLayoutY()+road.getHeight()/2-car.getHeight()/2);

        createVehicle();

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
    private double countdown = 0;
    private int count = 0;
    public void vehicleHandler(ActionEvent event) {
        countdown += DURATION;
        if (countdown > 2000 ) { // && count < 3
            countdown = 0;
            createVehicle();
            count++;
        }
//        double offset = 2;
//        car.setX(car.getX()+offset);

        moveVehicles();
//        removeVehiclesBeyondMap();
    }
    public AnchorPane getMap() {
        return map;
    }
    public void removeVehiclesBeyondMap() {
//        for (int i = 0; i < vehicles.size();) {
//            VehicleView vehicle = vehicles.get(i);
//            if (vehicle.view().getX() > road.getX()+road.getWidth())
//                vehicles.remove(i);
//            else
//                ++i;
//        }
    }
    public void moveVehicles() {
        for (VehicleController vehicle : vehicleControllers) {
            vehicle.update(DURATION);
//            view.vehicle.update(2);
//            view.update();
//            System.out.println(view.view().getLayoutX() + " " + view.view().getLayoutY());
        }
    }
    public void createVehicle() {
        VehicleController controller = carFactory.createVehicle(20, 15);

        double x = -controller.vehicle.width();
        double y = road.getLayoutY()+road.getHeight()/2-controller.vehicle.height()/2;

        for (VehicleController vehicleController :vehicleControllers) {
            Vehicle other = vehicleController.vehicle;
            if (other.x() <= x+other.width())
                return;
        }
//        System.out.println(x + " " + y);
        controller.vehicle.setPosition(x, y);

        map.getChildren().add(controller.view.view());
        controller.view.setPosition(x, y);
//        Vehicle next = null;
//        try {
//            next = vehicleControllers.peek().vehicle;
//        }
//        catch (NullPointerException e) {
//
//        }
//        controller.setNextVehicle(next);
        vehicleControllers.add(controller);

//        controller.setNextTrafficLight(trafficLightController.view.x());
        controller.setNextTrafficLight(WIDTH/2-trafficLightsControl.getPrefHeight()/2);
        controller.setNextTrafficLight(244); // WIDTH/2
        trafficLightsControl.events.subscribeAll(controller);
//        trafficLightController.subscribe("red light is on", controller);
//        trafficLightController.subscribe( "red and yellow light is on",controller);
//        trafficLightController.subscribe("yellow light is on", controller);
//        trafficLightController.subscribe( "green light is on", controller);

////        double x = width;

////        System.out.println(x + " " + y);
//        Vehicle vehicle = new Vehicle(width, height, x, y);
//        VehicleView vehicleView = new VehicleView(vehicle, map);
//        vehicles.add(vehicleView);
//        map.getChildren().add(vehicleView.view());

//        vehicleView.view().setLayoutX(vehicle.x());
//        vehicleView.view().setLayoutY(vehicle.y());
    }

}
