package com.nhnacademy;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Paddle extends Rectangle implements Drawable {
    private double speed; // 패들의 이동 속도

    // 생성자
    public Paddle(double x, double y, double width, double height, double speed, Color color) {
        super(x, y, width, height);
        this.speed = speed;
        this.color = color;
    }

    // 벽돌을 그리는 메서드
    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(color);
        gc.fillRect(x, y, width, height); // 벽돌 그리기
    }


    // 패들의 위치를 왼쪽으로 이동
    public void moveLeft() {
        x -= speed;
    }

    // 패들의 위치를 오른쪽으로 이동
    public void moveRight() {
        x += speed;
    }

    @Override
    public boolean checkCollision(Ball ball) {
        // 공이 패들의 경계와 충돌했는지 확인
        return ball.getX() + ball.getRadius() > x &&
                ball.getX() - ball.getRadius() < x + width &&
                ball.getY() + ball.getRadius() > y &&
                ball.getY() - ball.getRadius() < y + height;
    }

    // 패들이 화면 경계를 벗어나지 않도록 제한
    public void checkBounds(double canvasWidth) {
        if (x < 0) { // 왼쪽 경계
            x = 0;
        } else if (x + width > canvasWidth) { // 오른쪽 경계
            x = canvasWidth - width;
        }
    }

    // Getter와 Setter (필요 시 사용)
    public void setWidth(double width) {
        this.width = width;
    }
}