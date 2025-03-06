package com.nhnacademy;

import java.util.List;

import javafx.scene.paint.Color;

public class Ball extends Circle implements Bounceable {
    private double dx; // 공의 x축 속도 (단위: 픽셀/프레임)
    private double dy; // 공의 y축 속도 (단위: 픽셀/프레임)
    private boolean isPaused = false;

    // 생성자
    public Ball(double x, double y, double radius, double dx, double dy, Color color) {
        super(x, y, radius, color);
        this.dx = dx;
        this.dy = dy;
    }

    // 공의 위치를 업데이트하는 메서드
    @Override
    public void move() {
        if(!isPaused) {
            x += dx; // x축 위치 업데이트
            y += dy; // y축 위치 업데이트
        }
    }

    // 공이 화면 경계와 충돌했는지 확인 및 속도 반전
    public void checkCollision(double canvasWidth, double canvasHeight) {
        // 좌우 경계 충돌
        if (getMinX() <= 0 || getMaxX() >= canvasWidth) {
            dx = -dx; // x축 속도 반전
        }
        // 상하 경계 충돌
        if (getMinY() <= 0 || getMaxY() >= canvasHeight) {
            dy = -dy; // y축 속도 반전
        }
    }

    /**
     * 공이 화면 하단 경계에 닿았는지 확인하는 메서드
     * 
     * @param canvasHeight 화면의 높이
     * @return 공이 하단 경계에 닿았다면 true, 아니면 false
     */
    public boolean isAtBottom(double canvasHeight) {
        return getMaxY() >= canvasHeight;
    }

    @Override
    public double getDx() {
        return dx;
    }

    @Override
    public double getDy() {
        return dy;
    }

    @Override
    public void setDx(double dx) {
        this.dx = dx;
    }

    @Override
    public void setDy(double dy) {
        this.dy = dy;
    }

    @Override
    public void pause() {
        isPaused = true;
    }

    @Override
    public void resume() {
        isPaused = false;
    }

    @Override
    public void bounce(List<Shape> shapes) {
        if(shapes == null) throw new IllegalArgumentException("매개변수는 null이 아니어야함");

        for(Shape shape : shapes) {
            if(shape instanceof Collidable) {
                Collidable collidable = (Collidable)shape;
                if((collidable instanceof Rectangle) && collidable.isCollisionDetected(this)) {
                    Rectangle rect = (Rectangle)collidable;

                    // Ball의 이전 위치 계산 (현재 위치 - 이동거리)
                    double prevX = this.getX() - dx;
                    double prevY = this.getY() - dy;

                    boolean wasLeft = prevX < rect.getMinX(); // 공이 충돌 전 왼쪽에 있었는가?
                    boolean wasRight = prevX > rect.getMaxX(); // 공이 충돌 전 오른쪽에 있었는가?
                    boolean wasAbove = prevY < rect.getMinY(); // 공이 충돌 전 위쪽에 있었는가?
                    boolean wasBelow = prevY > rect.getMaxY(); // 공이 충돌 전 아래쪽에 있었는가?

                    if((wasLeft && dx > 0) || (wasRight && dx < 0)) {
                        bounceX(); // 좌우 충돌이면 X축 반전
                    }
                    if((wasAbove && dy > 0) || (wasBelow && dy < 0)) {
                        bounceY(); // 상하 충돌이면 Y축 반전
                    }
                    
                    return;
                }
            }
        }
    }

    @Override
    public void bounceX() {
        dx = -dx;
    }

    @Override
    public void bounceY() {
        dy = -dy;
    }
}