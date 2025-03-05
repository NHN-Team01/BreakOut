package com.nhnacademy;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Circle 클래스는 Shape를 상속받으며, 원의 반지름 정보를 포함합니다.
 * 원의 경계는 중심 좌표와 반지름을 이용하여 계산됩니다.
 */
public class Circle extends Shape implements Drawable {
    // 원의 반지름, 색상
    protected double radius;
    protected Color color;
    
    /**
     * 생성자: 원의 중심 좌표와 반지름을 초기화합니다.
     * 
     * @param x 중심 X 좌표
     * @param y 중심 Y 좌표
     * @param radius 원의 반지름
     * @param color 원의 색상
     */
    public Circle(double x, double y, double radius, Color color) {
            super(x, y);
            this.radius = radius;
            this.color = color;
    }

    /**
     * 원의 반지름 값을 반환합니다.
     * 
     * @return 원의 반지름
     */
    public double getRadius() {
        return radius;
    }
    
    /**
     * 원이 차지하는 영역의 최소 X 좌표(왼쪽 경계)를 반환합니다.
     * 
     * @return 최소 X 좌표
     */
    @Override
    public double getMinX() {
        return getX() - radius;
    }

    /**
     * 원이 차지하는 영역의 최대 X 좌표(오른쪽 경계)를 반환합니다.
     * 
     * @return 최대 X 좌표
     */
    @Override
    public double getMaxX() {
        return getX() + radius;
    }

    /**
     * 원이 차지하는 영역의 최소 Y 좌표(위쪽 경계)를 반환합니다.
     * 
     * @return 최소 Y 좌표
     */
    @Override
    public double getMinY() {
        return getY() - radius;
    }

    /**
     * 원이 차지하는 영역의 최대 Y 좌표(아래쪽 경계)를 반환합니다.
     * 
     * @return 최대 Y 좌표
     */
    @Override
    public double getMaxY() {
        return getY() + radius;
    }

    /**
     * 원을 그리는 메서드
     * 
     * @param gc 그리기 작업을 처리하는 GraphicsContext 객체
     */
    public void draw(GraphicsContext gc) {
        gc.setFill(color);
        gc.fillOval(getMinX(), getMinY(), radius * 2, radius * 2); // 중심을 기준으로 원 그리기
    }
}
