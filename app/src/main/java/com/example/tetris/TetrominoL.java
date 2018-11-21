package com.example.tetris;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.List;

public class TetrominoL extends Tetromino {
    private static final int COLOR = Color.parseColor("#FFA500");

    public TetrominoL() {
        super(COLOR);
    }

    @Override
    public List<Pos> getSlideLeftPos() {
        List<Pos> posList = new ArrayList<Pos>() {};
        posList.add(new Pos(getPos().get(0).getX(), getPos().get(0).getY()));
        posList.add(new Pos(getPos().get(1).getX(), getPos().get(1).getY()));
        posList.add(new Pos(getPos().get(2).getX(), getPos().get(2).getY()));
        posList.add(new Pos(getPos().get(3).getX(), getPos().get(3).getY()));
        int pos = getPosition();
        if (pos == 0) {
            pos = 3;
        } else pos -= 1;
        switch (pos)
        {
            case 0:
                posList.get(0).setX(posList.get(0).getX()+1);
                posList.get(0).setY(posList.get(0).getY()-1);
                posList.get(2).setX(posList.get(2).getX()-1);
                posList.get(2).setY(posList.get(2).getY()+1);
                posList.get(3).setX(posList.get(3).getX()-2);
                break;
            case 1:
                posList.get(0).setX(posList.get(0).getX()-1);
                posList.get(0).setY(posList.get(0).getY()-1);
                posList.get(2).setX(posList.get(2).getX()+1);
                posList.get(2).setY(posList.get(2).getY()+1);
                posList.get(3).setY(posList.get(3).getY()+2);
                break;
            case 2:
                posList.get(0).setX(posList.get(0).getX()-1);
                posList.get(0).setY(posList.get(0).getY()+1);
                posList.get(2).setX(posList.get(2).getX()+1);
                posList.get(2).setY(posList.get(2).getY()-1);
                posList.get(3).setX(posList.get(3).getX()+2);
                break;
            default:
                posList.get(0).setX(posList.get(0).getX()+1);
                posList.get(0).setY(posList.get(0).getY()+1);
                posList.get(2).setX(posList.get(2).getX()-1);
                posList.get(2).setY(posList.get(2).getY()-1);
                posList.get(3).setY(posList.get(3).getY()-2);
                break;
        }
        return posList;
    }

