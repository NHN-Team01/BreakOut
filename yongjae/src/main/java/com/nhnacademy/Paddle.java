package com.nhnacademy;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Paddle extends Rectangle implements Drawable, Movable {
    private double speed; // 패들의 이동 속도
    private boolean paused;
    private boolean moveLeft = false;
    private boolean moveRight = false;
    // 생성자
    public Paddle(double x, double y, double width, double height, double speed, Color color) {
        super(x, y, width, height);
        this.speed = speed;
        this.color = color;
    }

    // 벽돌을 그리는 메서드
    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(color);
        gc.fillRect(x, y, width, height); // 벽돌 그리기
    }

    // Getter와 Setter (필요 시 사용)
    public void setWidth(double width) {
        this.width = width;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setMoveLeft(boolean moveLeft) {
        this.moveLeft = moveLeft;
    }

    public boolean getMoveLeft() {
        return moveLeft;
    }

    public void setMoveRight(boolean moveRight) {
        this.moveRight = moveRight;
    }

    public boolean getMoveRight() {
        return moveRight;
    }

    @Override
    public void move() {
        if (!paused && (moveLeft || moveRight)) {
            x += speed;
        }
    }

    @Override
    public double getDx() {
        return speed;
    }

    @Override
    public double getDy() {
        return 0;
    }

    @Override
    public void setDx(double dx) {
        speed = dx;
    }

    @Override
    public void setDy(double dy) {
    }

    @Override
    public void pause() {
        paused = true;
    }

    @Override
    public void resume() {
        paused = false;
    }

    @Override
    public boolean isCollisionDetected(Shape other) {
        if (other instanceof Wall wall) {
            if (wall.getWidth() == 0) {
                if (wall.getX() == 0 &&  x < 0) {
                    x = 0;
                }
                if (wall.getX() == 800 && getMaxX() > 800) {
                    x = 800 - width;
                }
                return true;
            }
        }
        else if (other instanceof Ball ball) {
            return ball.getX() + ball.getRadius() > x &&
                    ball.getX() - ball.getRadius() < x + width &&
                    ball.getY() + ball.getRadius() > y &&
                    ball.getY() - ball.getRadius() < y + height;
        }
        return false;
    }
}