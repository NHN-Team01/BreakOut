package com.nhnacademy.shapes;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Brick extends Rectangle implements Drawable {
    private Color color; // 벽돌의 색상
    private boolean isDestroyed; // 벽돌이 파괴되었는지 여부

    // 생성자
    public Brick(double x, double y, double width, double height, Color color) {
        super(x, y, width, height);
        this.color = color;
        this.isDestroyed = false; // 초기 상태는 파괴되지 않음
    }

    @Override
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
            isDestroyed = true; // 벽돌 파괴
        }

        return collision;
    }

    public boolean isDestroyed() {
        return isDestroyed;
    }

}
