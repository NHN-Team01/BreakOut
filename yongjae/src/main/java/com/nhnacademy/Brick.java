package com.nhnacademy;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Brick extends Rectangle implements Drawable, Breakable {
    protected boolean isDestroyed; // 벽돌이 파괴되었는지 여부
    protected int HP;
    protected int score;

    // 생성자
    public Brick(double x, double y, double width, double height, int HP) {
        super(x, y, width, height);
        this.HP = HP;
        switch (HP) {
            case 1 -> color = Color.RED;
            case 2 -> color = Color.ORANGE;
            case 3 -> color = Color.YELLOW;
            case 4 -> color = Color.GREEN;
            case 5 -> color = Color.BLUE;
        }
        this.isDestroyed = false; // 초기 상태는 파괴되지 않음
        this.score = 50 * HP;
    }

    // 벽돌을 그리는 메서드
    @Override
    public void draw(GraphicsContext gc) {
        if (!isDestroyed) {
            gc.setFill(color);
            gc.fillRect(x, y, width, height);
        }
    }

    // Getter와 Setter (필요 시 사용)
    public boolean isDestroyed() {
        return isDestroyed;
    }

    public void setDestroyed(boolean destroyed) {
        isDestroyed = destroyed;
    }

    public int getScore() {return score;}

    @Override
    public void crash() {
        HP--;
        switch (HP) {
            case 0 -> isDestroyed = true;
            case 1 -> color = Color.RED;
            case 2 -> color = Color.ORANGE;
            case 3 -> color = Color.YELLOW;
            case 4 -> color = Color.GREEN;
        }
    }
}
