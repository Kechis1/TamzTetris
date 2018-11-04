package com.example.tetris;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {
    TextView TVVolume;
    TextView TVCog;
    TextView TVPlay;
    TextView TVMedal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TVVolume = findViewById(R.id.tvVolume);
        TVCog = findViewById(R.id.tvCog);
        TVPlay = findViewById(R.id.tvPlay);
        TVMedal = findViewById(R.id.tvMedal);

        TVPlay.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MainActivity.this.startActivity(new Intent(MainActivity.this, GameActivity.class));
                    }
                }
        );

    }
}
