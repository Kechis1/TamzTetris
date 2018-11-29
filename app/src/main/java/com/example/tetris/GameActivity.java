package com.example.tetris;

import android.app.Activity;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;

import android.os.Bundle;
import android.os.Handler;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;

import android.util.Log;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.view.Display;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;



/**
 * Required Features - Gestures, Thread, Database, Audio, Canvas, Login in to facebook -> Download data from facebook (image, name)
 * @TODO settings (speed, name)
 * @TODO Login in to facebook
 * @TODO database
 * @TODO TOP scores (rankings, table)
 */
public class GameActivity extends Activity implements GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener, View.OnClickListener {
    private static final String COLOR_GRID_BACKGROUND = "#7987A5";
    private static final String COLOR_GRID_LINES = "#90BBE6";
    private static final String COLOR_BACKGROUND_NEXT = "#CBE0FC";
    private static final int NUM_ROWS = 20;
    private static final int NUM_COLUMNS = 10;
    private static final int NUM_ROWS_NEXT = 24;
    private static final int NUM_COLUMNS_NEXT = 8;
    private static final int BOARD_HEIGHT = 480;
    private static final int BOARD_WIDTH = 240;
    private static final int BOARD_HEIGHT_NEXT = 240;
    private static final int BOARD_WIDTH_NEXT = 80;
    private static final float CELL_WIDTH = BOARD_WIDTH/NUM_COLUMNS;
    private static final float CELL_HEIGHT = BOARD_HEIGHT/NUM_ROWS;
    private static final float CELL_WIDTH_NEXT = BOARD_WIDTH_NEXT/NUM_COLUMNS_NEXT;
    private static final float CELL_HEIGHT_NEXT = BOARD_HEIGHT_NEXT/NUM_ROWS_NEXT;
    private static final int SPEED_START = 800;
    private static final int SPEED_MAX = 200;
    private static final int SPEED_STEP = 30;
    private static final int LEVEL_UP = 10;

    private List<Tetromino> tetrominos;

    private int level, score, linesCleared, currentSpeed;
    private TextView TVGameOver, TVGameOverPlay, TVScore, TVLevel;
    private boolean gamePaused, gameOver = false;
    private List<Tetromino> stack;

