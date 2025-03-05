package com.nhnacademy;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public abstract class Rectangle extends Shape {
    protected double x; // 벽돌의 x 좌표
    protected double y; // 벽돌의 y 좌표
    protected double width; // 벽돌의 너비
    protected double height; // 벽돌의 높이
    protected Color color; // 벽돌의 색상

    // 생성자
    public Rectangle(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    // 공과 충돌 여부 확인
    public abstract boolean checkCollision(Ball ball) ;

    // Getter와 Setter (필요 시 사용)
    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }
}
