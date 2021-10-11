package xyz.fraction;

import xyz.fraction.event.EventHandler;

public enum Fraction {
    INSTANCE;

    private final EventHandler eventHandler = new EventHandler();

    public void start() {

    }

    public void stop() {

    }

    public EventHandler getEventHandler() {
        return eventHandler;
    }
}
