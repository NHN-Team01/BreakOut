package com.nhnacademy.effect;

import com.nhnacademy.Ball;
import com.nhnacademy.Brick;
import com.nhnacademy.Paddle;
import javafx.scene.paint.Color;

public class BombEffect implements Effect {
    private int[] dboom = {-1, 0, 1};
    private Color color = Color.GRAY;
    private Brick[][] bricks;
    private int rows;
    private int cols;
    private int i;
    private int j;

    public BombEffect(int rows, int cols, Brick[][] bricks, int i, int j) {
        this.rows = rows;
        this.cols = cols;
        this.bricks = bricks;
        this.i = i;
        this.j = j;
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public int apply() {
        int sumScore = 0;
        for (int k = 0; k < dboom.length; k++) {
            for (int l = 0; l < dboom.length; l++) {
                int ni = i + dboom[k];
                int nj = j + dboom[l];
                if (0 <= ni && 0<= nj && ni < rows && nj < cols) {
                    Brick b = bricks[ni][nj];
                    if (b == null) continue;
                    b.setDestroyed(true);
                    sumScore += b.getScore();
                    bricks[ni][nj] = null;
                }
            }
        }
        return sumScore;
    }
}
