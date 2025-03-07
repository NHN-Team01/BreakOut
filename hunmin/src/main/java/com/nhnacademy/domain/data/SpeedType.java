package com.nhnacademy.domain.data;

/**
 * 스피드를 설정하는 클래스
 */
public enum SpeedType {
    SLOW(3),
    NORMAL(5),
    FAST(10);


    SpeedType(double speed) {
        this.speed = speed;
    }

    public double speed() {
        return speed;
    }

    private final double speed;
}
