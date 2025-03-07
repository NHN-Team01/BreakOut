package com.nhnacademy.domain.data;


/**
 * 각 LEVEL 의 벽돌의 크기, 공의 크기 등 난이도를 설정하는 클래스
 *
 */
public enum LevelType {
    LEVEL_1(
            5, 10,
            50, 50,
            BrickType.NORMAL,
            BallType.NORMAL,
            PaddleType.NORMAL,
            SpeedType.SLOW,
            5,
            400, 300,
            400, 550
    ),
    LEVEL_2(
            6, 12,
            30, 80,
            BrickType.NORMAL,
            BallType.NORMAL,
            PaddleType.NORMAL,
            SpeedType.NORMAL,
            5,
            300, 300,
            400, 500
    );


    /**
     * Bricks
     */
    private final BrickType brickType;
    private final int rows;
    private final int cols;
    private final double startX;
    private final double startY;
    private final double padding;

    /**
     * Ball
     */
    private final BallType ballType;
    private final double ballStartX;
    private final double ballStartY;

    /**
     * Paddle
     */
    private final PaddleType paddleType;
    private final double paddleStartX;
    private final double paddleStartY;

    /**
     * Speed
     */
    private final SpeedType speedType;

    // TODO 너무 많은 파라미터.. 빌더패턴으로?
    LevelType(int rows, int cols,
              double startX, double startY,
              BrickType brickType,
              BallType ballType,
              PaddleType paddleType,
              SpeedType speedType,
              double padding,
              double ballStartX, double ballStartY,
              double paddleStartX, double paddleStartY) {
        this.rows = rows;
        this.cols = cols;
        this.startX = startX;
        this.startY = startY;
        this.brickType = brickType;
        this.ballType = ballType;
        this.paddleType = paddleType;
        this.speedType = speedType;
        this.padding = padding;
        this.ballStartX = ballStartX;
        this.ballStartY = ballStartY;
        this.paddleStartX = paddleStartX;
        this.paddleStartY = paddleStartY;
    }

    public int getRows() { return rows; }
    public int getCols() { return cols; }
    public double getStartX() { return startX; }
    public double getStartY() { return startY; }
    public double getPadding() { return padding; }
    public double getBallStartX() { return ballStartX; }
    public double getBallStartY() { return ballStartY; }
    public double getPaddleStartX() { return paddleStartX; }
    public double getPaddleStartY() { return paddleStartY; }
    public BrickType getBrickType() { return brickType; }
    public BallType getBallType() { return ballType; }
    public PaddleType getPaddleType() { return paddleType; }
    public SpeedType getSpeedType() { return speedType; }
}
