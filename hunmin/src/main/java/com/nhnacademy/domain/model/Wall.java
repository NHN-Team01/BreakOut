package com.nhnacademy.domain.model;

import com.nhnacademy.domain.data.WallType;
import com.nhnacademy.view.Rectangle;
import javafx.scene.paint.Color;

public class Wall extends Rectangle implements Collidable{
    private final WallType type;

    public Wall(double x, double y, double width, double height, WallType type) {
        super(x, y, Color.BLACK, width, height);
        this.type = type;
    }

    @Override
    public void onCollision(Collidable other) {
        if (!(other instanceof Ball ball)) return;

        switch (type) {
            case LEFT, RIGHT:
                ball.bounceX();
                break;
            case TOP:
                ball.bounceY();
                break;
        }
    }
}
