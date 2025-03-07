package com.nhnacademy.effect;

import com.nhnacademy.Ball;
import com.nhnacademy.Brick;
import com.nhnacademy.Paddle;
import javafx.scene.paint.Color;

public interface Effect {
    int apply();

    Color getColor();
}
