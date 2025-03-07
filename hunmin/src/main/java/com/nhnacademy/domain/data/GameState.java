package com.nhnacademy.domain.data;

public enum GameState {
    PLAYING,
    PAUSED,
    GAME_OVER,
    NEXT_LEVEL;

    public GameState togglePlayPause() {
        if (this == PLAYING) {
            return PAUSED;
        } else if (this == PAUSED) {
            return PLAYING;
        }
        return this;
    }
}
