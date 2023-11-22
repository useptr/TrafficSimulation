package com.example.trafficsimulation.views;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class TrafficLightView {
    private StackPane root;
    private VBox vBox;
    private double x;
//    private Color bgColor = Color.web("#111111");
    private Color offStageColor = Color.web("#333333");
    private Circle redCircle = new Circle(6,offStageColor);
    private Circle yellowCircle = new Circle(6,offStageColor);
    private Circle greenCircle = new Circle(6, Color.GREEN);

    private Rectangle post = new Rectangle(6, 70); // ((6*3*2)+2*2)*2

    public TrafficLightView() {
        vBox = new VBox(redCircle, yellowCircle, greenCircle);
        vBox.setStyle("-fx-background-color: #111111;");
        vBox.setMaxHeight(6*2*3);
        post.setFill(offStageColor);
        root = new StackPane(post, vBox);
        root.setAlignment(Pos.TOP_CENTER);
    }
    public StackPane view() {
        return root;
    }
    public void turnOnRedLight() {
        resetTrafficLightStages();
        redCircle.setFill(Color.RED);
    }
    public void turnOnRedYellowLight() {
        resetTrafficLightStages();
        redCircle.setFill(Color.RED);
        yellowCircle.setFill(Color.YELLOW);
    }
    public void turnOnYellowLight() {
        resetTrafficLightStages();
        yellowCircle.setFill(Color.YELLOW);
    }
    public void turnOnGreenLight() {
        resetTrafficLightStages();
        greenCircle.setFill(Color.GREEN);
    }
    public void resetTrafficLightStages() {
        redCircle.setFill(offStageColor);
        yellowCircle.setFill(offStageColor);
        greenCircle.setFill(offStageColor);
    }

    public double x() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }
}
