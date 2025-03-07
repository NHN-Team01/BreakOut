package com.nhnacademy;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * 장애물(Obstacle): Ball 충돌 시 특정 동작 가능
 */
public class Obstacle extends Shape implements Drawable, Blockable {
    private double width, height;
    private Color color;

    public Obstacle(double x, double y, double width, double height, Color color) {
        super(x, y);
        this.width = width;
        this.height = height;
        this.color = color;
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(color);
        gc.fillRect(x, y, width, height);
    }

    /**
     * Ball과 충돌 여부 판정
     */
    public boolean checkCollision(Ball ball) {
        double ballX = ball.getX();
        double ballY = ball.getY();
        double r = ball.getRadius();
        return (ballX + r > x) && (ballX - r < x + width)
                && (ballY + r > y) && (ballY - r < y + height);
    }

    // Getter
    public double getX() { return x; }
    public double getY() { return y; }
    public double getWidth() { return width; }
    public double getHeight() { return height; }
}