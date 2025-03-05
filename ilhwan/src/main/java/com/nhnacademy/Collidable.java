package com.nhnacademy;

/**
 * 다른 객체와 충돌할 수 있는 객체를 나타내는 인터페이스
 * 
 * <p>이 인터페이스를 구현하는 클래스는 {@link Shape} 타입의 다른 객체와 충돌을 감지하는 로직을 제공해야 합니다. </p>
 */
public interface Collidable {
    /**
     * 주어진 객체와 충돌이 감지되었는지 확인하는 메서드
     * @param other 충돌 여부를 확인할 {@link Shape} 객체
     * @return 충돌이 감지되면 {@code true}, 그렇지 않으면 {@code false}를 반환합니다.
     */
    boolean isCollisionDetected(Shape other);
}
