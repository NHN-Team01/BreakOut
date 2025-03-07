package com.nhnacademy;

import java.util.ArrayList;
import java.util.List;

public class EventManager {
    private static EventManager instance;
    private List<GameEventListener> listeners = new ArrayList<>();

    private EventManager() {}

    public static EventManager getInstance() {
        if(instance == null) {
            instance = new EventManager();
        }
        return instance;
    }

    public void addListener(GameEventListener listener) {
        listeners.add(listener);
    }

    public void removeListener(GameEventListener listener) {
        listeners.remove(listener);
    }

    public void notifyGameOver() {
        for(GameEventListener listener : listeners) {
            listener.onGameOver();
        }
    }
}
