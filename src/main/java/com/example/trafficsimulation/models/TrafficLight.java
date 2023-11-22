package com.example.trafficsimulation.models;

import com.example.trafficsimulation.events.EventManager;
import com.example.trafficsimulation.events.TrafficLightChangeStageEvent;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.util.Duration;

public class TrafficLight {
    public EventManager events = new EventManager("red light is on", "red and yellow light is on", "yellow light is on", "green light is on");
    int[] stageDurations = {3, 5, 7, 10};
    private TrafficLightsStage stage = TrafficLightsStage.GREEN;
    private Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), this::stageHandler));
    private int countdown = stageDurations[2];
    public TrafficLight() {
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
    public void stageHandler(ActionEvent event) {
        ++countdown;
        if (countdown > stageDurations[3])
            countdown = 0;

        if (isRedStage()) {
            stage = TrafficLightsStage.RED;
            events.notify("red light is on", new TrafficLightChangeStageEvent(stage));

        } else if (isRedYellowStage()) {
            stage = TrafficLightsStage.RED_YELLOW;
            events.notify("red and yellow light is on", new TrafficLightChangeStageEvent(stage));

        } else if (isYellowStage()) {
            events.notify("yellow light is on",new TrafficLightChangeStageEvent(stage));
            stage = TrafficLightsStage.YELLOW;
        } else if (isGreenStage()) {
            events.notify("green light is on",new TrafficLightChangeStageEvent(stage));
            stage = TrafficLightsStage.GREEN;
        }
    }
    public boolean isRedStage() {
        if (countdown < stageDurations[0])
            return true;
        return false;
    }
    public boolean isRedYellowStage() {
        if (countdown < stageDurations[1])
            return true;
        return false;
    }
    public boolean isYellowStage() {
        if (countdown < stageDurations[2])
            return true;
        return false;
    }
    public boolean isGreenStage() {
        if (countdown < stageDurations[3])
            return true;
        return false;
    }
}
