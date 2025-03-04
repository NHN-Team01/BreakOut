package com.nhnacademy.shapes;

public class Rectangle extends Shape {

    public Rectangle(double x, double y, double width, double height) {
        super(x, y, width, height);
    }

    @Override
    public double getX() {
        return x;
    }

    @Override
    public double getY() {
        return y;
    }

    @Override
    public double getMinX() {
        return x - width / 2;
    }

    @Override
    public double getMaxX() {
        return x + width / 2;
    }

    @Override
    public double getMinY() {
        return y - height / 2;
    }

    @Override
    public double getMaxY() {
        return y + height / 2;
    }

}
