package com.nhnacademy;

/**
 * Shape (추상 클래스)
 * <p>
 * - x, y 좌표를 공통으로 갖는 2D 도형의 기본
 * - Ball, Brick, Paddle 등 다양한 구체 클래스가 이를 상속받아 사용
 * - 화면에서의 위치 관련 필드와, getter를 제공
 */
public abstract class Shape {
    // 2D 상의 위치
    protected double x, y;

    /**
     * @param x 초기 X 좌표
     * @param y 초기 Y 좌표
     */
    public Shape(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /** @return 현재 X 좌표 */
    public double getX() {
        return x;
    }
    /** @return 현재 Y 좌표 */
    public double getY() {
        return y;
    }
}