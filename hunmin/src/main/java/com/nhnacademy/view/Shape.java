package com.nhnacademy.view;

import javafx.scene.paint.Color;

/**
 * 공통적인 속성을 가지는 추상클래스 Shape
 * 해당 클래스를 구현하는 클래스는 반드시 x, y 값의 최소값 최대값을 반환하는 메서드를 작성해야 한다
 *
 */
public abstract class Shape {
    public double x;
    public double y;
    public Color color;

    public Shape(double x, double y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public Color getColor() {
        return color;
    }

    abstract public double getMinX();
    abstract public double getMaxX();
    abstract public double getMinY();
    abstract public double getMaxY();
}
