package com.example.tetris;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements View.OnClickListener {
    TextView TVVolume;
    TextView TVCog;
    TextView TVPlay;
    TextView TVMedal;
    MediaPlayer mediaPlayer;
    private boolean isPaused = false;
    SharedPreferences mySharedPref;

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

        mySharedPref = PreferenceManager.getDefaultSharedPreferences(this);
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
                intent.putExtra(SettingsActivity.PLAYER_NAME, mySharedPref.getString(SettingsActivity.PLAYER_NAME, SettingsActivity.PLAYER_NAME));
                intent.putExtra(SettingsActivity.GAME_DIFFICULTY, mySharedPref.getInt(SettingsActivity.GAME_DIFFICULTY, R.id.RB1));
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
                Intent intentGame = new Intent(this, GameActivity.class);
                intentGame.putExtra(SettingsActivity.GAME_DIFFICULTY, mySharedPref.getInt(SettingsActivity.GAME_DIFFICULTY, R.id.RB1));
                intentGame.putExtra(SettingsActivity.SPEED, mySharedPref.getInt(SettingsActivity.SPEED, GameActivity.SPEED_START));
                intentGame.putExtra(SettingsActivity.LEVEL, mySharedPref.getInt(SettingsActivity.LEVEL, GameActivity.LEVEL_EASY));
                intentGame.putExtra(SettingsActivity.PLAYER_NAME, mySharedPref.getString(SettingsActivity.PLAYER_NAME, SettingsActivity.PLAYER_NAME));
                startActivity(intentGame);
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
                Intent intentSettings = new Intent(this, SettingsActivity.class);
                intentSettings.putExtra(SettingsActivity.PLAYER_NAME, mySharedPref.getString(SettingsActivity.PLAYER_NAME, SettingsActivity.PLAYER_NAME));
                intentSettings.putExtra(SettingsActivity.GAME_DIFFICULTY, mySharedPref.getInt(SettingsActivity.GAME_DIFFICULTY, R.id.RB1));
                startActivityForResult(intentSettings, 333);
                break;
            case R.id.tvMedal:

                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 333 && resultCode == 303) {
            String playerName = data.getStringExtra(SettingsActivity.PLAYER_NAME);
            int radioButton = data.getIntExtra(SettingsActivity.GAME_DIFFICULTY, R.id.RB1);
            int speed;
            int level;
            switch (radioButton) {
                case R.id.RB1:
                    level = GameActivity.LEVEL_EASY;
                    break;
                case R.id.RB2:
                    level = GameActivity.LEVEL_MEDIUM;
                    break;
                default:
                    level = GameActivity.LEVEL_HARD;
                    break;
            }
            speed = GameActivity.calculateSpeedByLevel(level);
            mySharedPref.edit().putInt(SettingsActivity.GAME_DIFFICULTY, radioButton).apply();
            mySharedPref.edit().putInt(SettingsActivity.SPEED, speed).apply();
            mySharedPref.edit().putInt(SettingsActivity.LEVEL, level).apply();
            mySharedPref.edit().putString(SettingsActivity.PLAYER_NAME, playerName).apply();
        }
    }
}
