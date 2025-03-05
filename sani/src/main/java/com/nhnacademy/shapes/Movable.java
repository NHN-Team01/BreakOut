package com.nhnacademy.shapes;

public interface Movable {
    void move();

    double getDx();

    void setDx(double dx);

    double getDy();

    void setDy(double dy);

    void pause();

    void resume();

    boolean isCollisionDetected(Shape other);

    void revertPosition(); // 추가된 메서드
}
