package com.nhnacademy;

import java.util.List;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Ball extends Circle implements Drawable, Bounceable {
    private double dx; // 공의 x축 속도 (단위: 픽셀/프레임)
    private double dy; // 공의 y축 속도 (단위: 픽셀/프레임)
    private Color color;
    private boolean isPaused = false;

    // 생성자
    public Ball(double x, double y, double radius, double dx, double dy, Color color) {
        super(x, y, radius);
        this.dx = dx;
        this.dy = dy;
        this.color = color;
    }

    /**
     * 볼을 그리는 메서드
     * 
     * @param gc 그리기 작업을 처리하는 GraphicsContext 객체
     */
    public void draw(GraphicsContext gc) {
        gc.setFill(color);
        gc.fillOval(getMinX(), getMinY(), radius * 2, radius * 2); // 중심을 기준으로 원 그리기
    }

    // 공의 위치를 업데이트하는 메서드
    @Override
    public void move() {
        if(!isPaused) {
            x += dx; // x축 위치 업데이트
            y += dy; // y축 위치 업데이트
        }
    }

    /**
     * 공이 화면 하단 경계에 닿았는지 확인하는 메서드
     * 
     * @param canvasHeight 화면의 높이
     * @return 공이 하단 경계에 닿았다면 true, 아니면 false
     */
    public boolean isAtBottom(Wall wall) {
        return getMaxY() >= wall.getY();
    }

    @Override
    public double getDx() {
        return dx;
    }

    @Override
    public double getDy() {
        return dy;
    }

    @Override
    public void setDx(double dx) {
        this.dx = dx;
    }

    @Override
    public void setDy(double dy) {
        this.dy = dy;
    }

    @Override
    public void pause() {
        isPaused = true;
    }

    @Override
    public void resume() {
        isPaused = false;
    }

    @Override
    public void bounce(List<Shape> shapes) {
        if(shapes == null) throw new IllegalArgumentException("매개변수는 null이 아니어야함");

        for(Shape shape : shapes) {
            if(shape instanceof Collidable) {
                Collidable collidable = (Collidable)shape;
                if((collidable instanceof Rectangle) && collidable.isCollisionDetected(this)) {
                    Rectangle rect = (Rectangle)collidable;

                    // Ball의 이전 위치 계산 (현재 위치 - 이동거리)
                    double prevX = this.getX() - dx;
                    double prevY = this.getY() - dy;

                    boolean wasLeft = prevX < rect.getMinX(); // 공이 충돌 전 왼쪽에 있었는가?
                    boolean wasRight = prevX > rect.getMaxX(); // 공이 충돌 전 오른쪽에 있었는가?
                    boolean wasAbove = prevY < rect.getMinY(); // 공이 충돌 전 위쪽에 있었는가?
                    boolean wasBelow = prevY > rect.getMaxY(); // 공이 충돌 전 아래쪽에 있었는가?

                    if((wasLeft && dx > 0) || (wasRight && dx < 0)) {
                        bounceX(); // 좌우 충돌이면 X축 반전
                    }
                    if((wasAbove && dy > 0) || (wasBelow && dy < 0)) {
                        bounceY(); // 상하 충돌이면 Y축 반전
                    }
                    
                    return;
                }
            }
        }
    }

    @Override
    public void bounceX() {
        dx = -dx;
    }

    @Override
    public void bounceY() {
        dy = -dy;
    }
}