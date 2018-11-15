package com.example.hp.doubletapapp.events;

import com.example.hp.doubletapapp.database.WordEntity;

public class ClickEvents {

    private final String message;
    private final WordEntity item;

    public ClickEvents(String message, WordEntity item) {
        this.message = message;
        this.item = item;
    }

    public String getMessage() {
        return message == null || message.isEmpty() ? "": message;
    }

    public WordEntity getItem() {
        return item;
    }
}
