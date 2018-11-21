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

    public static boolean isPositionOccupied(int index, int numRows, int numColumns, int xChange, int yChange, List<Pos> positions, List<Tetromino> tetrominos) {
        for (Pos posChange : positions) {
            if (posChange.getX() + xChange >= numRows || posChange.getY() + yChange >= numColumns || posChange.getY() + yChange < 0) return true;
        }
        int i = 0;
        for (Tetromino tetromino : tetrominos) {
            if (i == index) continue;
            for (Pos pos : tetromino.getPos()) {
                for (Pos posChange : positions) {
                    if (pos.getX() == posChange.getX() + xChange && pos.getY() == posChange.getY() + yChange) {
                        return true;
                    }
                }
            }
            i++;
        }
        return false;
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

    public abstract List<Pos> getSlideLeftPos();
    public abstract List<Pos> getSlideRightPos();
    public abstract void slideRight();
    public abstract void slideLeft();
    public abstract void setStartPosition(int numColumns, int numRows);
}
