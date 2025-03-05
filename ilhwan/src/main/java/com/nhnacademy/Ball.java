package com.nhnacademy;

import javafx.scene.paint.Color;

public class Ball extends Circle implements Movable {
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
}