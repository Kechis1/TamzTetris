package com.example.tetris;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {
    TextView TVExit;
    TextView TVVolume;
    TextView TVCog;
    TextView TVPlay;
    TextView TVMedal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TVExit = findViewById(R.id.tvExit);
        TVVolume = findViewById(R.id.tvVolume);
        TVCog = findViewById(R.id.tvCog);
        TVPlay = findViewById(R.id.tvPlay);
        TVMedal = findViewById(R.id.tvMedal);
    }
}