    private Random random = new Random();
    private Handler handler;
    private Bitmap bitmap, bitmapNext;
    private Canvas canvas, canvasNext;
    Paint paint;
    LinearLayout linearLayout;
    private ConstraintLayout CLNext;
    private GestureDetector gestureDetector;
    Button BTPause;
    private FirebaseFirestore database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        database = FirebaseFirestore.getInstance();
        gestureDetector = new GestureDetector(this, this);
        gestureDetector.setOnDoubleTapListener(this);
        linearLayout = findViewById(R.id.game_board);
        BTPause = findViewById(R.id.BTPause);
        BTPause.setOnClickListener(this);
        bitmap = Bitmap.createBitmap(BOARD_WIDTH, BOARD_HEIGHT, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);
        paint = new Paint();
        linearLayout.bringToFront();
        TVGameOver = findViewById(R.id.TVGameOver);
        TVGameOver.bringToFront();
        TVLevel = findViewById(R.id.TVLevel);
        TVScore = findViewById(R.id.TVScore);
        TVGameOverPlay = findViewById(R.id.TVGameOverPlay);
        TVGameOverPlay.bringToFront();
        TVGameOverPlay.setOnClickListener(this);
        CLNext = findViewById(R.id.CLNext);
        bitmapNext = Bitmap.createBitmap(BOARD_WIDTH_NEXT, BOARD_HEIGHT_NEXT, Bitmap.Config.ARGB_8888);
        canvasNext = new Canvas(bitmapNext);
        gameInit();
        handler = new Handler();
        handler.postDelayed(runnable, SPEED_START);
    }

    private void gameInit() {
        paintBackground();
        paintBackgroundNext();
        paintGrid();
        currentSpeed = SPEED_START;
        gameOver = false;
        gamePaused = false;
        score = level = linesCleared = 0;
        TVGameOver.setVisibility(View.GONE);
        TVGameOverPlay.setVisibility(View.GONE);
        TVScore.setText(getString(R.string.score, score));
        TVLevel.setText(getString(R.string.level, level));
        tetrominos = new ArrayList<Tetromino>(){};
        Tetromino item = getRandomTetromino(random.nextInt(7));
        item.setStartPosition(NUM_COLUMNS, NUM_ROWS);
        stack = new ArrayList<Tetromino>(){};
        stack.add(getRandomTetromino(random.nextInt(7)));
        stack.add(getRandomTetromino(random.nextInt(7)));
        stack.add(getRandomTetromino(random.nextInt(7)));
        stack.add(getRandomTetromino(random.nextInt(7)));
        paintTetrominoNext();
       // generateTetrominos();
        tetrominos.add(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.BTPause:
                gamePaused = !gamePaused;
                if (!gamePaused) {
                    BTPause.setText(getString(R.string.icon_pause));
                } else {
                    BTPause.setText(getString(R.string.icon_play));
                }
                break;
            case R.id.TVGameOverPlay:
                gameInit();
                break;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    private int calculateScore(int lines) {
        int points;
        switch (lines) {
            case 1:
                points = 40;
                break;
            case 2:
                points = 100;
                break;
            case 3:
                points = 300;
                break;
            default:
                points = 1200;
                break;
        }
        return points * (level + 1);
    }

    private List<Integer> getFullRows() {
        List<Integer> rows = new ArrayList<>();
        List<Integer> map = new ArrayList<Integer>(NUM_ROWS) {};
        for (int i = 0; i < NUM_ROWS; i++) {
            map.add(i, 0);
        }
        for (Tetromino tetro : tetrominos) {
            for (Pos pos : tetro.getPos()) {
                int index = pos.getX();
                map.set(index, map.get(index)+1);
                if (map.get(index) == NUM_COLUMNS) {
                    rows.add(index);
                }
            }
        }
        return rows;
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (gamePaused || gameOver) {
                handler.postDelayed(this, currentSpeed);
                return;
            }
            paintBackground();
            paintGrid();
            paintTetrominos();
            int i = 0;
            int size = tetrominos.size();
            for (Tetromino tetro : tetrominos) {
                i++;
                boolean fall = true;
                if (Tetromino.isPositionOccupied(i-1, NUM_ROWS, NUM_COLUMNS, 1, 0, tetro.getPos(), tetrominos)) {
                    fall = false;
                }
                if (fall) {
                    tetro.fall();
                } else if (i == size) {
                    List<Integer> fullRows = getFullRows();
                    if (fullRows.size() > 0) {
                        for (Tetromino tet : tetrominos) {
                            for (int a : fullRows) {
                                for (int h = 0; h < tet.getPos().size(); h++) {
                                    if (tet.getPos().get(h).getX() == a) {
                                        tet.getPos().remove(h);
                                        h = -1;
                                    }
                                }
                            }
                        }
                        linesCleared += fullRows.size();
                        score += calculateScore(fullRows.size());
                        TVScore.setText(getString(R.string.score, score));

                        if (linesCleared >= LEVEL_UP) {
                            linesCleared -= LEVEL_UP;
                            level += 1;
                            TVLevel.setText(getString(R.string.level, level));
                            if (currentSpeed > SPEED_MAX) {
                                currentSpeed -= SPEED_STEP;
                            }
                        }
                    }
                    Tetromino item = stack.get(0);
                    item.setStartPosition(NUM_COLUMNS, NUM_ROWS);
                    tetrominos.add(item);
                    if (Tetromino.isPositionOccupied(tetrominos.size()-1, NUM_ROWS, NUM_COLUMNS, 1, 0, item.getPos(), tetrominos)) {
                        gameOver = true;
                        TVGameOver.setVisibility(View.VISIBLE);
                        TVGameOverPlay.setVisibility(View.VISIBLE);
                    }
                    stack.remove(0);
                    stack.add(getRandomTetromino(random.nextInt(7)));
                    paintBackgroundNext();
                    paintTetrominoNext();
                }
            }
            handler.postDelayed(this, currentSpeed);
        }
    };

    private void generateTetrominos() {
        TetrominoI a = new TetrominoI();
        List<Pos> pos = new ArrayList<Pos>() {};
        pos.add(new Pos(19, 0));
        pos.add(new Pos(19, 1));
        pos.add(new Pos(19, 2));
        pos.add(new Pos(19, 3));
        a.setPos(pos);
        TetrominoI b = new TetrominoI();
        List<Pos> pos1 = new ArrayList<Pos>() {};
        pos1.add(new Pos(19, 4));
        pos1.add(new Pos(19, 5));
        pos1.add(new Pos(19, 6));
        pos1.add(new Pos(19, 7));
        b.setPos(pos1);
        TetrominoI c= new TetrominoI();
        List<Pos> pos2 = new ArrayList<Pos>() {};
        pos2.add(new Pos(18, 4));
        pos2.add(new Pos(18, 5));
        pos2.add(new Pos(18, 6));
        pos2.add(new Pos(18, 7));
        c.setPos(pos2);

        TetrominoI d= new TetrominoI();
        List<Pos> pos3 = new ArrayList<Pos>() {};
        pos3.add(new Pos(18, 0));
        pos3.add(new Pos(18, 1));
        pos3.add(new Pos(18, 2));
        pos3.add(new Pos(18, 3));
        d.setPos(pos3);

        TetrominoI e= new TetrominoI();
        List<Pos> pos4 = new ArrayList<Pos>() {};
        pos4.add(new Pos(17, 4));
        pos4.add(new Pos(17, 5));
        pos4.add(new Pos(17, 6));
        pos4.add(new Pos(17, 7));
        e.setPos(pos4);

        TetrominoI f= new TetrominoI();
        List<Pos> pos5 = new ArrayList<Pos>() {};
        pos5.add(new Pos(16, 0));
        pos5.add(new Pos(16, 1));
        pos5.add(new Pos(16, 2));
        pos5.add(new Pos(16, 3));
        f.setPos(pos5);

        TetrominoI g= new TetrominoI();
        List<Pos> pos6 = new ArrayList<Pos>() {};
        pos6.add(new Pos(16, 4));
        pos6.add(new Pos(16, 5));
        pos6.add(new Pos(16, 6));
        pos6.add(new Pos(16, 7));
        g.setPos(pos6);

        tetrominos.add(a);
        tetrominos.add(b);
        tetrominos.add(c);
        tetrominos.add(d);
        tetrominos.add(e);
        tetrominos.add(f);
        tetrominos.add(g);
    }

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

    public void paintBackgroundNext() {
        paint.setColor(Color.parseColor(GameActivity.COLOR_BACKGROUND_NEXT));
        canvasNext.drawRect(0,0, BOARD_WIDTH_NEXT, BOARD_HEIGHT_NEXT, paint);
    }

    public void paintTetrominoNext() {
        for (int i = 0; i < stack.size(); i++) {
            paint.setColor(stack.get(i).getColor());
            stack.get(i).setStartPosition(NUM_COLUMNS_NEXT, NUM_ROWS_NEXT);
            for (Pos pos : stack.get(i).getPos()) {
                float left = pos.getY() * CELL_WIDTH_NEXT;
                float top = (float) (pos.getX() * CELL_HEIGHT_NEXT + (i+1) * 4.5 * CELL_HEIGHT_NEXT);
                canvasNext.drawRect(left, top, left + CELL_WIDTH_NEXT, top + CELL_HEIGHT_NEXT, paint);
            }
        }

        paint.setStrokeWidth(1);
        for (int i = 0; i < stack.size(); i++) {
            paint.setColor(stack.get(i).getBorderColor());
            for (Pos pos : stack.get(i).getPos()) {
                float left = pos.getY() * CELL_WIDTH_NEXT;
                float top = (float) (pos.getX() * CELL_HEIGHT_NEXT + (i+1) * 4.5 * CELL_HEIGHT_NEXT);
                canvasNext.drawLine(left, top,left + CELL_WIDTH_NEXT, top, paint);
                canvasNext.drawLine(left, top, left, top + CELL_HEIGHT_NEXT, paint);
                canvasNext.drawLine(left,top + CELL_HEIGHT_NEXT,left + CELL_WIDTH_NEXT, top + CELL_HEIGHT_NEXT, paint);
                canvasNext.drawLine(left + CELL_WIDTH_NEXT, top, left + CELL_WIDTH_NEXT, top + CELL_HEIGHT_NEXT, paint);
            }
        }

        CLNext.setBackground(new BitmapDrawable(getApplicationContext().getResources(), bitmapNext));
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

        paint.setStrokeWidth(1.5f);
        for (int i = 0; i < tetrominos.size(); i++) {
            paint.setColor(tetrominos.get(i).getBorderColor());
            for (Pos pos : tetrominos.get(i).getPos()) {
                float left = pos.getY() * CELL_WIDTH;
                float top = pos.getX() * CELL_HEIGHT;
                canvas.drawLine(left, top,left + CELL_WIDTH, top, paint);
                canvas.drawLine(left, top, left, top + CELL_HEIGHT, paint);
                canvas.drawLine(left,top + CELL_HEIGHT,left + CELL_WIDTH, top + CELL_HEIGHT, paint);
                canvas.drawLine(left + CELL_WIDTH, top, left + CELL_WIDTH, top + CELL_HEIGHT, paint);
            }
        }
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        if (gamePaused || gameOver) {
            return false;
        }
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int lastIndex = tetrominos.size() - 1;
        if (e.getY() < size.y/2) {
            if (e.getX() < size.x / 2) {
                boolean slideLeft = true;
                List<Pos> positions = tetrominos.get(lastIndex).getSlideLeftPos();
                if (Tetromino.isPositionOccupied(lastIndex, NUM_ROWS, NUM_COLUMNS, 0, 0, positions, tetrominos)) {
                    slideLeft = false;
                }
                if (slideLeft) {
                    tetrominos.get(lastIndex).slideLeft();
                }
            } else {
                boolean slideRight = true;
                List<Pos> positions = tetrominos.get(lastIndex).getSlideRightPos();
                if (Tetromino.isPositionOccupied(lastIndex, NUM_ROWS, NUM_COLUMNS, 0, 0, positions, tetrominos)) {
                    slideRight = false;
                }
                if (slideRight) {
                    tetrominos.get(lastIndex).slideRight();
                }
            }
        } else {
            if (e.getX() < size.x / 4) {
                boolean moveLeft = true;
                if (Tetromino.isPositionOccupied(lastIndex, NUM_ROWS, NUM_COLUMNS,0, -1, tetrominos.get(lastIndex).getPos(), tetrominos)) {
                    moveLeft = false;
                }
                if (moveLeft) {
                    tetrominos.get(lastIndex).moveLeft();
                }
            } else if (e.getX() > size.x - size.x / 4) {
                boolean moveRight = true;
                if (Tetromino.isPositionOccupied(lastIndex, NUM_ROWS, NUM_COLUMNS, 0, 1, tetrominos.get(lastIndex).getPos(), tetrominos)) {
                    moveRight = false;
                }
                if (moveRight) {
                    tetrominos.get(lastIndex).moveRight();
                }
            } else {
                while (!Tetromino.isPositionOccupied(lastIndex, NUM_ROWS, NUM_COLUMNS, 1, 0, tetrominos.get(lastIndex).getPos(), tetrominos)) {
                    tetrominos.get(lastIndex).fall();
                }
            }
        }
        paintBackground();
        paintGrid();
        paintTetrominos();
        return true;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

        return false;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        return false;
    }
}
