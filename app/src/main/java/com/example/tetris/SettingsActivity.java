package com.example.tetris;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

public class SettingsActivity extends Activity implements View.OnClickListener {
    public static final String PLAYER_NAME = "player";
    public static final String GAME_DIFFICULTY = "difficulty";
    public static final String SPEED = "speed";
    public static final String LEVEL = "level";

    EditText ETPlayer;
    Button BTSave;
    RadioGroup RGDifficulty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        BTSave = findViewById(R.id.BTSave);
        ETPlayer = findViewById(R.id.ETPlayer);
        RGDifficulty = findViewById(R.id.RGDifficulty);

        Intent intent = getIntent();
        String playerName = intent.getStringExtra(PLAYER_NAME);
        int radioButton = intent.getIntExtra(GAME_DIFFICULTY, R.id.RB1);

        ETPlayer.setText(playerName);
        RGDifficulty.check(radioButton);
        BTSave.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        Intent returnIntent = new Intent();
        returnIntent.putExtra(PLAYER_NAME, ETPlayer.getText().toString());
        returnIntent.putExtra(GAME_DIFFICULTY, RGDifficulty.getCheckedRadioButtonId());
        setResult(303, returnIntent);
        finish();
    }
}
