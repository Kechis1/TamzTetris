package com.example.tetris;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.shapes.Shape;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameActivity extends Activity {
    private static final String COLOR_GRID_BACKGROUND = "#7987A5";
    private static final String COLOR_GRID_LINES = "#90BBE6";
    private static final int NUM_ROWS = 20;
    private static final int NUM_COLUMNS = 10;
    private static final int BOARD_HEIGHT = 480;
    private static final int BOARD_WIDTH = 280;
    private static final int START_POS_X = 0;
    private static final int START_POS_Y = NUM_COLUMNS/2-1;
    private static final float CELL_WIDTH = BOARD_WIDTH/NUM_COLUMNS;
    private static final float CELL_HEIGHT = BOARD_HEIGHT/NUM_ROWS;

    private List<Tetromino> tetrominos;

    Bitmap bitmap;
    Canvas canvas;
    Paint paint;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        linearLayout = findViewById(R.id.game_board);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        bitmap = Bitmap.createBitmap(BOARD_WIDTH, BOARD_HEIGHT, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);
        paint = new Paint();
        linearLayout.setBackground(new BitmapDrawable(getApplicationContext().getResources(), bitmap));
        linearLayout.bringToFront();
        paint.setColor(Color.parseColor(GameActivity.COLOR_GRID_BACKGROUND));
        canvas.drawRect(0,0, BOARD_WIDTH, BOARD_HEIGHT, paint);


        tetrominos = new ArrayList<Tetromino>(){};
        List<Pos> posList = new ArrayList<Pos>() {};
        posList.add(new Pos(START_POS_X, START_POS_Y));
        posList.add(new Pos(START_POS_X, START_POS_Y+1));
        posList.add(new Pos(START_POS_X+1, START_POS_Y));
        posList.add(new Pos(START_POS_X+1, START_POS_Y+1));

        tetrominos.add(new TetrominoO(posList));

        paintTetrominos();
        paintGrid();
    }

    public void paintGrid() {
        paint.setStrokeWidth(1);
        paint.setColor(Color.parseColor(GameActivity.COLOR_GRID_LINES));
        for (int i = 1; i <= GameActivity.NUM_ROWS; i++) {
            float xY = i * (BOARD_HEIGHT / GameActivity.NUM_ROWS);
            canvas.drawLine(0, xY, BOARD_WIDTH, xY, paint);
        }
        for (int j = 1; j <= GameActivity.NUM_COLUMNS; j++) {
            float yX = j * (BOARD_WIDTH / GameActivity.NUM_COLUMNS);
            canvas.drawLine(yX,0, yX, BOARD_HEIGHT, paint);
        }

    }

    public void paintTetrominos() {
        for (Tetromino tetromino : tetrominos) {
            paint.setColor(tetromino.getColor());
            for (Pos pos : tetromino.getPos()) {
                float left = pos.getY() * CELL_WIDTH;
                float top = pos.getX() * CELL_HEIGHT;
                canvas.drawRect(left, top, left + CELL_WIDTH, top + CELL_HEIGHT, paint);
            }
        }
    }
}
