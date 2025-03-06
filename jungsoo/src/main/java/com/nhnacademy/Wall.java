package com.nhnacademy;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * 벽(Wall): 화면 상/하/좌/우 경계를 표현
 */
public class Wall extends Shape implements Drawable {
    private double width, height;
    private Color color;
    private boolean isHorizontal;

    public Wall(double x, double y, double width, double height, Color color, boolean isHorizontal) {
        super(x, y);
        this.width = width;
        this.height = height;
        this.color = color;
        this.isHorizontal = isHorizontal;
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(color);
        gc.fillRect(x, y, width, height);
    }

    /**
     * Ball과 충돌 여부 체크
     */
    public boolean checkCollision(Ball ball) {
        double bx = ball.getX(), by = ball.getY(), br = ball.getRadius();
        return (bx + br > x) && (bx - br < x + width)
                && (by + br > y) && (by - br < y + height);
    }

    public boolean isHorizontal() { return isHorizontal; }
    public double getHeight() { return height; }
    public double getWidth() { return width; }
}