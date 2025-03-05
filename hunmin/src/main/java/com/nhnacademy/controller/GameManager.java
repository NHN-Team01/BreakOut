package com.nhnacademy.controller;


import com.nhnacademy.domain.model.Ball;
import com.nhnacademy.domain.model.Brick;
import com.nhnacademy.domain.model.Obstacle;
import com.nhnacademy.domain.model.Paddle;
import com.nhnacademy.domain.model.Wall;
import com.nhnacademy.domain.model.WallType;
import com.nhnacademy.view.Drawable;
import com.nhnacademy.view.Level;
import com.nhnacademy.view.Shape;
import java.util.ArrayList;
import java.util.List;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

public class GameManager {

    private final Canvas canvas;
    private final GraphicsContext graphicsContext;
    private AnimationTimer gameLoop;
    private boolean gameOver = false;

    private boolean moveLeft = false;
    private boolean moveRight = false;

    private List<Shape> shapes;
    private List<Wall> walls;
    private List<Obstacle> obstacles;
    private List<Brick> bricks;
    private Ball ball;
    private Paddle paddle;

    public GameManager(Canvas canvas, GraphicsContext graphicsContext) {
        this.canvas = canvas;
        this.graphicsContext = graphicsContext;
        initGameObjects();
    }

    private void initGameObjects() {
        // Ball, Paddle, Bricks 생성
        shapes = new ArrayList<>();
        walls = new ArrayList<>();
        obstacles = new ArrayList<>();

        double w = canvas.getWidth();
        double h = canvas.getHeight();

        Level level1 = new Level(
                5, 10,         // rows, cols
                50, 50,        // startX, startY for bricks
                70, 20,        // brickWidth, brickHeight
                5,             // padding
                Color.BLUE,    // brick color
                400, 300,      // ball start
                400, 550       // paddle start
        );

        // TODO : ball, paddle, bricks 를 그리는 책임은 Level 에 있어야 한다
        ball = new Ball(level1.getBallStartX(), level1.getBallStartY(), Color.RED, 10, 3, 3);
        paddle = new Paddle(level1.getPaddleStartX(), level1.getPaddleStartY(), Color.BLUE, 100, 20, 5);
        bricks = level1.createBricks();
        // 좌
        walls.add(new Wall(0.5, h/2, 1, h, WallType.LEFT));
        // 우
        walls.add(new Wall(w-0.5, h/2, 1, h, WallType.RIGHT));
        // 상
        walls.add(new Wall(w/2, 0.5, w, 1, WallType.TOP));
        // 하
        obstacles.add(new Obstacle(w/2, h-0.5, w, 1));
        shapes.add(ball);
        shapes.add(paddle);
        shapes.addAll(bricks);
        shapes.addAll(obstacles);
        shapes.addAll(walls);
    }

    public void startGame() {
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (gameOver) {
                    return;
                }
                update();
                render();
            }
        };
        gameLoop.start();
    }

    private void update() {
        // 공 업데이트
        ball.update();

        for (Wall wall : walls) {
            if (wall.isCollisionDetected(ball)) {
                wall.onCollision(ball);
            }
        }

        for (Obstacle obs : obstacles) {
            if (obs.isCollisionDetected(ball)) {
                obs.onCollision(ball);
                gameOver = true;
                showGameOverPopup();
                return;
            }
        }


        // 패들 이동
        if (moveLeft) {
            paddle.moveLeft();
        }
        if (moveRight) {
            paddle.moveRight();
        }
        paddle.checkBounds(canvas.getWidth());

        // TODO : 패들과 충돌 로직 수정 필요
        if (paddle.isCollisionDetected(ball)) {
            ball.setDy(-ball.getDy());
        }

        // 벽돌 충돌
        bricks.forEach(brick -> {
            if (brick.isCollisionDetected(ball)) {
                ball.setDy(-ball.getDy());
                brick.breakObject();
            }
        });

        // 충돌한 벽돌 제거
        bricks.removeIf(Brick::isBroken);
        shapes.removeIf(shape -> shape instanceof Brick && ((Brick) shape).isBroken());
    }

    private void render() {
        // 화면 초기화
        graphicsContext.setFill(Color.BLACK);
        graphicsContext.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        // 그리기
        for(Shape shape : shapes) {
            if(shape instanceof Ball || shape instanceof Paddle || shape instanceof Brick) {
                ((Drawable) shape).draw(graphicsContext);
            }
        }
    }

    // 팝업 표시 (게임오버)
    private void showGameOverPopup() {
        Platform.runLater(() -> {
            ButtonType retryButton = new ButtonType("Retry", ButtonBar.ButtonData.OK_DONE);
            ButtonType exitButton = new ButtonType("Exit", ButtonBar.ButtonData.CANCEL_CLOSE);

            Alert alert = new Alert(Alert.AlertType.NONE, "Game Over! Thank you for playing.", retryButton, exitButton);
            alert.setTitle("Game Over");
            alert.setHeaderText(null);

            alert.showAndWait().ifPresent(response -> {
                if (response == retryButton) {
                    restartGame();
                } else {
                    Platform.exit();
                }
            });
        });
    }

    public void restartGame() {
        if (gameLoop != null) {
            gameLoop.stop();
        }
        gameOver = false;
        initGameObjects();
        startGame();
    }

    public void onKeyPressed(javafx.scene.input.KeyEvent e) {
        if (e.getCode() == KeyCode.LEFT) {
            moveLeft = true;
        } else if (e.getCode() == KeyCode.RIGHT) {
            moveRight = true;
        }
    }

    public void onKeyReleased(javafx.scene.input.KeyEvent e) {
        if (e.getCode() == KeyCode.LEFT) {
            moveLeft = false;
        } else if (e.getCode() == KeyCode.RIGHT) {
            moveRight = false;
        }
    }

    private void checkCollisionWithWalls(Ball ball) {
        double w = canvas.getWidth();
        double h = canvas.getHeight();

        // 왼/오 경계
        if (ball.getX() - ball.getRadius() < 0 || ball.getX() + ball.getRadius() > w) {
            ball.bounceX();  // dx 반전
        }

        // 위 경계
        if (ball.getY() - ball.getRadius() < 0) {
            ball.bounceY();  // dy 반전
        }

        // 아래쪽 경계(= 바닥) '게임 오버' 처리
        // TODO : 라이프도 추가
        if (ball.getY() + ball.getRadius() > h) {
            gameOver = true;
            showGameOverPopup();
            return;
        }
    }

}
