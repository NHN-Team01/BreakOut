package com.nhnacademy;

/**
 * Shape 클래는 도형이 차지는 영역의 중심 좌를 제공하는 추상 클래스입니다.
 * 구체적인 도형은 자신의 영역(경계)을 어떻게 정의할지 결정해야 하므로,
 * getMinX(), getMaxX(), getMinY(), getMaxY() 메서드는 추상 메서드로 선언합니다.
 */
public abstract class Shape {
    // 도형의 중심 좌표
    protected double x; // 중심 X 좌표
    protected double y; // 중심 Y 좌표
    
    /**
     * 생성자: 중심 좌표와 영역의 크기를 초기화합니다.
     * 
     * @param x 중심 X 좌표
     * @param y 중심 Y 좌표
     */
    public Shape(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * 도형의 중심 X 좌표를 반환합니다.
     * @return 중심 X 좌표
     */
    public double getX() {
        return x;
    }

    /**
     * 도형의 중심 Y 좌표를 반환합니다.
     * @return 중심 Y 좌표
     */
    public double getY() {
        return y;
    }

    /**
     * 도형이 차지하는 영역의 최소 X 좌표를 반환합니다.
     * 구체적인 도형마다 계산 방식이 다르므로 추상 메서드로 선언합니다.
     * 
     * @return 최소 X 좌표
     */
    public abstract double getMinX();

    /**
     * 도형이 차지하는 영역의 최대 X 좌표를 반환합니다.
     * 구체적인 도형마다 계산 방식이 다르므로 추상 메서드로 선언합니다.
     * 
     * @return 최대 X 좌표
     */
    public abstract double getMaxX();

    /**
     * 도형이 차지하는 영역의 최소 Y 좌표를 반환합니다.
     * 구체적인 도형마다 계산 방식이 다르므로 추상 메서드로 선언합니다.
     * 
     * @return 최소 Y 좌표
     */
    public abstract double getMinY();

    /**
     * 도형이 차지하는 영역의 최대 Y 좌표를 반환합니다.
     * 구체적인 도형마다 계산 방식이 다르므로 추상 메서드로 선언합니다.
     * 
     * @return 최대 Y 좌표
     */
    public abstract double getMaxY();
}
