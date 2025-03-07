package com.nhnacademy;

/**
 * Movable
 * <p>
 * - 이동( move() ) 기능을 가진 객체에 대한 인터페이스
 * - dx, dy 속도 제어, 일시정지(pause)/재시작(resume) 등의 기능도 포함
 */
public interface Movable {
    /**
     * 매 프레임 혹은 매 tick 시점에서 x, y 좌표를 업데이트
     * (dx, dy 에 따라 이동)
     */
    void move();

    /** @return 현재 x축 속도 */
    double getDx();
    /** @return 현재 y축 속도 */
    double getDy();

    /** @param dx x축 속도 설정 */
    void setDx(double dx);
    /** @param dy y축 속도 설정 */
    void setDy(double dy);

    /** 이동 일시정지 */
    void pause();
    /** 이동 재개 */
    void resume();

    /**
     * 'other'라는 Shape와 충돌했는지 여부를 판별
     * @param other 비교 대상 Shape
     * @return 충돌이면 true, 아니면 false
     */
    boolean isCollisionDetected(Shape other);
}