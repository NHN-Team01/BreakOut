package com.nhnacademy.view;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


public class Circle extends Shape implements Drawable {
    public double radius;

    public Circle(double x, double y, Color color, double radius) {
        super(x, y, color);
        this.radius = radius;
    }

    public double getRadius() {
        return radius;
    }

    /**
     * 중심을 기준으로 원그리기
     * @param gc
     */
    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(color);
        gc.fillOval(getMinX(), getMinY(), radius * 2, radius * 2);
    }

    @Override
    public double getMinX() {
        return x-radius;
    }

    @Override
    public double getMaxX() {
        return x+radius;
    }

    @Override
    public double getMinY() {
        return y-radius;
    }

    @Override
    public double getMaxY() {
        return y+radius;
    }
}
