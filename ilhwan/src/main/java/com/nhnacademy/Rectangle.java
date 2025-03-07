package com.nhnacademy;

/**
 * Rectangle 클래스는 Shape를 상속받으며, 사각형 도형의 폭(width)과 높이(height)를 포함합니다.
 * 도형의 경계는 중심 좌표와 폭, 높이를 이용하여 계산됩니다.
 */
public abstract class Rectangle extends Shape {
    // 사각형의 폭, 높이, 색상
    protected double width;
    protected double height;

    /**
     * 생성자: 사각형의 중심 좌표, 폭, 높이를 초기화합니다.
     * 
     * @param x 중심 X 좌표
     * @param y 중심 Y 좌표
     * @param width 사각형의 폭
     * @param height 사각형의 높이
     */
    public Rectangle(double x, double y, double width, double height) {
        super(x, y);
        this.width = width;
        this.height = height;
    }

    /**
     * 사각형이 차지하는 영역의 최소 X 좌표(왼쪽 경계)를 반환합니다. 
     * 
     * @return 최소 X 좌표
     */
    @Override
    public double getMinX() {
        return getX() - (width / 2.0);
    }

    /**
     * 사각형이 차지하는 영역의 최대 X 좌표(오른쪽 경계)를 반환합니다. 
     *
     * @return 최대 X 좌표
     */
    @Override
    public double getMaxX() {
        return getX() + (width / 2.0);
    }

    /**
     * 사각형이 차지하는 영역의 최소 Y 좌표(위쪽 경계)를 반환합니다.
     * 
     * @return 최소 Y 좌표
     */
    @Override
    public double getMinY() {
        return getY() - (height / 2.0);
    }
    
    /**
     * 사각형이 차지하는 영역의 최대 Y 좌표(아래쪽 경계)를 반환합니다.
     * 
     * @return 최대 Y 좌표
     */
    @Override
    public double getMaxY() {
        return getY() + (height / 2.0);
    }
}
