package com.nhnacademy;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Brick extends Rectangle {
    protected boolean isDestroyed; // 벽돌이 파괴되었는지 여부
    protected int HP;
    protected int score;

    // 생성자
    public Brick(double x, double y, double width, double height, int HP) {
        super(x, y, width, height);
        this.HP = HP;
        switch (HP) {
            case 1 -> color = Color.RED;
            case 2 -> color = Color.ORANGE;
            case 3 -> color = Color.YELLOW;
            case 4 -> color = Color.GREEN;
            case 5 -> color = Color.BLUE;
        }
        this.isDestroyed = false; // 초기 상태는 파괴되지 않음
        this.score = 50 * HP;
    }

    // 벽돌을 그리는 메서드
    public void draw(GraphicsContext gc) {
        if (!isDestroyed) {
            super.draw(gc);
        }
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

    public int getScore() {return score;}
}
