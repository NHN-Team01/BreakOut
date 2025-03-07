package com.nhnacademy.domain.data;

import com.nhnacademy.domain.model.Ball;
import com.nhnacademy.domain.model.Paddle;
import com.nhnacademy.view.Shape;
import java.util.List;

public class Level {
    private final Ball ball;
    private final Paddle paddle;
    private final List<Shape> shapes;


    public Level(Ball ball, Paddle paddle, List<Shape> shapes) {
        this.ball = ball;
        this.paddle = paddle;
        this.shapes = shapes;
    }

    public Ball getBall() { return ball; }
    public Paddle getPaddle() { return paddle; }
    public List<Shape> getShapes() { return shapes; }
}
