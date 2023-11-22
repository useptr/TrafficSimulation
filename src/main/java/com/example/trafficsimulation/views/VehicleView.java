package com.example.trafficsimulation.views;

import com.example.trafficsimulation.models.Vehicle;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class VehicleView {
//    public Vehicle vehicle;
    private Rectangle view;
    public VehicleView(double width, double height, Color color) {
//        this.vehicle = vehicle;
//        System.out.println(width + " " + height);
//        System.out.println(vehicle.width() + " " + vehicle.height());
        view = new Rectangle(width, height);
//        map.getChildren().add(view);
        view.setFill(Color.BLUE);
//        view.setLayoutX(vehicle.x());
//        view.setLayoutY(vehicle.y());
    }
    public void setPosition(double x,  double y) {
//        System.out.println(x + " " + y);
        view.setLayoutX(x);
        view.setLayoutY(y);
    }

    public Rectangle view() {
        return view;
    }
}
