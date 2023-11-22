package com.example.trafficsimulation.events;

public interface EventListener {
//    void handle(Event event);
    void handle(String eventType, Event event);
}
