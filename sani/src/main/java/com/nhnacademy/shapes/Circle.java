package com.nhnacademy.shapes;

public class Circle extends Shape {

    protected double radius;

    public Circle(double x, double y, double radius) {
        super(x, y, radius * 2, radius * 2);
        this.radius = radius;
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
        return x - radius;
    }

    @Override
    public double getMaxX() {
        return x + radius;
    }

    @Override
    public double getMinY() {
        return y - radius;
    }

    @Override
    public double getMaxY() {
        return y + radius;
    }

    public double getRadius(){
        return radius;
    }
}
