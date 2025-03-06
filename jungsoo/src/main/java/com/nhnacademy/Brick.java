package com.nhnacademy;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * 벽돌(일반). 공에 맞으면 파괴 상태가 됨
 */
public class Brick extends Shape implements Drawable {
    protected double width, height;
    protected boolean destroyed;
    protected Color color;

    public Brick(double x, double y, double w, double h, Color color) {
        super(x, y);
        this.width = w;
        this.height = h;
        this.color = color;
        this.destroyed = false;
    }

    public boolean isDestroyed() {
        return destroyed;
    }
    public void destroy() {
        destroyed = true;
    }
    public double getWidth() {
        return width;
    }
    public double getHeight() {
        return height;
    }

    /**
     * 공과 충돌 여부 확인 & 충돌 시 파괴 처리
     */
    public boolean checkCollision(Ball ball) {
        if (destroyed) return false;
        double bx = ball.getX(), by = ball.getY(), r = ball.getRadius();
        boolean collision = (bx + r > x) && (bx - r < x + width)
                && (by + r > y) && (by - r < y + height);
        if (collision) {
            destroy();
            ball.setDy(-ball.getDy()); // 공 간단히 반사
        }
        return collision;
    }

    @Override
    public void draw(GraphicsContext gc) {
        if (!destroyed) {
            gc.setFill(color);
            gc.fillRect(x, y, width, height);
        }
    }
}