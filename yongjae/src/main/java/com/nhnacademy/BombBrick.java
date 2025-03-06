package com.nhnacademy;

import javafx.scene.paint.Color;

import java.util.List;

public class BombBrick extends Brick {

    private int[] dboom = {-1 , 0 , 1};
    // 생성자
    public BombBrick(double x, double y, double width, double height) {
        super(x, y, width, height, 1);
        this.color = Color.GRAY;
        this.HP = 1;
    }
    @Override
    public int crash(Ball ball, int rows, int cols, List<List<Brick>> bricks, int i, int j,  Paddle paddle) {
        super.crash(ball, rows, cols, bricks, i, j, paddle);
        int sumScore = 0;
        for (int k = 0; k < dboom.length; k++) {
            for (int l = 0; l < dboom.length; l++) {
                int ni = i + dboom[k];
                int nj = j + dboom[l];
                if (0 <= ni && 0<= nj && ni < bricks.size() && nj < bricks.get(0).size()) {
                    Brick b = bricks.get(ni).get(nj);
                    if (b == null) continue;
                    b.setDestroyed(true);
                    sumScore += b.getScore();
                    bricks.get(ni).set(nj, null);
                }
            }
        }
        return sumScore;
    }
}
