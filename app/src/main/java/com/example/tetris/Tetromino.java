package com.example.tetris;

import java.util.List;

public abstract class Tetromino {
    protected List<Pos> pos;
    protected int color;

    public Tetromino(List<Pos> pos, int color) {
        this.pos = pos;
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

    public abstract void slideRight();
    public abstract void slideLeft();
}
