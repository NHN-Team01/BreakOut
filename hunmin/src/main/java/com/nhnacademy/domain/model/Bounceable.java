package com.nhnacademy.domain.model;

public interface Bounceable extends Collidable {
    void bounceX();
    void bounceY();

    default void bounceBoth(){
        bounceX();
        bounceY();
    }
}
