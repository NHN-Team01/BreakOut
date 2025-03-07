package com.nhnacademy;

public class Wall extends Rectangle implements Collidable {

    public Wall(double x, double y, double width, double height) {
        super(x, y, width, height);
    }

    @Override
    public boolean isCollisionDetected(Shape other) {
        // Ball과 Paddle 충돌만 고려하면 됨
        if(!(other instanceof Ball)) {
            return false;
        }
        Ball ball = (Ball)other;

        // 공이 사각형의 경계와 충돌했는지 확인
        double closestX = clamp(ball.getX(), this.getMinX(), this.getMaxX());  // 공의 X좌표와 사각형의 X경계 사이의 값
        double closestY = clamp(ball.getY(), this.getMinY(), this.getMaxY()); // 공의 Y좌표와 사각형의 Y경계 사이의 값

        // 공의 중심과 사각형과의 가장 가까운 점 간의 거리를 계산
        double dx = ball.getX() - closestX;
        double dy = ball.getY() - closestY;

        // 거리가 공의 반지름보다 작으면 충돌
        boolean collision = (dx * dx + dy * dy) < (ball.getRadius() * ball.getRadius());

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

}
