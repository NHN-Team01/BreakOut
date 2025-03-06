package com.nhnacademy;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * 공(Ball): 화면에서 이동하며 반사 가능한 객체
 */
public class Ball extends Shape implements Drawable, Movable, Bounceable {
    private double radius;      // 공의 반지름
    private double dx, dy;      // 이동 속도(x, y)
    private Color color;        // 공 색상
    private boolean isPaused=false;

    /**
     * @param x 공의 초기 X 좌표
     * @param y 공의 초기 Y 좌표
     * @param radius 공의 반지름
     * @param dx 초기 x속도
     * @param dy 초기 y속도
     * @param color 공 색상
     */
    public Ball(double x, double y, double radius, double dx, double dy, Color color) {
        super(x, y);
        this.radius = radius;
        this.dx = dx;
        this.dy = dy;
        this.color = color;
    }

    // 그리기
    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(color);
        gc.fillOval(x - radius, y - radius, radius*2, radius*2);
    }

    // 이동 (일시정지 상태면 멈춤)
    @Override
    public void move() {
        if (!isPaused) {
            x += dx;
            y += dy;
        }
    }

    // ============= Bounceable =============
    @Override
    public void bounceX() { dx = -dx; }
    @Override
    public void bounceY() { dy = -dy; }
    @Override
    public void bounceXY() {
        dx = -dx;
        dy = -dy;
    }

    /**
     * 패들과 충돌 시 Y축 반사 및 X속도 조정
     */
    public void bounceOffPaddle(Paddle paddle) {
        if (paddle.checkCollision(this)) {
            bounceY();
            double paddleCenter = paddle.getX();
            double relativeIntersect = (x - paddleCenter) / (paddle.getWidth() / 2);
            dx = relativeIntersect * 5;
        }
    }

    // ============= Movable 구현 =============
    @Override
    public boolean isCollisionDetected(Shape other) {
        // 간단한 박스 충돌
        if (other instanceof Brick brick) {
            return (x + radius > brick.getX()) &&
                    (x - radius < brick.getX() + brick.getWidth()) &&
                    (y + radius > brick.getY()) &&
                    (y - radius < brick.getY() + brick.getHeight());
        }
        return false;
    }

    @Override
    public double getDx() { return dx; }
    @Override
    public double getDy() { return dy; }
    @Override
    public void setDx(double dx) { this.dx = dx; }
    @Override
    public void setDy(double dy) { this.dy = dy; }

    @Override
    public void pause() { isPaused = true; }
    @Override
    public void resume() { isPaused = false; }

    // ============= Getter / Setter =============
    public double getRadius() { return radius; }
    public void setX(double x){ this.x = x; }
    public void setY(double y){ this.y = y; }
}