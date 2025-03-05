package com.nhnacademy;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Paddle extends Rectangle implements Movable {
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

    public boolean checkCollision(Ball ball) {
        // 공이 패들의 경계와 충돌했는지 확인
        return ball.getX() + ball.getRadius() > x &&
                ball.getX() - ball.getRadius() < x + width &&
                ball.getY() + ball.getRadius() > y &&
                ball.getY() - ball.getRadius() < y + height;
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