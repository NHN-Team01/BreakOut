package com.nhnacademy;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Brick extends Rectangle implements Breakable {
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

        // 공이 벽돌의 경계와 충돌했는지 확인
        double closestX = clamp(ball.getX(), this.getMinX(), this.getMaxX());  // 공의 X좌표와 사각형의 X경계 사이의 값
        double closestY = clamp(ball.getY(), this.getMinY(), this.getMaxY()); // 공의 Y좌표와 사각형의 Y경계 사이의 값

        // 공의 중심과 사각형과의 가장 가까운 점 간의 거리를 계산
        double dx = ball.getX() - closestX;
        double dy = ball.getY() - closestY;

        // 거리가 공의 반지름보다 작으면 충돌
        boolean collision = (dx * dx + dy * dy) < (ball.getRadius() * ball.getRadius());

        if (collision) {
            isDestroyed = true; // 벽돌 파괴
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

    @Override
    public void hit() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'hit'");
    }

    @Override
    public boolean isBroken() {
        return isDestroyed;
    }
}
