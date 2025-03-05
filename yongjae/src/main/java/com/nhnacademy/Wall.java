package com.nhnacademy;

public class Wall extends Rectangle {

    public Wall(double x, double y, double width, double height) {
        super(x, y, width, height);
    }

    @Override
    public boolean checkCollision(Ball ball) {
        if (width == 0) {
            if (ball.getY() - ball.getRadius() <= getY() && ball.getY() + ball.getRadius() >= getY()) {
                return true;
            }
        }
        else if (height == 0) {
            if (ball.getX() - ball.getRadius() <= getX() && ball.getX() + ball.getRadius() >= getX()) {
                return true;
            }
        }
        return false;
    }
}
