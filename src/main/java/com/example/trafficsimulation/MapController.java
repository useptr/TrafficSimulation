package com.example.trafficsimulation;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class MapController {
    private final double WIDTH=500, HEIGHT=300;
    private AnchorPane map = new AnchorPane();
    // 10 millis - 100 fps
    private Timeline timeline = new Timeline(new KeyFrame(Duration.millis(10),this::vehicleHandler));
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

        road.setFill(Color.web("#949391"));
        road.setY(HEIGHT-road.getHeight());
        map.getChildren().add(road);

        car.setFill(Color.BLUE);
        map.getChildren().add(car);
        car.setY(road.getY()+road.getHeight()/2-car.getHeight()/2);

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
    public void vehicleHandler(ActionEvent event) {
        double offset = 5;
        car.setX(car.getX()+offset);
    }
    public AnchorPane getMap() {
        return map;
    }

}
