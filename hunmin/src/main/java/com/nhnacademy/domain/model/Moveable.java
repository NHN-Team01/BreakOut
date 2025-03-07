package com.nhnacademy.domain.model;


public interface Moveable {
    double getDx();
    double getDy();
    void setDx(double dx);
    void setDy(double dy);

    void move();
    void pause();
    void resume();

}
