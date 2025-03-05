package com.nhnacademy;

/**
 * {@code Movable} 인터페이스는 이동 가능한 객체에 대한 기능을 정의합니다.
 * 이 인터페이스는 객체가 이동, 속도 설정, 일시 정지 및 재개 기능을 제공하도록 강제합니다.
 */
public interface Movable {
    /**
     * 단위 거리만큼 이동합니다.
     */
    public void move();

    /**
     * 단위시간당 X축 상으로의 이동량을 반환합니다.
     * @return 단위 시간당 X축 이동량 (dx)
     */
    public double getDx();

    /**
     * 단위시간당 Y축 상으로의 이동량을 반환합니다.
     * @return 단위 시간당 Y축 이동량 (dy)
     */
    public double getDy();

    /**
     * 단위시간당 X축 상으로의 이동량을 설정합니다.
     * @param dx 단위 시간당 X축 이동량
     */
    public void setDx(double dx);

    /**
     * 단위시간당 Y축 상으로의 이동량을 설정합니다.
     * @param dy 단위 시간당 Y축 이동량
     */
    public void setDy(double dy);

    /**
     * 움직임을 중단합니다. move()를 호출하더라도 이동하지 않습니다.
     */
    public void pause();

    /**
     * 움직임을 재개합니다. move()를 호출시 단위 시간 이동량만큼 이동합니다.
     */
    public void resume();
}
