package com.example.trafficsimulation.events;

import com.example.trafficsimulation.models.TrafficLightsStage;

public class TrafficLightChangeStageEvent extends Event{
    public TrafficLightsStage stage;
    public TrafficLightChangeStageEvent(TrafficLightsStage stage) {
        this.stage = stage;
    }
}
