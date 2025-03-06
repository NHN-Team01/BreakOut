package com.nhnacademy;

/**
 * Breakable 인터페이스는 충돌에 의해 파괴될 수 있는 객체의 특성을 정의합니다.
 * 이 인터페이스는 Collidable 인터페이스를 확장합니다.
 */
public interface Breakable extends Collidable {
    /**
     * 객체가 공과 충돌했을 때 호출되는 메서드
     * 이 메소드 내부엥서 객체의 내부 상태를 업데이트함
     */
    void hit();

    /**
     * 객체가 파괴되었는지여 여부를 반환하는 메서드
     * @return 객체가 파괴되었으면 {@code true}, 그렇지 않으면 {@code false}
     */
    boolean isBroken();
}
