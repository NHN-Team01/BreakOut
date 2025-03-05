package com.nhnacademy;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Paddle extends Rectangle implements Movable, Collidable {
    private double speed;   // 패들 속도
    private double dx = 0;      // X 방향 이동속도
    private final double dy = 0;  // Y 방향 이동속도 (패들은 Y 방향으로는 이동하지 않으므로 0)
    private boolean isPaused = false;

    // 생성자
    public Paddle(double x, double y, double width, double height, double speed, Color color) {
        super(x, y, width, height, color);
        this.speed = speed;
    }

    // 패들을 그리는 메서드
    public void draw(GraphicsContext gc) {
        gc.setFill(color);
        gc.fillRect(getMinX(), getMinY(), width, height); // 중심을 기준으로 사각형 그리기
    }

    // 패들의 위치를 왼쪽으로 이동
    public void moveLeft() {
        dx = -speed;
    }

    // 패들의 위치를 오른쪽으로 이동
    public void moveRight() {
        dx = speed;
    }

    @Override
    public boolean isCollisionDetected(Shape other) {
        // Ball과의 충돌만 고려하면 됨
        if(!(other instanceof Ball)) {
            return false;
        }
        Ball ball = (Ball)other;
        
        double closestX = clamp(ball.getX(), x, x + width);  // 공의 X좌표와 사각형의 X경계 사이의 값
        double closestY = clamp(ball.getY(), y, y + height); // 공의 Y좌표와 사각형의 Y경계 사이의 값

        // 공의 중심과 사각형과의 가장 가까운 점 간의 거리를 계산
        double dx = ball.getX() - closestX;
        double dy = ball.getY() - closestY;

        // 거리가 공의 반지름보다 작으면 충돌
        boolean collision = (dx * dx + dy * dy) < (ball.getRadius() * ball.getRadius());

        if(collision) {
            // 두 객체가 겹치지 않게 충돌하지 않는 가장 가까운 위치로 이동
            // (문제를 단순화 시키기 위해 충돌 시 이전 자리로 이동)
            ball.setDx(-ball.getDx()); ball.setDy(-ball.getDy());
            ball.move();
            ball.setDx(-ball.getDx()); ball.setDy(-ball.getDy());
        }
        return collision;
    }

    /**
     * 주어진 값을 특정 범위 내로 제한하는 함수
     * @param value 제한할 값
     * @param min 값의 최소값
     * @param max 값의 최대값
     * @return 제한된 값 (value가 min가 max 사이에 위치)
     */
    private double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }

    // 패들이 화면 경계를 벗어나지 않도록 제한
    public void checkBounds(double canvasWidth) {
        if (x - width / 2 < 0) { // 왼쪽 경계
            x = width / 2;
        } else if (x + width / 2 > canvasWidth) { // 오른쪽 경계
            x = canvasWidth - width / 2;
        }
    }

    @Override
    public void move() {
        if(!isPaused) {
            x += dx;
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
        
    }

    @Override
    public void pause() {
        isPaused = true;
    }

    @Override
    public void resume() {
        isPaused = false;
    }
}