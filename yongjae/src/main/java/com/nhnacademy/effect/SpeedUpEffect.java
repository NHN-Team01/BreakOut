package com.nhnacademy.effect;

import com.nhnacademy.Ball;
import com.nhnacademy.Brick;
import com.nhnacademy.Paddle;
import javafx.scene.paint.Color;

public class SpeedUpEffect implements Effect {

    private Color color = Color.IVORY;
    private Ball ball;

    public SpeedUpEffect(Ball ball) {
        this.ball = ball;
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public int apply() {
        ball.setDx(ball.getDx() * 2);
        ball.setDy(ball.getDy() * 2);
        return 50;
    }
}
