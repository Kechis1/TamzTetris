package com.example.tetris;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.List;

public class TetrominoO extends Tetromino {
    private static final int COLOR = Color.parseColor("#f8fd75");
    private static final int BORDER_COLOR = Color.parseColor("#fdcb75");

    public TetrominoO() {
        super(COLOR, BORDER_COLOR);
    }

    @Override
    public List<Pos> getSlideLeftPos() {
        return new ArrayList<Pos>() {};
    }

    @Override
    public List<Pos> getSlideRightPos() {
        return new ArrayList<Pos>() {};
    }

    @Override
    public void slideRight() {

    }

    @Override
    public void slideLeft() {

    }

    @Override
    public void setStartPosition(int numColumns, int numRows) {
        int startPositionX = 0;
        int startPositionY = numColumns/2-1;
        List<Pos> posList = new ArrayList<Pos>() {};
        posList.add(new Pos(startPositionX, startPositionY));
        posList.add(new Pos(startPositionX, startPositionY+1));
        posList.add(new Pos(startPositionX+1, startPositionY));
        posList.add(new Pos(startPositionX+1, startPositionY+1));

        setPos(posList);
    }

}
