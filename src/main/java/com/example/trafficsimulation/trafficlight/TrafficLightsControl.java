package com.example.trafficsimulation.trafficlight;

import com.example.trafficsimulation.trafficlight.interfaces.TrafficLightBehaviour;
import com.example.trafficsimulation.trafficlight.models.TrafficLightEvent;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.IOException;

public class TrafficLightsControl extends AnchorPane implements TrafficLightBehaviour {
    public enum Event {
        RED("Красная", 0), RED_YELLOW("Красный/Желтый", 1), YELLOW("Желтый", 2), GREEN("Зеленый",3), GREEN_BLINKING("Зеленый мигающий", 4), LAST_YELLOW("Последний желтый",5), YELLOW_BLINKING("Желтый мигающий",6);
        public final String name;
        public final int index;
        Event(String name, int index) {
            this.name = name;
            this.index = index;
        }
    } // события светофора
    public TrafficLightEventManager events = new TrafficLightEventManager(Event.RED, Event.RED_YELLOW, Event.YELLOW, Event.GREEN);
    private int[] stateDurations = {8, 9, 10, 13}; // продолжительности фаз
    private final int statesSize = 4; // количество фаз
    private Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), this::stateHandler));
    private boolean isOn = true; // состояние светофора вкл/выкл
    private int countdown = 0; // время светофора
    public TrafficLightComponent trafficLight = new TrafficLightComponent(); // view
    public TrafficLightsControl() {
//            this.setStyle("-fx-background-color: gray;");
            this.getChildren().add(trafficLight.root());
            trafficLight.initBinds(this);
            timeline.setCycleCount(Animation.INDEFINITE);

    } // конструктор
    // TrafficLightBehaviour Override методы
    @Override
    public void on() {
        isOn = true;
        state = Event.RED;
        countdown = 0;
        timeline.play();
    }

    @Override
    public void off() {
        isOn = false;
        countdown = 0;
        timeline.stop();
        trafficLight.off();
    }

    @Override
    public void reset() {
        isOn = false;
        state = Event.YELLOW_BLINKING;
    }
    @Override
    public void setState(Event state) {
        if (state.index < statesSize) {
            if (state.index > 0)
                countdown = stateDurations[state.index-1];
            else
                countdown =0;
        }
        this.state = state;
    }
    @Override
    public Event state() {
        return state;
    }
    @Override
    public void setStateDuration(Event state, int duration) {
        if (state.index < statesSize) {
            if (state.index == 0)
                stateDurations[state.index] = duration;
            else {
                int prevDuration = stateDurations[state.index]-stateDurations[state.index - 1];
                int dif = duration-prevDuration;

                for (int i = state.index; i < statesSize; ++i)
                    stateDurations[i] += dif;
            }
        }
    } // установка продолжительности фазы
    private String getMessage(Event prevState, Event curState) {
        if (prevState != curState) {
            return  "Фаза светофора изменилась с "+prevState.name+" на " + curState.name;
        }
        return "Продолжается фаза " + curState.name;
    }
    private Event state = Event.RED; // текущая фаза
    private Event prevState = state; // предыдущая фаза
    public void stateHandler(ActionEvent event) {
//        System.out.println(state);
        if (isOn) {
            if (countdown >= stateDurations[statesSize-1])
                countdown = 0;

            prevState = state;
            if (countdown < stateDurations[0]) {
                trafficLight.onRed();
                state = Event.RED;
            } else if (countdown < stateDurations[1]) {
                trafficLight.onRedYellow();
                state =Event.RED_YELLOW;
            } else if (countdown < stateDurations[2]) {
                trafficLight.onYellow();
                state = Event.YELLOW;
            } else if (countdown < stateDurations[3]) {
                trafficLight.onGreen();
                state = Event.GREEN;
            }
            int timeLeft = stateDurations[state.index]-countdown;
            TrafficLightEvent trafficLightEvent = new TrafficLightEvent( getMessage(prevState, state), timeLeft);
            events.notify(state, trafficLightEvent);
            ++countdown;
        } else {
            TrafficLightEvent trafficLightEvent = new TrafficLightEvent(getMessage(state, state), -1);
            trafficLight.onYellowBlinking();
            events.notify(state, trafficLightEvent);
        }
    } // генерирование события в зависимости от фазы
}
