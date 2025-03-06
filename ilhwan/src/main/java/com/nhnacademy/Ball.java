package com.nhnacademy;

import java.util.List;

import javafx.scene.paint.Color;

public class Ball extends Circle implements Bounceable {
    private double dx; // 공의 x축 속도 (단위: 픽셀/프레임)
    private double dy; // 공의 y축 속도 (단위: 픽셀/프레임)
    private boolean isPaused = false;

    // 생성자
    public Ball(double x, double y, double radius, double dx, double dy, Color color) {
        super(x, y, radius, color);
        this.dx = dx;
        this.dy = dy;
    }

    // 공의 위치를 업데이트하는 메서드
    @Override
    public void move() {
        if(!isPaused) {
            x += dx; // x축 위치 업데이트
            y += dy; // y축 위치 업데이트
        }
    }

    // 공이 화면 경계와 충돌했는지 확인 및 속도 반전
    public void checkCollision(double canvasWidth, double canvasHeight) {
        // 좌우 경계 충돌
        if (getMinX() <= 0 || getMaxX() >= canvasWidth) {
            dx = -dx; // x축 속도 반전
        }
        // 상하 경계 충돌
        if (getMinY() <= 0 || getMaxY() >= canvasHeight) {
            dy = -dy; // y축 속도 반전
        }
    }

    /**
     * 공이 화면 하단 경계에 닿았는지 확인하는 메서드
     * 
     * @param canvasHeight 화면의 높이
     * @return 공이 하단 경계에 닿았다면 true, 아니면 false
     */
    public boolean isAtBottom(double canvasHeight) {
        return getMaxY() >= canvasHeight;
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
                if(collidable.isCollisionDetected(this) && (collidable instanceof Rectangle)) {
                    Rectangle rect = (Rectangle)collidable;

                    // 공과 사각형의 경계를 비교하여 어느 축에서 충돌했는지 판별
                    double leftDist = Math.abs(this.getX() - rect.getMinX());  // 공과 브릭의 왼쪽 경계
                    double rightDist = Math.abs(this.getX() - rect.getMaxX()); // 공과 브릭의 오른쪽 경계
                    double topDist = Math.abs(this.getY() - rect.getMinY());   // 공과 브릭의 위쪽 경계
                    double bottomDist = Math.abs(this.getY() - rect.getMaxY()); // 공과 브릭의 아래쪽 경계

                    // 각 축에 대해 충돌한 최소 거리를 찾아서 방향을 결정
                    double minXDist = Math.min(leftDist, rightDist);
                    double minYDist = Math.min(topDist, bottomDist);
                
                    // 충돌한 축을 판단하여 반사 처리
                    if (minXDist < minYDist) {
                        bounceX(); // X축 충돌
                    } else {
                        bounceY(); // Y축 충돌
                    }

                    // 모서리와의 충돌이 발생한 경우 두 방향 모두 반전
                    if (minXDist == minYDist) {
                        bounceX();
                        bounceY();
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

    public void resolveOverlap(List<Shape> shapes) {
        for(Shape shape : shapes) {
            if(shape instanceof Collidable) {
                Collidable collidable = (Collidable)shape;
                if(collidable.isCollisionDetected(this)) {
                    adjustPosition(); // 겹침이 발생한 경우 위치 조정
                    break; // 겹침을 해결한 후에는 더 이상 확인하지 않음
                }
            }
        }
    }

    private void adjustPosition() {
        // 겹침을 방지하기 위한 위치 조정
        this.x -= dx * 2;
        this.y -= dy * 2;
    }
}