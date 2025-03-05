package com.nhnacademy.shapes;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Ball extends Circle implements Drawable, Movable{
    private double dx; // 공의 x축 속도 (단위: 픽셀/프레임)
    private double dy; // 공의 y축 속도 (단위: 픽셀/프레임)
    private Color color; // 공의 색상
    private boolean paused;
    private double prevX;
    private double prevY;

    // 생성자
    public Ball(double x, double y, double radius, double dx, double dy, Color color) {
        super(x,y,radius);
        this.dx = dx;
        this.dy = dy;
        this.color = color;
        paused = false;
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(color);
        gc.fillOval(x - radius, y - radius, radius * 2, radius * 2); // 중심을 기준으로 원 그리기
    }

    // 공이 화면 경계와 충돌했는지 확인 및 속도 반전
    public void checkCollision(double canvasWidth, double canvasHeight) {
        // 좌우 경계 충돌
        if (x - radius <= 0 || x + radius >= canvasWidth) {
            dx = -dx; // x축 속도 반전
        }
        // 상하 경계 충돌
        if (y - radius <= 0 || y + radius >= canvasHeight) {
            dy = -dy; // y축 속도 반전
        }
    }

    @Override
    public boolean isCollisionDetected(Shape other) {
        // 원과 사각형의 충돌 감지
        // 원의 중심에서 가장 가까운 사각형 위의 점 찾기
        double closestX = Math.max(other.getMinX(), Math.min(x, other.getMaxX()));
        double closestY = Math.max(other.getMinY(), Math.min(y, other.getMaxY()));

        // 원의 중심과 가장 가까운 점 사이의 거리 계산
        double distanceX = x - closestX;
        double distanceY = y - closestY;
        double distanceSquared = distanceX * distanceX + distanceY * distanceY;

        // 거리가 반지름보다 작거나 같으면 충돌
        return distanceSquared <= (radius * radius);
    }

    public double getDx() {
        return dx;
    }

    @Override
    public void setDx(double dx) {
        this.dx = dx;
    }

    @Override
    public double getDy() {
        return dy;
    }

    @Override
    public void setDy(double dy) {
        this.dy = dy;
    }

    @Override
    public void move() {
        if (!paused) {
            prevX = x;
            prevY = y;
            x += dx;
            y += dy;
        }
    }

    @Override
    public void revertPosition() {
        x = prevX;
        y = prevY;
    }

    @Override
    public void pause() {
        paused = true;
    }

    @Override
    public void resume() {
        paused = false;
    }
}