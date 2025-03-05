package com.nhnacademy.view;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Rectangle extends Shape implements Drawable{
    public double width;
    public double height;

    public Rectangle(double x, double y, Color color, double width, double height) {
        super(x, y, color);
        this.width = width;
        this.height = height;
    }

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
