package com.nhnacademy.domain.model;

import com.nhnacademy.view.Circle;
import javafx.scene.paint.Color;

public class Ball extends Circle implements Moveable, Bounceable {
    private double dx;
    private double dy;
    private boolean isPaused = false;
    private boolean isDead = false;

    public Ball(double x, double y, Color color, double radius, double dx, double dy) {
        super(x, y, color, radius);
        this.dx = dx;
        this.dy = dy;
    }

    public void setDead(boolean dead) {
        isDead = dead;
    }

    public boolean isDead() {
        return isDead;
    }

    @Override
    public double getDx() {
        return dx;
    }

    @Override
    public double getDy() {
        return dy;
    }

    @Override
    public void setDx(double dx) {
        this.dx = dx;
    }

    @Override
    public void setDy(double dy) {
        this.dy = dy;
    }

    @Override
    public void move() {
        if(!isPaused){
            x += dx;
            y += dy;
        }
    }

    @Override
    public void pause() {
        if(!isPaused) isPaused = true;
    }

    @Override
    public void resume() {
        if(isPaused) isPaused = false;
    }

    @Override
    public void bounceX() {
        dx = -dx;
    }

    @Override
    public void bounceY() {
        dy = -dy;
    }

    @Override
    public void onCollision(Collidable other) {

    }
}
