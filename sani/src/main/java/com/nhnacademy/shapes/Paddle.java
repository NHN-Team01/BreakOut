package com.nhnacademy.shapes;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Paddle extends Rectangle implements Drawable, Movable{
    private double speed; // 패들의 이동 속도
    private Color color; // 패들의 색상
    private double dx;
    private boolean paused;
    private double prevX;

    // 생성자
    public Paddle(double x, double y, double width, double height, double speed, Color color) {
        super(x, y, width, height);
        this.speed = speed;
        this.color = color;
        this.paused = false;
        this.dx = 0;
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(color);
        gc.fillRect(x - width / 2, y - height / 2, width, height); // 중심을 기준으로 사각형 그리기
    }

    // 패들의 위치를 왼쪽으로 이동
    public void moveLeft() {
        setDx(-speed);
    }

    // 패들의 위치를 오른쪽으로 이동
    public void moveRight() {
        setDx(speed);
    }

    @Override
    public void move() {
        if(!paused) {
            prevX = x;
            x += dx;
        }
    }

    @Override
    public void revertPosition() {
        x = prevX;
    }

    @Override
    public double getDy() {
        return 0;
    }

    @Override
    public void setDy(double dy) {

    }

    @Override
    public double getDx() {
        return dx;
    }

    @Override
    public void setDx(double dx) {
        this.dx = dx;
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
        boolean isCollision = other.getMinX() < getMaxX() &&
                other.getMaxX() > getMinX() &&
                other.getMinY() < getMaxY() &&
                other.getMaxY() > getMinY();

        if (isCollision) {
            if (other instanceof Wall) {
                if (getDx() > 0) {
                    x = other.getMinX() - width / 2;
                } else if (getDx() < 0) {
                    x = other.getMaxX() + width / 2;
                }

                setDx(0);
            }
            return true;
        }
        return false;
    }
}