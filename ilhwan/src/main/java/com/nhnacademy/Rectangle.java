package com.nhnacademy;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Rectangle 클래스는 Shape를 상속받으며, 사각형 도형의 폭(width)과 높이(height)를 포함합니다.
 * 도형의 경계는 중심 좌표와 폭, 높이를 이용하여 계산됩니다.
 */
public class Rectangle extends Shape implements Drawable {
    // 사각형의 폭, 높이, 색상
    protected double width;
    protected double height;
    protected Color color;

    /**
     * 생성자: 사각형의 중심 좌표, 폭, 높이를 초기화합니다.
     * 
     * @param x 중심 X 좌표
     * @param y 중심 Y 좌표
     * @param width 사각형의 폭
     * @param height 사각형의 높이
     * @param color 사각형의 색상
     */
    public Rectangle(double x, double y, double width, double height, Color color) {
        super(x, y);
        this.width = width;
        this.height = height;
        this.color = color;
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
    
    /**
     * 사각형을 그리는 메서드
     * 
     * @param gc 그리기 작업을 처리하는 GraphicsContext 객체
     */
    public void draw(GraphicsContext gc) {
        gc.setFill(color);
        gc.fillRect(x, y, width, height); // 사각형 그리기
    }
}
