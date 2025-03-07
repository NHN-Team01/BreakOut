package com.nhnacademy;

import javafx.scene.paint.Color;

/**
 * 2번 맞아야 깨지는 벽돌
 */
public class DoubleHitBrick extends Brick {
    private int hits = 2; // 남은 타격 횟수

    public DoubleHitBrick(double x, double y, double w, double h) {
        super(x, y, w, h, Color.ORANGE);
    }

    @Override
    public boolean checkCollision(Ball ball) {
        // 아직 파괴 전이고, 부모 로직에서 충돌 발생 시
        if (!destroyed && super.checkCollision(ball)) {
            hits--;
            // 첫 번째 충돌 시: 색만 바꾸고 파괴 안 함
            if (hits == 1) {
                color = Color.RED;
                destroyed = false;
            }
            // 두 번째 충돌이면 완전히 파괴
            if (hits <= 0) destroyed = true;
            return true;
        }
        return false;
    }
}