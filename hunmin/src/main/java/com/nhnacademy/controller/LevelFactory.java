package com.nhnacademy.controller;

import com.nhnacademy.domain.data.Level;
import com.nhnacademy.domain.data.LevelType;
import com.nhnacademy.domain.data.WallType;
import com.nhnacademy.domain.model.Ball;
import com.nhnacademy.domain.model.Brick;
import com.nhnacademy.domain.model.Obstacle;
import com.nhnacademy.domain.model.Paddle;
import com.nhnacademy.domain.model.Wall;
import com.nhnacademy.view.Shape;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.paint.Color;

/**
 * LEVEL 이 지정되면 해당 레벨에 맞는 레벨 데이터를 생성하는 클래스
 */
public class LevelFactory {


    /**
     * 레벨과 맵이 정해지면 그에 맞는 레벨 데이터 객체를 리턴하는 메서드
     *
     * @param levelType 레벨 지정
     * @param canvasWidth 맵 너비
     * @param canvasHeight 맵 높이
     * @return 레벨 객체
     */
    public static Level createLevel(LevelType levelType, double canvasWidth, double canvasHeight) {
        Ball ball = new Ball(
                levelType.getBallStartX(),
                levelType.getBallStartY(),
                Color.RED,
                levelType.getBallType().radius(),
                levelType.getSpeedType().speed(), levelType.getSpeedType().speed()
        );

        Paddle paddle = new Paddle(
                levelType.getPaddleStartX(),
                levelType.getPaddleStartY(),
                Color.BLUE,
                levelType.getPaddleType().width(), levelType.getPaddleType().height(), levelType.getSpeedType().speed()
        );
        paddle.setBounds(canvasWidth, canvasHeight);

        List<Shape> shapes = new ArrayList<>();
        for (int r = 0; r < levelType.getRows(); r++) {
            for (int c = 0; c < levelType.getCols(); c++) {
                double x = levelType.getStartX() + c * (levelType.getBrickType().width() + levelType.getPadding());
                double y = levelType.getStartY() + r * (levelType.getBrickType().height() + levelType.getPadding());
                shapes.add(new Brick(x, y, levelType.getBrickType().color(), levelType.getBrickType().width(), levelType.getBrickType().height()));
            }
        }

        shapes.add(ball);
        shapes.add(paddle);
        shapes.add(new Wall(0.5, canvasHeight / 2, 1, canvasHeight, WallType.LEFT));
        shapes.add(new Wall(canvasWidth - 0.5, canvasHeight / 2, 1, canvasHeight, WallType.RIGHT));
        shapes.add(new Wall(canvasWidth / 2, 0.5, canvasWidth, 1, WallType.TOP));
        shapes.add(new Obstacle(canvasWidth / 2, canvasHeight - 0.5, canvasWidth, 1));

        return new Level(ball, paddle, shapes);
    }
}
