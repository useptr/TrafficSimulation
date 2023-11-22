package com.example.trafficsimulation;

import com.example.trafficsimulation.models.Vehicle;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class VehicleView {
    public Vehicle vehicle;
    private Rectangle view;
    public VehicleView(Vehicle vehicle, AnchorPane map) {
        this.vehicle = vehicle;
//        System.out.println(vehicle.width() + " " + vehicle.height());
        view = new Rectangle(vehicle.width(), vehicle.height());
        map.getChildren().add(view);
        view.setFill(Color.BLUE);
        view.setLayoutX(vehicle.x());
        view.setLayoutY(vehicle.y());
    }
    public void update() {
        view.setLayoutX(vehicle.x());
        view.setLayoutY(vehicle.y());
    }

    public Rectangle view() {
        return view;
    }
}
