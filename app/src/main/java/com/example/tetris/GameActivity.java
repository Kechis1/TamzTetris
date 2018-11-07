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
    private static final float CELL_WIDTH = BOARD_WIDTH/NUM_COLUMNS;
    private static final float CELL_HEIGHT = BOARD_HEIGHT/NUM_ROWS;
    private static final int SPEED = 400;

    private List<Tetromino> tetrominos;

    Random random = new Random();
    Handler handler;
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
        linearLayout.bringToFront();
        tetrominos = new ArrayList<Tetromino>(){};
        Tetromino item = getRandomTetromino(random.nextInt(6));
        item.setStartPosition(NUM_COLUMNS, NUM_ROWS);
        tetrominos.add(item);
        handler = new Handler();
        handler.postDelayed(runnable, SPEED);
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            paintBackground();
            paintTetrominos();
            paintGrid();
            int i = 0;
            int size = tetrominos.size();
            for (Tetromino tetro : tetrominos) {
                i++;
                boolean fall = true;
                for (Pos pos : tetro.getPos()) {
                    if (isPositionOccupied(i-1,pos.getX() + 1, pos.getY())) {
                        fall = false;
                    }
                }
                if (fall) {
                    tetro.fall();
                } else if (i == size) {
                    Tetromino item = getRandomTetromino(random.nextInt(6));
                    item.setStartPosition(NUM_COLUMNS, NUM_ROWS);
                    tetrominos.add(item);
                }
            }
            handler.postDelayed(this, SPEED);
        }
    };

    private Tetromino getRandomTetromino(int number) {
        Tetromino newItem;
        switch (number) {
            case 0: newItem = new TetrominoO(); break;
            case 1: newItem = new TetrominoI(); break;
            case 2: newItem = new TetrominoJ(); break;
            case 3: newItem = new TetrominoL(); break;
            case 4: newItem = new TetrominoZ(); break;
            case 5: newItem = new TetrominoT(); break;
            default: newItem = new TetrominoS(); break;
        }
        return newItem;
    }

    public void paintBackground() {
        paint.setColor(Color.parseColor(GameActivity.COLOR_GRID_BACKGROUND));
        canvas.drawRect(0,0, BOARD_WIDTH, BOARD_HEIGHT, paint);
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
        linearLayout.setBackground(new BitmapDrawable(getApplicationContext().getResources(), bitmap));
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

    private boolean isPositionOccupied(int index, int x, int y) {
        if (x >= NUM_ROWS || y >= NUM_COLUMNS || y < 0) return true;
        int i = 0;
        for (Tetromino tetromino : tetrominos) {
            if (i == index) continue;
            for (Pos pos : tetromino.getPos()) {
                if (pos.getX() == x && pos.getY() == y) {
                    return true;
                }
            }
            i++;
        }
        return false;
    }

}
