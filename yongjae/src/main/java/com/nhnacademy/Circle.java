package com.nhnacademy;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Circle {
    protected double x; // 공의 현재 x 좌표
    protected double y; // 공의 현재 y 좌표
    protected double radius; // 공의 반지름
    protected Color color; // 공의 색상

    // 생성자
    public Circle(double x, double y, double radius, Color color) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.color = color;
    }

    // 공을 그리는 메서드
    public void draw(GraphicsContext gc) {
        gc.setFill(color);
        gc.fillOval(x - radius, y - radius, radius * 2, radius * 2); // 중심을 기준으로 원 그리기
    }



    // Getter와 Setter (필요 시 사용)
    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getRadius() {
        return radius;
    }

}