package com.example.trafficsimulation.trafficlight;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class TrafficLightComponent {
    private Color offColor = Color.web("#555555");
    private Timeline greenBlinkingTimeline;
    private Timeline yellowBlinkingTimeline;
//    @FXML
    private Pane trafficLightPane;
//    @FXML
    private VBox trafficLightVBox;
//    @FXML
    private Circle trafficLightGreenCircle = new Circle(10, offColor);
//    @FXML
    private Circle trafficLightYellowCircle= new Circle(10, offColor);;
//    @FXML
    private Circle trafficLightRedCircle= new Circle(10, offColor);;
//    @Override
//    public void initialize(URL url, ResourceBundle resourceBundle) {
//
//    }
    public TrafficLightComponent() {
        trafficLightVBox = new VBox(trafficLightRedCircle, trafficLightYellowCircle, trafficLightGreenCircle);
        trafficLightPane = new Pane(trafficLightVBox);
        initTimelines();
    }
    public Pane root() {
        return trafficLightPane;
    }
    private void initTimelines() {
        greenBlinkingTimeline = new Timeline(
                new KeyFrame(Duration.seconds(0.8), new KeyValue(trafficLightGreenCircle.fillProperty(), Color.GREEN)),
                new KeyFrame(Duration.seconds(0.2), new KeyValue(trafficLightGreenCircle.fillProperty(), offColor))
        );
        greenBlinkingTimeline.setCycleCount(Animation.INDEFINITE);

        yellowBlinkingTimeline = new Timeline(
                new KeyFrame(Duration.seconds(0.8), new KeyValue(trafficLightYellowCircle.fillProperty(), Color.YELLOW)),
                new KeyFrame(Duration.seconds(0.2), new KeyValue(trafficLightYellowCircle.fillProperty(), offColor))
        );
        yellowBlinkingTimeline.setCycleCount(Animation.INDEFINITE);
    }
    public void initBinds(Pane pane)
    {
        double circleRadiusRatio = 3*2+0.1;
//        pane.getHeight();
        trafficLightGreenCircle.radiusProperty().bind(pane.heightProperty().divide(circleRadiusRatio));
        trafficLightYellowCircle.radiusProperty().bind(pane.heightProperty().divide(circleRadiusRatio));
        trafficLightRedCircle.radiusProperty().bind(pane.heightProperty().divide(circleRadiusRatio));
    }
    public Circle getRed() {
        return trafficLightRedCircle;
    }
    public Circle getYellow() {
        return trafficLightYellowCircle;
    }
    public Circle getGreen() {
        return trafficLightGreenCircle;
    }
    private void resetCircles() {
        trafficLightGreenCircle.setFill(offColor);
        trafficLightYellowCircle.setFill(offColor);
        trafficLightRedCircle.setFill(offColor);
        greenBlinkingTimeline.stop();
        yellowBlinkingTimeline.stop();
    }
    public void onRed() {
        resetCircles();
        trafficLightRedCircle.setFill(Color.RED);
    }
    public void onRedYellow() {
        resetCircles();
        trafficLightRedCircle.setFill(Color.RED);
        trafficLightYellowCircle.setFill(Color.YELLOW);
    }
    public void onYellow() {
        resetCircles();
        trafficLightYellowCircle.setFill(Color.YELLOW);
    }
    public void onGreen() {
        resetCircles();
        trafficLightGreenCircle.setFill(Color.GREEN);
    }
    public void onGreenBlinking() {
        resetCircles();
        greenBlinkingTimeline.play();
    }
    public void onLastYellow() {
        resetCircles();
        trafficLightYellowCircle.setFill(Color.YELLOW);
    }
    public void onYellowBlinking() {
        resetCircles();
        yellowBlinkingTimeline.play();
    }

}
