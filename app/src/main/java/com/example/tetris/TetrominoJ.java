package com.example.tetris;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.List;

public class TetrominoJ extends Tetromino {
    private static final int COLOR = Color.BLUE;

    public TetrominoJ() {
        super(COLOR);
    }

    @Override
    public void slideRight() {

    }

    @Override
    public void slideLeft() {

    }

    @Override
    public void setStartPosition(int numColumns, int numRows) {
        int startPositionX = 1;
        int startPositionY = numColumns/2-1;
        List<Pos> posList = new ArrayList<Pos>() {};
        posList.add(new Pos(startPositionX, startPositionY));
        posList.add(new Pos(startPositionX, startPositionY+1));
        posList.add(new Pos(startPositionX, startPositionY-1));
        posList.add(new Pos(startPositionX-1, startPositionY-1));

        setPos(posList);
    }
}
