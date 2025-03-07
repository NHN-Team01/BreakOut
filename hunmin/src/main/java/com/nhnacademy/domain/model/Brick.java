package com.nhnacademy.domain.model;


import com.nhnacademy.view.Rectangle;
import javafx.scene.paint.Color;

public class Brick extends Rectangle implements Collidable, Breakable {
    private boolean isDestroyed = false;

    public Brick(double x, double y, Color color, double width, double height) {
        super(x, y, color, width, height);
    }

    @Override
    public boolean isBroken() {
        return isDestroyed;
    }

    @Override
    public void onCollision(Collidable other) {
        if (!(other instanceof Ball ball)) return;

        isDestroyed = true;

        // TODO : 코너 충돌로직 이게 맞나?
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
}
