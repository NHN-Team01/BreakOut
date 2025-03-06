package com.nhnacademy.shapes;

public interface Movable {
    void move();

    double getDx();

    void setDx(double dx);

    boolean isCollisionDetected(Shape other);

}