    @Override
    public List<Pos> getSlideRightPos() {
        List<Pos> posList = new ArrayList<Pos>() {};
        posList.add(new Pos(getPos().get(0).getX(), getPos().get(0).getY()));
        posList.add(new Pos(getPos().get(1).getX(), getPos().get(1).getY()));
        posList.add(new Pos(getPos().get(2).getX(), getPos().get(2).getY()));
        posList.add(new Pos(getPos().get(3).getX(), getPos().get(3).getY()));
        int pos = getPosition();
        if (pos == 3) {
            pos = 0;
        } else pos += 1;
        switch (pos) {
            case 0:
                posList.get(0).setX(posList.get(0).getX() - 1);
                posList.get(0).setY(posList.get(0).getY() - 1);
                posList.get(2).setX(posList.get(2).getX() + 1);
                posList.get(2).setY(posList.get(2).getY() + 1);
                posList.get(3).setY(posList.get(3).getY() + 2);
                break;
            case 1:
                posList.get(0).setX(posList.get(0).getX() - 1);
                posList.get(0).setY(posList.get(0).getY() + 1);
                posList.get(2).setX(posList.get(2).getX() + 1);
                posList.get(2).setY(posList.get(2).getY() - 1);
                posList.get(3).setX(posList.get(3).getX() + 2);
                break;
            case 2:
                posList.get(0).setX(posList.get(0).getX() + 1);
                posList.get(0).setY(posList.get(0).getY() + 1);
                posList.get(2).setX(posList.get(2).getX() - 1);
                posList.get(2).setY(posList.get(2).getY() - 1);
                posList.get(3).setY(posList.get(3).getY() - 2);
                break;
            default:
                posList.get(0).setX(posList.get(0).getX() + 1);
                posList.get(0).setY(posList.get(0).getY() - 1);
                posList.get(2).setX(posList.get(2).getX() - 1);
                posList.get(2).setY(posList.get(2).getY() + 1);
                posList.get(3).setX(posList.get(3).getX() - 2);
                break;
        }
        return posList;
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
                posList.get(0).setX(posList.get(0).getX()-1);
                posList.get(0).setY(posList.get(0).getY()-1);
                posList.get(2).setX(posList.get(2).getX()+1);
                posList.get(2).setY(posList.get(2).getY()+1);
                posList.get(3).setY(posList.get(3).getY()+2);
                break;
            case 1:
                posList.get(0).setX(posList.get(0).getX()-1);
                posList.get(0).setY(posList.get(0).getY()+1);
                posList.get(2).setX(posList.get(2).getX()+1);
                posList.get(2).setY(posList.get(2).getY()-1);
                posList.get(3).setX(posList.get(3).getX()+2);
                break;
            case 2:
                posList.get(0).setX(posList.get(0).getX()+1);
                posList.get(0).setY(posList.get(0).getY()+1);
                posList.get(2).setX(posList.get(2).getX()-1);
                posList.get(2).setY(posList.get(2).getY()-1);
                posList.get(3).setY(posList.get(3).getY()-2);
                break;
            default:
                posList.get(0).setX(posList.get(0).getX()+1);
                posList.get(0).setY(posList.get(0).getY()-1);
                posList.get(2).setX(posList.get(2).getX()-1);
                posList.get(2).setY(posList.get(2).getY()+1);
                posList.get(3).setX(posList.get(3).getX()-2);
                break;
        }
    }

    @Override
    public void slideLeft() {
        List<Pos> posList = getPos();
        if (getPosition() == 0) {
            setPosition(3);
        } else setPosition(getPosition() - 1);
        switch (getPosition())
        {
            case 0:
                posList.get(0).setX(posList.get(0).getX()+1);
                posList.get(0).setY(posList.get(0).getY()-1);
                posList.get(2).setX(posList.get(2).getX()-1);
                posList.get(2).setY(posList.get(2).getY()+1);
                posList.get(3).setX(posList.get(3).getX()-2);
                break;
            case 1:
                posList.get(0).setX(posList.get(0).getX()-1);
                posList.get(0).setY(posList.get(0).getY()-1);
                posList.get(2).setX(posList.get(2).getX()+1);
                posList.get(2).setY(posList.get(2).getY()+1);
                posList.get(3).setY(posList.get(3).getY()+2);
                break;
            case 2:
                posList.get(0).setX(posList.get(0).getX()-1);
                posList.get(0).setY(posList.get(0).getY()+1);
                posList.get(2).setX(posList.get(2).getX()+1);
                posList.get(2).setY(posList.get(2).getY()-1);
                posList.get(3).setX(posList.get(3).getX()+2);
                break;
            default:
                posList.get(0).setX(posList.get(0).getX()+1);
                posList.get(0).setY(posList.get(0).getY()+1);
                posList.get(2).setX(posList.get(2).getX()-1);
                posList.get(2).setY(posList.get(2).getY()-1);
                posList.get(3).setY(posList.get(3).getY()-2);
                break;
        }
    }

    @Override
    public void setStartPosition(int numColumns, int numRows) {
        int startPositionX = 1;
        int startPositionY = numColumns/2-1;
        List<Pos> posList = new ArrayList<Pos>() {};
        posList.add(new Pos(startPositionX, startPositionY-1));
        posList.add(new Pos(startPositionX, startPositionY));
        posList.add(new Pos(startPositionX, startPositionY+1));
        posList.add(new Pos(startPositionX-1, startPositionY+1));

        setPos(posList);
    }
}
