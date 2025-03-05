package com.nhnacademy;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Brick extends Rectangle implements Collidable {
    private boolean isDestroyed; // 벽돌이 파괴되었는지 여부

    // 생성자
    public Brick(double x, double y, double width, double height, Color color) {
        super(x, y, width, height, color);
        this.isDestroyed = false; // 초기 상태는 파괴되지 않음
    }

    // 벽돌을 그리는 메서드
    @Override
    public void draw(GraphicsContext gc) {
        if (!isDestroyed) {
            gc.setFill(color);
            gc.fillRect(x, y, width, height); // 벽돌 그리기
        }
    }

    // 주어진 객체와 충돌 여부 확인
    @Override
    public boolean isCollisionDetected(Shape other) {
        // Ball과의 충돌만 고려하면 됨
        if(!(other instanceof Ball)) {
            return false;
        }
        if (isDestroyed) {
            return false; // 이미 파괴된 벽돌은 충돌하지 않음
        }
        Ball ball = (Ball)other;
        double ballX = ball.getX();
        double ballY = ball.getY();
        double ballRadius = ball.getRadius();

        // 공이 벽돌의 경계와 충돌했는지 확인
        boolean collision = ballX + ballRadius > x &&
                ballX - ballRadius < x + width &&
                ballY + ballRadius > y &&
                ballY - ballRadius < y + height;

        if (collision) {
            isDestroyed = true; // 벽돌 파괴
            // 두 객체가 겹치지 않게 충돌하지 않는 가장 가까운 위치로 이동
            // (문제를 단순화 시키기 위해 충돌 시 이전 자리로 이동)
            ball.setDx(-ball.getDx()); ball.setDy(-ball.getDy());
            ball.move();
            ball.setDx(-ball.getDx()); ball.setDy(-ball.getDy());
        }

        return collision;
    }

    // Getter와 Setter (필요 시 사용)
    public boolean isDestroyed() {
        return isDestroyed;
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
