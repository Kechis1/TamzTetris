package com.example.tetris;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity implements View.OnClickListener {
    TextView TVVolume;
    TextView TVCog;
    TextView TVPlay;
    TextView TVMedal;
    MediaPlayer mediaPlayer;
    private boolean isPaused = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TVVolume = findViewById(R.id.tvVolume);
        TVCog = findViewById(R.id.tvCog);
        TVPlay = findViewById(R.id.tvPlay);
        TVMedal = findViewById(R.id.tvMedal);

        TVVolume.setOnClickListener(this);
        TVPlay.setOnClickListener(this);
        TVCog.setOnClickListener(this);
        TVMedal.setOnClickListener(this);

        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.bensound_buddy);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.MISettings: {
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivityForResult(intent, 333);
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.tvPlay:
                MainActivity.this.startActivity(new Intent(MainActivity.this, GameActivity.class));
                break;
            case R.id.tvVolume:
                isPaused = !isPaused;
                if (!isPaused) {
                    mediaPlayer.pause();
                    TVVolume.setText(getString(R.string.icon_volume_up));
                } else {
                    mediaPlayer.start();
                    TVVolume.setText(getString(R.string.icon_volume_mute));
                }
                break;
            case R.id.tvCog:

                break;
            case R.id.tvMedal:

                break;
        }
    }
}
