package com.nhnacademy;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Paddle {
    private double x; // 패들의 중심 x 좌표
    private double y; // 패들의 중심 y 좌표
    private double width; // 패들의 너비
    private double height; // 패들의 높이
    private double speed; // 패들의 이동 속도
    private Color color; // 패들의 색상

    // 생성자
    public Paddle(double x, double y, double width, double height, double speed, Color color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.speed = speed;
        this.color = color;
    }

    // 패들을 그리는 메서드
    public void draw(GraphicsContext gc) {
        gc.setFill(color);
        gc.fillRect(x - width / 2, y - height / 2, width, height); // 중심을 기준으로 사각형 그리기
    }

    // 패들의 위치를 왼쪽으로 이동
    public void moveLeft() {
        x -= speed;
    }

    // 패들의 위치를 오른쪽으로 이동
    public void moveRight() {
        x += speed;
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

    // Getter와 Setter (필요 시 사용)
    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }
}