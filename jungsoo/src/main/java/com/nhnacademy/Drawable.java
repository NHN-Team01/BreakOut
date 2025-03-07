package com.nhnacademy;

import javafx.scene.canvas.GraphicsContext;

/**
 * Drawable
 * <p>
 * - 화면에 그릴 수 있는 객체를 나타내는 인터페이스
 * - draw() 메서드에서 JavaFX GraphicsContext를 사용해
 *   해당 객체를 시각적으로 표현
 */
public interface Drawable {
    /**
     * @param gc 그리기 대상 GraphicsContext (Canvas 용)
     *           객체 자신을 그리기 위한 정보를 담고 있음
     */
    void draw(GraphicsContext gc);
}