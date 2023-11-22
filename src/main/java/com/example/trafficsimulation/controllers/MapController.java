package com.example.trafficsimulation.controllers;

import com.example.trafficsimulation.factories.CarFactory;
import com.example.trafficsimulation.models.Vehicle;
import com.example.trafficsimulation.views.TrafficLightView;
import com.example.trafficsimulation.views.VehicleView;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.LinkedList;

public class MapController {
    private final static CarFactory carFactory = new CarFactory();
    private final double WIDTH=500, HEIGHT=300;
    private final double DURATION = 10;
//    private TrafficLightView trafficLight = new TrafficLightView();

    private TrafficLightController trafficLightController = new TrafficLightController();
    private LinkedList<VehicleController> vehicleControllers = new LinkedList<>();
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

        map.getChildren().add(trafficLightController.view());
//        System.out.println(trafficLight.view().getWidth());
        trafficLightController.view().setLayoutX(WIDTH/2-6);
        trafficLightController.view().setLayoutY(HEIGHT/3*2-70);
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
    public void vehicleHandler(ActionEvent event) {
        countdown += DURATION;
        if (countdown > 2000) {
            countdown = 0;
            createVehicle();
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

        double x = controller.vehicle.width();
        double y = road.getLayoutY()+road.getHeight()/2-controller.vehicle.height()/2;
//        System.out.println(x + " " + y);
        controller.vehicle.setPosition(x, y);

        map.getChildren().add(controller.view.view());
        controller.view.setPosition(x, y);
        vehicleControllers.add(controller);

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
