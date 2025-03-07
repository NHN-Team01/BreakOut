package com.nhnacademy;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * 공(Ball) 클래스
 * - Shape을 상속받아 위치(x, y) 정보를 이용
 * - Drawable, Movable, Bounceable 인터페이스를 구현하여 그리기, 이동, 반사 기능 제공
 */
public class Ball extends Shape implements Drawable, Movable, Bounceable {
    private double radius;      // 공의 반지름
    private double dx, dy;      // x, y 방향 속도
    private Color color;        // 공의 색상
    private boolean isPaused = false; // 일시정지 여부

    /**
     * 생성자
     * @param x 초기 x 좌표(중심)
     * @param y 초기 y 좌표(중심)
     * @param radius 공의 반지름
     * @param dx x축 속도
     * @param dy y축 속도
     * @param color 공의 색상
     */
    public Ball(double x, double y, double radius, double dx, double dy, Color color) {
        super(x, y); // 부모인 Shape에서 x, y 초기화
        this.radius = radius;
        this.dx = dx;
        this.dy = dy;
        this.color = color;
    }

    /**
     * 공을 화면에 그리는 메서드
     * - (x, y)를 중심으로 하여 원을 그림
     */
    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(color);
        gc.fillOval(x - radius, y - radius, radius * 2, radius * 2);
    }

    /**
     * 이동 메서드 (일시정지 상태가 아니면 속도만큼 이동)
     */
    @Override
    public void move() {
        if (!isPaused) {
            x += dx;
            y += dy;
        }
    }

    // Movable 인터페이스 구현
    @Override
    public double getDx() { return dx; }
    @Override
    public double getDy() { return dy; }
    @Override
    public void setDx(double dx) { this.dx = dx; }
    @Override
    public void setDy(double dy) { this.dy = dy; }
    @Override
    public void pause()  { isPaused = true; }
    @Override
    public void resume() { isPaused = false; }

    /**
     * 다른 Shape(주로 Brick)와 충돌 여부를 박스 방식으로 판단
     */
    @Override
    public boolean isCollisionDetected(Shape other) {
        if (other instanceof Brick brick) {
            return (x + radius > brick.getX())
                    && (x - radius < brick.getX() + brick.getWidth())
                    && (y + radius > brick.getY())
                    && (y - radius < brick.getY() + brick.getHeight());
        }
        return false;
    }

    // Bounceable 인터페이스 구현: 각 축 또는 두 축을 동시에 반전
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
     * 패들과 충돌 시, Y축 반사 후 공의 X속도를 조정하는 메서드
     */
    public void bounceOffPaddle(Paddle paddle) {
        if (paddle.checkCollision(this)) {
            bounceY();
            // 패들의 중심과의 상대적 위치에 따라 X속도를 조정
            double paddleCenter = paddle.getX();
            double relativeIntersect = (x - paddleCenter) / (paddle.getWidth() / 2);
            dx = relativeIntersect * 5;
        }
    }

    // Getter / Setter 추가
    public double getRadius() {
        return radius;
    }

    public void setX(double newX) {
        this.x = newX;
    }

    public void setY(double newY) {
        this.y = newY;
    }
}
