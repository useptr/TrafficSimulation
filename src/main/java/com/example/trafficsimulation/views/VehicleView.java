package com.example.trafficsimulation.views;

import com.example.trafficsimulation.models.Vehicle;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class VehicleView {
    private Rectangle view; // прямоугольник для представления
    public VehicleView(double width, double height, Color color) {
        view = new Rectangle(width, height);
        view.setFill(color);
    }
    public void setPosition(double x,  double y) {
        view.setLayoutX(x);
        view.setLayoutY(y);
    }
    public Rectangle view() {
        return view;
    }
}
