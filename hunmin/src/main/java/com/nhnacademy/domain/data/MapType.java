package com.nhnacademy.domain.data;

/**
 * 맵의 크기를 설정하는 클래스
 */
public enum MapType {
    SMALL(600, 400),
    NORMAL(800, 600),
    BIG(1000, 800);

    private final double width;
    private final double height;

    MapType(double width, double height) {
        this.width = width;
        this.height = height;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }
}
