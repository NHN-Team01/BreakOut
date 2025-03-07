package com.nhnacademy.domain.model;

import com.nhnacademy.view.Rectangle;
import javafx.scene.paint.Color;

public class Obstacle extends Rectangle implements Blockable {

    public Obstacle(double x, double y, double width, double height) {
        super(x, y, Color.BLACK, width, height);
    }

    public void onCollision(Collidable other) {
        if(other instanceof Ball ball)
            ball.setDead(true);
    }

}
