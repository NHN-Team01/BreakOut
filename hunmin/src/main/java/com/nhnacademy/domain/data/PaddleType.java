package com.nhnacademy.domain.data;

import javafx.scene.paint.Color;

/**
 * 발판의 크기를 설정하는 클래스
 */
public enum PaddleType {
    SMALL(70, 20, Color.RED),
    NORMAL(100, 20, Color.BLUE),
    BIG(120, 20, Color.GREEN);

    private final double width;
    private final double height;
    private final Color color;

    PaddleType(double width, double height, Color color) {
        this.width = width;
        this.height = height;
        this.color = color;
    }

    public double width() {
        return width;
    }

    public double height() {
        return height;
    }

    public Color color() {
        return color;
    }
}
