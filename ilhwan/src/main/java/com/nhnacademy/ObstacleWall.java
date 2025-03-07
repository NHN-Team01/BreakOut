package com.nhnacademy;

public class ObstacleWall extends Wall implements Blockable {

    public ObstacleWall(double x, double y, double width, double height) {
        super(x, y, width, height);
    }

    @Override
    public void hit() {
        EventManager.getInstance().notifyGameOver();
    }

}
