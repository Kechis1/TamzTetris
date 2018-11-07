package com.example.tetris;

import java.util.List;

public abstract class Tetromino {
    protected List<Pos> pos;
    protected int color;

    public Tetromino(int color) {
        this.color = color;
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

    public abstract void slideRight();
    public abstract void slideLeft();
    public abstract void setStartPosition(int numColumns, int numRows);
}
