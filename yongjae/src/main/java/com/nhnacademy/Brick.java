package com.nhnacademy;

import com.nhnacademy.effect.Effect;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.List;

public class Brick extends Rectangle implements Drawable, Breakable {
    protected boolean destroyed; // 벽돌이 파괴되었는지 여부
    protected int hp;
    protected int score;
    private Effect effect;

    // 생성자
    public Brick(double x, double y, double width, double height, int hp, Effect effect) {
        super(x, y, width, height);
        this.hp = hp;
        switch (hp) {
            case 1 -> color = Color.RED;
            case 2 -> color = Color.ORANGE;
            case 3 -> color = Color.YELLOW;
            case 4 -> color = Color.GREEN;
            case 5 -> color = Color.BLUE;
        }
        this.destroyed = false; // 초기 상태는 파괴되지 않음
        this.score = 50 * hp;
        this.effect = effect;
        if (effect != null) {
            this.color = effect.getColor();
        }
    }

    // 벽돌을 그리는 메서드
    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(color);
        gc.fillRect(x, y, width, height);
    }

    // Getter와 Setter (필요 시 사용)
    public boolean isDestroyed() {
        return destroyed;
    }

    public void setDestroyed(boolean destroyed) {
        this.destroyed = destroyed;
    }

    public int getScore() {return score;}

    @Override
    public int crash() {
        hp--;
        switch (hp) {
            case 0 -> destroyed = true;
            case 1 -> color = Color.RED;
            case 2 -> color = Color.ORANGE;
            case 3 -> color = Color.YELLOW;
            case 4 -> color = Color.GREEN;
        }
        if (destroyed) {
            if (effect != null) {
                return effect.apply();
            }
            return score;
        }
        else
            return 0;
    }
}
