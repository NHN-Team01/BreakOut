package com.nhnacademy.effect;

import com.nhnacademy.Ball;
import com.nhnacademy.Brick;
import com.nhnacademy.Paddle;
import javafx.scene.paint.Color;

public class PaddleLengthEffect implements Effect {

    private Color color = Color.LIGHTPINK;
    private Paddle paddle;

    public PaddleLengthEffect(Paddle paddle) {
        this.paddle = paddle;
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public int apply() {
        paddle.setWidth(paddle.getWidth() * 2);
        return 50;
    }
}
