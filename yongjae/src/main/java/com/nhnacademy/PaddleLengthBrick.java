package com.nhnacademy;

import javafx.scene.paint.Color;

public class PaddleLengthBrick extends Brick {

    // 생성자
    public PaddleLengthBrick(double x, double y, double width, double height) {
        super(x, y, width, height, 1);
        this.color = Color.GOLD;
        this.HP = 1;
    }
}
