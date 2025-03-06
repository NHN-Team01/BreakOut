package com.nhnacademy.shapes;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Ball extends Circle implements Drawable, Movable{
    private double dx; // 공의 x축 속도 (단위: 픽셀/프레임)
    private double dy; // 공의 y축 속도 (단위: 픽셀/프레임)
    private Color color; // 공의 색상

    // 생성자
    public Ball(double x, double y, double radius, double dx, double dy, Color color) {
        super(x,y,radius);
        this.dx = dx;
        this.dy = dy;
        this.color = color;
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(color);
        gc.fillOval(x - radius, y - radius, radius * 2, radius * 2); // 중심을 기준으로 원 그리기
    }

    public void bounce(Shape other, double closestX, double closestY) {
        if (closestX == other.getMaxX() || closestX == other.getMinX()) {
            dx = -dx;
        }
        if (closestY == other.getMaxY() || closestY == other.getMinY()) {
            dy = -dy;
            if (other instanceof Paddle paddle) {
                double paddleVelocity = paddle.getDx();
                double maxSpeed = 8;
                if (dx + paddleVelocity * 0.2 > maxSpeed) {
                    dx = maxSpeed;
                } else {
                    dx += paddleVelocity * 0.2;
                }
            }
        }
        repositionAfterCollision(other);
    }

    @Override
    public boolean isCollisionDetected(Shape other) {
        // 원의 중심에서 가장 가까운 사각형 위의 점 찾기
        double closestX = Math.max(other.getMinX(), Math.min(x, other.getMaxX()));
        double closestY = Math.max(other.getMinY(), Math.min(y, other.getMaxY()));

        // 원의 중심과 가장 가까운 점 사이의 거리 계산
        double distanceX = x - closestX;
        double distanceY = y - closestY;
        double distanceSquared = distanceX * distanceX + distanceY * distanceY;

        // 거리가 반지름보다 작거나 같으면 충돌
        boolean isCollision = distanceSquared <= (radius * radius);

        if (isCollision) {
            if (other instanceof Blockable) {
                return true;
            }
            bounce(other, closestX, closestY);
        }
        return isCollision;
    }
    public double getDx() {
        return dx;
    }

    @Override
    public void setDx(double dx) {
        this.dx = dx;
    }

    @Override
    public void move() {
        x += dx;
        y += dy;
    }

    private void repositionAfterCollision(Shape other) {
        double closestX = Math.max(other.getMinX(), Math.min(x, other.getMaxX()));
        double closestY = Math.max(other.getMinY(), Math.min(y, other.getMaxY()));

        double vx = x - closestX;
        double vy = y - closestY;

        double distance = Math.sqrt(vx * vx + vy * vy);

        if (distance < radius) {
            double nx = vx / distance;
            double ny = vy / distance;

            double overlap = radius - distance;
            x += nx * (overlap+2);
            y += ny * (overlap+2);
        }
    }
}