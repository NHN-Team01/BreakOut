package com.nhnacademy.domain.model;


public interface Collidable {

    double getMinX();
    double getMinY();
    double getMaxX();
    double getMaxY();

    default boolean isCollisionDetected(Collidable other){
        return this.getMaxX() > other.getMinX() &&
                this.getMinX() < other.getMaxX() &&
                this.getMaxY() > other.getMinY() &&
                this.getMinY() < other.getMaxY();
    };

    void onCollision(Collidable other);
}
