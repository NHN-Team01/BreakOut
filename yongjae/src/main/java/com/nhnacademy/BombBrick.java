package com.nhnacademy;

import javafx.scene.paint.Color;

public class BombBrick extends Brick {

    // 생성자
    public BombBrick(double x, double y, double width, double height) {
        super(x, y, width, height, 1);
        this.color = Color.GRAY;
        this.HP = 1;
    }
}
