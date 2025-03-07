package com.nhnacademy;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Ball extends Circle implements Drawable, Movable, Bounceable {
    private double dx; // 공의 x축 속도 (단위: 픽셀/프레임)
    private double dy; // 공의 y축 속도 (단위: 픽셀/프레임)
    private boolean paused;
    // 생성자
    public Ball(double x, double y, double radius, double dx, double dy, Color color) {
        super(x, y, radius, color);
        this.dx = dx;
        this.dy = dy;
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(color);
        gc.fillOval(x - radius, y - radius, radius * 2, radius * 2); // 중심을 기준으로 원 그리기
    }

    // 공의 위치를 업데이트하는 메서드
    @Override
    public void move() {
        if (!paused) {
            x += dx; // x축 위치 업데이트
            y += dy; // y축 위치 업데이트
        }
    }

    @Override
    public double getDx() { return dx; }

    @Override
    public double getDy() { return dy; }

    @Override
    public void setDx(double dx) {
        this.dx = dx;
    }

    @Override
    public void setDy(double dy) {
        this.dy = dy;
    }

    public boolean isPaused() { return paused; }

    @Override
    public void pause() {
        paused = true;
    }

    @Override
    public void resume() {
        paused = false;
    }

    @Override
    public boolean isCollisionDetected(Shape other) {
        double ballX = this.getX();
        double ballY = this.getY();
        double ballRadius = this.getRadius();
        if (other instanceof Wall wall) {
            if (wall.getHeight() == 0) {
                return ballY - ballRadius <= wall.getY() && ballY + ballRadius >= wall.getY();
            }
            else if (wall.getWidth() == 0) {
                return ballX - ballRadius <= wall.getX() && ballX + ballRadius >= wall.getX();
            }
        }
        else if (other instanceof Rectangle rectangle) {
            // 공이 벽돌의 경계와 충돌했는지 확인
            return ballX + ballRadius > rectangle.getX() &&
                    ballX - ballRadius < rectangle.getMaxX() &&
                    ballY + ballRadius > rectangle.getY() &&
                    ballY - ballRadius < rectangle.getMaxY();
        }
        return false;
    }

    @Override
    public void bounce(Shape shape) {
        if (shape instanceof Wall wall) {
            if (wall.width == 0) {
                dx = -dx;
            }
            else if (wall.height == 0) {
                dy = -dy;
            }
        }
        else if (shape instanceof Rectangle rectangle) {
            if(isCollidingWithRectangleVertex(rectangle)) {
                dx = -dx;
                dy = -dy;
            }
            else if (rectangle.getX() < x && x < rectangle.getMaxX()) {
                if (y + radius > rectangle.getY() &&  y - radius < rectangle.getMaxY()) {
                    dy = -dy;
                }
            }
            else if (rectangle.getY() < y && y < rectangle.getMaxY()) {
                if (x + radius > rectangle.getX() &&  x - radius < rectangle.getMaxX()) {
                    dx = -dx;
                }
            }
        }
    }

    private boolean isCollidingWithRectangleVertex(Rectangle rectangle) {
        double[][] vertices = {
                {rectangle.getX(), rectangle.getY()},                    // 왼쪽 상단
                {rectangle.getX(), rectangle.getMaxY()},                  // 왼쪽 하단
                {rectangle.getMaxX(), rectangle.getY()},                  // 오른쪽 상단
                {rectangle.getMaxX(), rectangle.getMaxY()}                // 오른쪽 하단
        };
        for (double[] vertex : vertices) {
            double vertexX = vertex[0];
            double vertexY = vertex[1];
            // 두 점 사이의 거리 계산
            double distance = Math.pow(x - vertexX, 2) + Math.pow(y - vertexY, 2);

            // 충돌 체크 (거리가 반지름보다 작으면 충돌)
            if (radius * radius > distance) {
                return true;
            }
        }
        return false;
    }
}