package com.example.tetris;

import java.util.List;

public abstract class Tetromino {
    protected List<Pos> pos;
    protected int position = 0;
    protected int color;

    public Tetromino(int color) {
        this.color = color;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public List<Pos> getPos() {
        return pos;
    }

    public void setPos(List<Pos> pos) {
        this.pos = pos;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void fall() {
        for (Pos position : pos) {
            position.setX(position.getX()+1);
        }
    }

    public void moveLeft() {
        for (Pos position : pos) {
            position.setY(position.getY()-1);
        }
    }

    public void moveRight() {
        for (Pos position : pos) {
            position.setY(position.getY()+1);
        }
    }

    public boolean isRightFree(int numColumns) {
        for (Pos position : pos) {
            if (position.getY() == numColumns-1) {
                return false;
            }
        }
        return true;
    }

    public boolean isLeftFree() {
        for (Pos position : pos) {
            if (position.getY() == 0) {
                return false;
            }
        }
        return true;
    }

    public abstract void slideRight();
    public abstract void slideLeft();
    public abstract void setStartPosition(int numColumns, int numRows);
}
