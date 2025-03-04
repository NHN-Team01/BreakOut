package com.nhnacademy;

import javafx.scene.paint.Color;

public class BombBrick extends Brick {

    // 생성자
    public BombBrick(double x, double y, double width, double height) {
        super(x, y, width, height, 1);
        this.color = Color.GRAY;
        this.HP = 1;
    }

    // 공과 충돌 여부 확인
    @Override
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
            isDestroyed = true;
        }

        return collision;
    }
}
