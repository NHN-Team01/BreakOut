package com.nhnacademy;

/**
 * Bounceable
 * <p>
 * - 공이나 패들처럼 '반사(bounce)'가 가능한 객체를 나타내는 인터페이스
 * - Movable을 확장하므로, 이동 기능 + 반사 기능을 모두 제공
 * - 충돌 시 X/Y축 방향을 뒤집는(반전시키는) 메서드를 정의
 */
public interface Bounceable extends Movable {
    /**
     * X축 속도를 반전한다. (dx = -dx)
     * - 좌우 벽에 부딪힐 때 사용 가능
     */
    void bounceX();

    /**
     * Y축 속도를 반전한다. (dy = -dy)
     * - 상단/하단 벽에 부딪힐 때 사용 가능
     */
    void bounceY();

    /**
     * X축과 Y축을 동시에 반전한다. (dx, dy 동시 반전)
     * - 대각선 반사 등 상황에서 유용
     */
    void bounceXY();
}