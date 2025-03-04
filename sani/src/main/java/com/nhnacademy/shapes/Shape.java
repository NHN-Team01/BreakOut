package com.nhnacademy.shapes;

public abstract class Shape {

    protected double x;
    protected double y;
    protected double width;
    protected double height;

    public Shape(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public abstract double getX();

    public abstract double getY();

    public abstract double getMinX();

    public abstract double getMaxX();

    public abstract double getMinY();

    public abstract double getMaxY();
}
