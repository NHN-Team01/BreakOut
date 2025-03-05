package com.nhnacademy;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Paddle extends Rectangle implements Drawable, Movable {
    private double speed; // 패들의 이동 속도
    private boolean paused;
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


    // 패들이 화면 경계를 벗어나지 않도록 제한
    public void checkBounds(double canvasWidth) {
        if (x < 0) { // 왼쪽 경계
            x = 0;
        } else if (x + width > canvasWidth) { // 오른쪽 경계
            x = canvasWidth - width;
        }
    }

    // Getter와 Setter (필요 시 사용)
    public void setWidth(double width) {
        this.width = width;
    }

    @Override
    public void move() {
        if (!paused) {
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
        if (other instanceof Wall) {
            Wall wall = (Wall) other;
            if (wall.width == 0 && (this.x < wall.x && wall.x < this.x + this.width)) {
                return true;
            }
        }
        else if (other instanceof Ball) {
            Ball ball = (Ball) other;
            return ball.getX() + ball.getRadius() > x &&
                    ball.getX() - ball.getRadius() < x + width &&
                    ball.getY() + ball.getRadius() > y &&
                    ball.getY() - ball.getRadius() < y + height;
        }
        return false;
    }
}