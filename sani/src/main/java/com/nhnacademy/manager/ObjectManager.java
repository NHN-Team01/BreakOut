package com.nhnacademy.manager;

import com.nhnacademy.shapes.*;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class ObjectManager {
    private final List<Shape> shapes = new ArrayList<>();
    private Ball ball;
    private Paddle paddle;
//    private List<Ball> balls = new ArrayList<>();

    public ObjectManager(){
        initializeGameObjects();
    }

    public void initializeGameObjects() {
        shapes.clear();

        ball = new Ball(400, 300, 10, 3, 3, Color.RED);
        shapes.add(ball);

        paddle = new Paddle(400, 550, 100, 20, 5, Color.BLUE);
        shapes.add(paddle);

        int rows = 5;
        int cols = 8;
        double brickWidth = 95;
        double brickHeight = 20;
        double padding = 5;
        double startX = 50;
        double startY = 50;

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                double x = startX + col * (brickWidth + padding);
                double y = startY + row * (brickHeight + padding);
                shapes.add(new Brick(x, y, brickWidth, brickHeight, Color.BLUE));
            }
        }

        shapes.add(new Wall(0, 300, 10, 600)); // 왼쪽 벽
        shapes.add(new Wall(800, 300, 10, 600)); // 오른쪽 벽
        shapes.add(new Wall(400, 0, 800, 10)); // 상단 벽
        shapes.add(new Obstacle(400, 600, 800, 10)); // 하단 벽
    }

    public void removeAllByShape(List<? extends Shape> shape) {
        shapes.removeAll(shape);
    }

    public List<Shape> getShapes() {
        return shapes;
    }

    public Ball getBall() {
        return ball;
    }

    public Paddle getPaddle() {
        return paddle;
    }


}
