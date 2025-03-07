package com.nhnacademy;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * 이 클래스는 좌측 상단 좌표를 기준으로 하는 사각형을 나타냅니다.
 * 사각형의 폭, 높이, 색상을 저장하며, 그리기 기능은 자식 클래스에서 직접 구현합니다.
 */
public abstract class Rectangle extends Shape {
    protected double width;   // 사각형의 폭
    protected double height;  // 사각형의 높이
    protected Color color;    // 사각형의 색상

    /**
     * 생성자
     * @param x 사각형의 왼쪽 상단 x좌표
     * @param y 사각형의 왼쪽 상단 y좌표
     * @param width 사각형의 폭
     * @param height 사각형의 높이
     * @param color 사각형의 색상
     */
    public Rectangle(double x, double y, double width, double height, Color color) {
        super(x, y);  // 부모 클래스(Shape)의 x, y를 초기화
        this.width = width;
        this.height = height;
        this.color = color;
    }

    // getter 메서드들

    /**
     * 좌측 상단 x좌표를 반환합니다.
     */
    @Override
    public double getX() {
        return x;
    }

    /**
     * 좌측 상단 y좌표를 반환합니다.
     */
    @Override
    public double getY() {
        return y;
    }

    /**
     * 사각형의 오른쪽 끝 x좌표를 반환합니다.
     */
    public double getMaxX() {
        return x + width;
    }

    /**
     * 사각형의 아래쪽 끝 y좌표를 반환합니다.
     */
    public double getMaxY() {
        return y + height;
    }

    /**
     * 사각형의 폭을 반환합니다.
     */
    public double getWidth() {
        return width;
    }

    /**
     * 사각형의 높이를 반환합니다.
     */
    public double getHeight() {
        return height;
    }

    /**
     * 사각형의 색상을 반환합니다.
     */
    public Color getColor() {
        return color;
    }

    // setter 메서드들

    /**
     * 사각형의 폭을 설정합니다.
     */
    public void setWidth(double width) {
        this.width = width;
    }

    /**
     * 사각형의 높이를 설정합니다.
     */
    public void setHeight(double height) {
        this.height = height;
    }

    /**
     * 사각형의 색상을 설정합니다.
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * 이 메서드는 사각형을 그리는 기능을 자식 클래스에서 구현하도록 합니다.
     * @param gc 그림을 그릴 GraphicsContext
     */
    public abstract void draw(GraphicsContext gc);
}