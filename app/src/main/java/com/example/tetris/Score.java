package com.example.tetris;

public class Score {
    public static final String LEVEL = "level";
    public static final String POINTS = "points";
    public static final String NAME = "name";

    private int level;
    private int points;
    private String name;

    public Score() {

    }

    public Score(int level, int points, String name) {
        this.level = level;
        this.points = points;
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}