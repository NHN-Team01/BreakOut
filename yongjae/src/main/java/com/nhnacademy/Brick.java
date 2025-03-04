package com.nhnacademy;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Brick {
    protected double x; // 벽돌의 x 좌표
    protected double y; // 벽돌의 y 좌표
    protected double width; // 벽돌의 너비
    protected double height; // 벽돌의 높이
    protected Color color; // 벽돌의 색상
    protected boolean isDestroyed; // 벽돌이 파괴되었는지 여부
    protected int HP;

    // 생성자
    public Brick(double x, double y, double width, double height, int HP) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.HP = HP;
        switch (HP) {
            case 1 -> color = Color.RED;
            case 2 -> color = Color.ORANGE;
            case 3 -> color = Color.YELLOW;
            case 4 -> color = Color.GREEN;
            case 5 -> color = Color.BLUE;
        }
        this.isDestroyed = false; // 초기 상태는 파괴되지 않음
    }

    // 벽돌을 그리는 메서드
    public void draw(GraphicsContext gc) {
        if (!isDestroyed) {
            gc.setFill(color);
            gc.fillRect(x, y, width, height); // 벽돌 그리기
        }
    }

    // 공과 충돌 여부 확인
    public boolean checkCollision(Ball ball) {
        if (isDestroyed) {
            return false; // 이미 파괴된 벽돌은 충돌하지 않음
        }

        double ballX = ball.getX();
        double ballY = ball.getY();
        double ballRadius = ball.getRadius();

        // 공이 벽돌의 경계와 충돌했는지 확인
        boolean collision = ballX + ballRadius > x &&
                ballX - ballRadius < x + width &&
                ballY + ballRadius > y &&
                ballY - ballRadius < y + height;

        if (collision) {
            HP--;
            switch (HP) {
                case 0 -> isDestroyed = true;
                case 1 -> color = Color.RED;
                case 2 -> color = Color.ORANGE;
                case 3 -> color = Color.YELLOW;
                case 4 -> color = Color.GREEN;
            }
        }

        return collision;
    }

    // Getter와 Setter (필요 시 사용)
    public boolean isDestroyed() {
        return isDestroyed;
    }

    public void setDestroyed(boolean destroyed) {
        isDestroyed = destroyed;
    }

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
