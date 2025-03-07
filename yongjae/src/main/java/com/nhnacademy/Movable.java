package com.nhnacademy;



public interface Movable {

    void move();

    double getDx();

    double getDy();

    void setDx(double dx);

    void setDy(double dy);

    void pause();

    void resume();

    boolean isCollisionDetected(Shape other);
}