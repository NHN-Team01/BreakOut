package com.nhnacademy.domain.data;

import javafx.scene.paint.Color;

/**
 * 벽돌의 크기를 설정하는 클래스
 */
public enum BrickType {
    SMALL(50, 20, Color.GREEN),
    NORMAL(70, 20, Color.BLUE),
    BIG(90, 20, Color.PURPLE);

    private final double width;
    private final double height;
    private final Color color;

    BrickType(double width, double height, Color color) {
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
