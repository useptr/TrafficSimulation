package com.example.trafficsimulation.trafficlight;

import com.example.trafficsimulation.trafficlight.interfaces.TrafficLightEventListener;
import com.example.trafficsimulation.trafficlight.models.TrafficLightEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TrafficLightEventManager {
    Map<TrafficLightsControl.Event, List<TrafficLightEventListener>> listeners = new HashMap<>(); // key - событие, value - слушатели события
    public TrafficLightEventManager(TrafficLightsControl.Event... operations) {
        for (TrafficLightsControl.Event operation : operations) {
            this.listeners.put(operation, new ArrayList<>());
        }
    } // конструктор
    public void subscribe(TrafficLightsControl.Event eventType, TrafficLightEventListener listener) {
        List<TrafficLightEventListener> users = listeners.get(eventType);
        users.add(listener);
    } // добавить нового слушателя на выбранное событие
    public void subscribeAll(TrafficLightEventListener listener) {
        for (TrafficLightsControl.Event event : listeners.keySet()) {
            List<TrafficLightEventListener> users = listeners.get(event);
            users.add(listener);
        }
    } // добавить нового слушателя на все события

    public void unsubscribe(TrafficLightsControl.Event eventType,TrafficLightEventListener listener) {
        List<TrafficLightEventListener> users = listeners.get(eventType);
        users.remove(listener);
    } // отписать слушателя
    public void notify(TrafficLightsControl.Event eventType, TrafficLightEvent event) {
        List<TrafficLightEventListener> users = listeners.get(eventType);
        for (TrafficLightEventListener listener : users) {
            listener.update(eventType, event);
        }
    } // уведомить слушателей о событии
}
