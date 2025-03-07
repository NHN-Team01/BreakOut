package com.nhnacademy.domain.data;

import javafx.scene.paint.Color;

/**
 * 공의 크기를 설정하는 클래스
 */
public enum BallType {
    SMALL(5, Color.YELLOW),
    NORMAL(10, Color.RED),
    BIG(20, Color.ORANGE);

    private final double radius;
    private final Color color;

    BallType(double radius, Color color) {
        this.radius = radius;
        this.color = color;
    }

    public double radius() { return radius; }
    public Color color() { return color; }

}
