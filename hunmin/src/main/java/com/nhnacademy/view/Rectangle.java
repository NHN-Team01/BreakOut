package com.nhnacademy.view;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Rectangle extends Shape implements Drawable{
    protected double width;
    protected double height;

    public Rectangle(double x, double y, Color color, double width, double height) {
        super(x, y, color);
        this.width = width;
        this.height = height;
    }


    /**
     * 좌측 상단을 기준으로 사각형을 그리는 메서드
     * @param gc GraphicsContext 객체
     */
    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(color);
        gc.fillRect(x - width/2, y - height/2, width, height);
    }


    @Override
    public double getMinX() {
        return x-width/2;
    }

    @Override
    public double getMaxX() {
        return x+width/2;
    }

    @Override
    public double getMinY() {
        return y-height/2;
    }

    @Override
    public double getMaxY() {
        return y+height/2;
    }
}
