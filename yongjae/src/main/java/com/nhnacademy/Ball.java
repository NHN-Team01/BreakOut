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
        if (other instanceof Wall) {
            Wall wall = (Wall) other;
            if (wall.height == 0) {
                if (ballY - ballRadius <= wall.getY() && ballY + ballRadius >= wall.getY()) {
                    return true;
                }
            }
            else if (wall.width == 0) {
                if (ballX - ballRadius <= wall.getX() && ballX + ballRadius >= wall.getX()) {
                    return true;
                }
            }
        }
        else if (other instanceof Brick) {
            Brick brick = (Brick) other;

            // 공이 벽돌의 경계와 충돌했는지 확인
            return ballX + ballRadius > brick.x &&
                    ballX - ballRadius < brick.x + brick.width &&
                    ballY + ballRadius > brick.y &&
                    ballY - ballRadius < brick.y + brick.height;
        }
        else if (other instanceof Paddle) {
            Paddle paddle = (Paddle) other;
            return ballX + ballRadius > paddle.x &&
                    ballX - ballRadius < paddle.x + paddle.width &&
                    ballY + ballRadius > paddle.y &&
                    ballY - ballRadius < paddle.y + paddle.height;
        }
        return false;
    }

    @Override
    public void bounce(Shape shape) {
        if (shape instanceof Wall) {
            Wall wall = (Wall) shape;
            if (wall.width == 0) {
                dx = -dx;
            }
            else if (wall.height == 0) {
                dy = -dy;
            }
        }
        else if (shape instanceof Rectangle) {
            Rectangle rectangle = (Rectangle) shape;
            if(rectangle.x > x && rectangle.y > y && radius * radius > Math.sqrt(x - rectangle.x) + Math.sqrt(y - rectangle.y)) {
                dx = -dx;
                dy = -dy;
            }
            else if (rectangle.x > x && rectangle.y + rectangle.getHeight() < y && radius * radius > Math.sqrt(x - rectangle.x) + Math.sqrt(y - (rectangle.y + rectangle.getHeight()))) {
                dx = -dx;
                dy = -dy;
            }
            else if (rectangle.x + rectangle.getWidth() < x && rectangle.y > y && radius * radius > Math.sqrt(x - (rectangle.x + rectangle.getWidth())) + Math.sqrt(y - rectangle.y)) {
                dx = -dx;
                dy = -dy;
            }
            else if (rectangle.x + rectangle.getWidth() < x && rectangle.y + rectangle.getHeight() < y && radius * radius > Math.sqrt(x - (rectangle.x + rectangle.getWidth())) + Math.sqrt(y - (rectangle.y + rectangle.getHeight()))) {
                dx = -dx;
                dy = -dy;
            }
            else if (rectangle.x < x && x < rectangle.x + rectangle.getWidth()) {
                if (y + radius > rectangle.y &&  y - radius < rectangle.y + rectangle.getHeight()) {
                    dy = -dy;
                }
            }
            else if (rectangle.y < y && y < rectangle.y + rectangle.getHeight()) {
                if (x + radius > rectangle.x &&  x - radius < rectangle.x + rectangle.getWidth()) {
                    dx = -dx;
                }
            }
        }
    }
}