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
        List<Pos> posList = getPos();
        if (getPosition() == 3) {
            setPosition(0);
        } else setPosition(getPosition() + 1);
        switch (getPosition())
        {
            case 0:
                posList.get(0).setX(posList.get(0).getX()-2);
                posList.get(1).setX(posList.get(1).getX()-1);
                posList.get(1).setY(posList.get(1).getY()-1);
                posList.get(3).setX(posList.get(3).getX()+1);
                posList.get(3).setY(posList.get(3).getY()+1);
                break;
            case 1:
                posList.get(0).setY(posList.get(0).getY()+2);
                posList.get(1).setX(posList.get(1).getX()-1);
                posList.get(1).setY(posList.get(1).getY()+1);
                posList.get(3).setX(posList.get(3).getX()+1);
                posList.get(3).setY(posList.get(3).getY()-1);
                break;
            case 2:
                posList.get(0).setX(posList.get(0).getX()+2);
                posList.get(1).setX(posList.get(1).getX()+1);
                posList.get(1).setY(posList.get(1).getY()+1);
                posList.get(3).setX(posList.get(3).getX()-1);
                posList.get(3).setY(posList.get(3).getY()-1);
                break;
            default:
                posList.get(0).setY(posList.get(0).getY()-2);
                posList.get(1).setX(posList.get(1).getX()+1);
                posList.get(1).setY(posList.get(1).getY()-1);
                posList.get(3).setX(posList.get(3).getX()-1);
                posList.get(3).setY(posList.get(3).getY()+1);
                break;
        }
    }

    @Override
    public void slideLeft() {
        List<Pos> posList = getPos();
        if (getPosition() == 0) {
            setPosition(3);
        } else setPosition(getPosition() + 1);
        switch (getPosition())
        {
            case 0:
                posList.get(0).setY(posList.get(0).getY()-2);
                posList.get(1).setX(posList.get(1).getX()+1);
                posList.get(1).setY(posList.get(1).getY()-1);
                posList.get(3).setX(posList.get(3).getX()-1);
                posList.get(3).setY(posList.get(3).getY()+1);
                break;
            case 1:
                posList.get(0).setY(posList.get(0).getY()-2);
                posList.get(1).setX(posList.get(1).getX()-1);
                posList.get(1).setY(posList.get(1).getY()-1);
                posList.get(3).setX(posList.get(3).getX()+1);
                posList.get(3).setY(posList.get(3).getY()+1);
                break;
            case 2:
                posList.get(0).setY(posList.get(0).getY()+2);
                posList.get(1).setX(posList.get(1).getX()-1);
                posList.get(1).setY(posList.get(1).getY()+1);
                posList.get(3).setX(posList.get(3).getX()+1);
                posList.get(3).setY(posList.get(3).getY()-1);
                break;
            default:
                posList.get(0).setX(posList.get(0).getX()+2);
                posList.get(1).setX(posList.get(1).getX()+1);
                posList.get(1).setY(posList.get(1).getY()+1);
                posList.get(3).setX(posList.get(3).getX()-1);
                posList.get(3).setY(posList.get(3).getY()-1);
                break;
        }
    }

    @Override
    public void setStartPosition(int numColumns, int numRows) {
        int startPositionX = 1;
        int startPositionY = numColumns/2-1;
        List<Pos> posList = new ArrayList<Pos>() {};
        posList.add(new Pos(startPositionX-1, startPositionY-1)); // oxxx
        posList.add(new Pos(startPositionX, startPositionY-1)); // xoxx
        posList.add(new Pos(startPositionX, startPositionY)); // xxox
        posList.add(new Pos(startPositionX, startPositionY+1)); // xxxo

        setPos(posList);
    }
}
