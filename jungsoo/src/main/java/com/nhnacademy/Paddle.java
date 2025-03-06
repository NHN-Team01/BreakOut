package com.nhnacademy;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Paddle extends Shape implements Drawable, Movable {
    private double width;
    private double height;
    private double speed;
    private double dx = 0;
    private Color color;
    private boolean isPaused = false;

    public Paddle(double x, double y, double width, double height, double speed, Color color) {
        super(x, y);
        this.width = width;
        this.height = height;
        this.speed = speed;
        this.color = color;
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(color);
        // 패들이 (x, y) 중심이 되도록 그리기
        gc.fillRect(x - width / 2, y - height / 2, width, height);
    }

    @Override
    public void move() {
        if (!isPaused) {
            x += dx;
            checkBounds(800); // 화면 폭이 800
        }
    }

    private void checkBounds(double canvasWidth) {
        if (x - width / 2 < 0) {
            x = width / 2;
        }
        else if (x + width / 2 > canvasWidth) {
            x = canvasWidth - width / 2;
        }
    }

    public void moveLeft() {
        dx = -speed;
    }
    public void moveRight() {
        dx = speed;
    }
    public void stopMoving() {
        dx = 0;
    }

    // 공과 충돌했는지 여부
    public boolean checkCollision(Ball ball) {
        double ballBottom = ball.getY() + ball.getRadius();
        double paddleTop = y - height / 2;

        boolean collisionX = (ball.getX() + ball.getRadius() > x - width / 2) &&
                (ball.getX() - ball.getRadius() < x + width / 2);
        boolean collisionY = (ballBottom >= paddleTop);
        return collisionX && collisionY;
    }

    // Movable 구현
    @Override
    public boolean isCollisionDetected(Shape other) {
        if (other instanceof Ball ball) {
            double r = ball.getRadius();
            return (ball.getX() + r > (x - width / 2)) &&
                    (ball.getX() - r < (x + width / 2)) &&
                    (ball.getY() + r > (y - height / 2)) &&
                    (ball.getY() - r < (y + height / 2));
        }
        return false;
    }

    @Override
    public double getDx() {
        return dx;
    }
    @Override
    public double getDy() {
        return 0;
    }
    @Override
    public void setDx(double dx) {
        this.dx = dx;
    }
    @Override
    public void setDy(double dy) {
        // 패들은 y축 이동 없음
    }
    @Override
    public void pause() {
        isPaused = true;
    }
    @Override
    public void resume() {
        isPaused = false;
    }

    // Getter
    public double getWidth() {
        return width;
    }
    public double getHeight() {
        return height;
    }
    public double getSpeed() {
        return speed;
    }
}