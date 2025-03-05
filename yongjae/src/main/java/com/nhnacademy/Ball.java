package com.nhnacademy;

import javafx.scene.paint.Color;

public class Ball extends Circle {
    private double dx; // 공의 x축 속도 (단위: 픽셀/프레임)
    private double dy; // 공의 y축 속도 (단위: 픽셀/프레임)

    // 생성자
    public Ball(double x, double y, double radius, double dx, double dy, Color color) {
        super(x, y, radius, color);
        this.dx = dx;
        this.dy = dy;
    }

    // 공의 위치를 업데이트하는 메서드
    public void update() {
        x += dx; // x축 위치 업데이트
        y += dy; // y축 위치 업데이트
    }

    // 공이 화면 경계와 충돌했는지 확인 및 속도 반전
    public void checkCollision(double canvasWidth, double canvasHeight) {
        // 좌우 경계 충돌
        if (x - radius <= 0 || x + radius >= canvasWidth) {
            dx = -dx; // x축 속도 반전
        }
        // 상하 경계 충돌
        if (y - radius <= 0) {
            dy = -dy; // y축 속도 반전
        }
    }

    public double getDx() {
        return dx;
    }

    public double getDy() { return dy; }

    public void setDx(double dx) {
        this.dx = dx;
    }

    public void setDy(double dy) {
        this.dy = dy;
    }
}