package com.nhnacademy.view;

import javafx.scene.paint.Color;

/**
 * 공통적인 속성을 가지는 추상클래스 Shape
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
