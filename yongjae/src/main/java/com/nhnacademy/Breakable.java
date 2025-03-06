package com.nhnacademy;

import java.util.List;

public interface Breakable {
    int crash(Ball ball, int rows, int cols, List<List<Brick>> bricks, int i, int j,  Paddle paddle);
}
