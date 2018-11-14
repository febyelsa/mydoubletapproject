package com.example.hp.doubletapapp.events;

public class BackgroundEvents {
    private final String event;

    public BackgroundEvents(String message) {
        this.event = message;
    }

    public String getEvent() {
        return event == null || event.isEmpty() ? "": event;
    }
}
