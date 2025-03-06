package com.nhnacademy;

import javafx.scene.paint.Color;

import java.util.List;

public class PaddleLengthBrick extends Brick {

    // 생성자
    public PaddleLengthBrick(double x, double y, double width, double height) {
        super(x, y, width, height, 1);
        this.color = Color.LIGHTPINK;
        this.HP = 1;
    }

    @Override
    public int crash(Ball ball, int rows, int cols, List<List<Brick>> bricks, int i, int j, Paddle paddle) {
        paddle.setWidth(paddle.getWidth() * 2);
        return super.crash(ball, rows, cols, bricks, i, j, paddle);
    }
}
