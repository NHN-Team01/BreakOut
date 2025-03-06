package com.nhnacademy;

import java.util.List;

/**
 * Bounceable 객체는 이동할 수 있으며, 다른 객체와 충돌하면 튕겨나갑니다.
 */
public interface Bounceable extends Movable {
    /**
     * 주어진 도형 리스트에 대해 bounce 동작을 수행
     * 각 도형의 종류에 따라 bounce 동작의 세부적인 동작이 달라질 수 있음
     * 
     * @param shapes bounce 동작을 수행할 도형들의 리스트. 리스트는 null이 아니어야 하며, 비어 있을 수도 있음
     * @throws IllegalArgumentException 만약 리스트에 null 요소가 포함되어 있을 경우
     */
    void bounce(List<Shape> shapes);
    /**
     * X축 방향으로 튕김
     */
    void bounceX();
    /**
     * Y축 방향으로 튕김
     */
    void bounceY();
}
