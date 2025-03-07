package com.nhnacademy.domain.model;

import com.nhnacademy.view.Drawable;
import com.nhnacademy.view.Rectangle;
import javafx.scene.paint.Color;

public class Paddle extends Rectangle implements Moveable, Drawable, Collidable {
    private double dx, dy;
    private double maxX, maxY;
    private boolean moveLeft, moveRight, moveUp, moveDown;
    private boolean isPaused = false;

    public Paddle(double x, double y, Color color, double width, double height, double speed) {
        super(x, y, color, width, height);
        dx = speed;
        dy = speed;
    }

    public void checkBounds() {
        if (x - width / 2 < 0) {
            x = width / 2;
        } else if (x > maxX) {
            x = maxX;
        } else if (y - height / 2 < 0) {
            y = height / 2;
        } else if (y > maxY) {
            y = maxY;
        }
    }

    public void setBounds(double canvasWidth, double canvasHeight){
        maxX = canvasWidth - width / 2;
        maxY = canvasHeight - height / 2;
    }

    @Override
    public void onCollision(Collidable other) {
        if (!(other instanceof Ball ball)) return;

        double overlapLeft   = ball.getMaxX() - this.getMinX();
        double overlapRight  = this.getMaxX() - ball.getMinX();
        double overlapTop    = ball.getMaxY() - this.getMinY();
        double overlapBottom = this.getMaxY() - ball.getMinY();

        overlapLeft   = Math.abs(overlapLeft);
        overlapRight  = Math.abs(overlapRight);
        overlapTop    = Math.abs(overlapTop);
        overlapBottom = Math.abs(overlapBottom);

        double minOverlap = Math.min(
                Math.min(overlapLeft, overlapRight),
                Math.min(overlapTop, overlapBottom)
        );

        boolean horizontalHit = (minOverlap == overlapLeft || minOverlap == overlapRight);
        boolean verticalHit   = (minOverlap == overlapTop  || minOverlap == overlapBottom);

        if (horizontalHit && verticalHit) {
            ball.bounceBoth();
        } else if (horizontalHit) {
            ball.bounceX();
        } else {
            ball.bounceY();
        }
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
            if(moveLeft) x -= dx;
            if(moveRight) x += dx;
            if(moveUp) y -= dy;
            if(moveDown) y += dy;
            checkBounds();
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

    public void setMoveLeft(boolean val)   { moveLeft = val; }
    public void setMoveRight(boolean val)  { moveRight = val; }
    public void setMoveUp(boolean val)     { moveUp = val; }
    public void setMoveDown(boolean val)   { moveDown = val; }
}
