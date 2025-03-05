package com.nhnacademy.shapes;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Obstacle extends Rectangle implements Drawable, Blockable{

    public Obstacle(double x, double y, double width, double height) {
        super(x, y, width, height);
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(Color.BLACK);
        gc.fillRect(x - width / 2,y - height / 2, width, height);
    }
}
