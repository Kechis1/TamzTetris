package com.example.tetris;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;

public class HighScoreActivity extends Activity {

    FirebaseFirestore db;
    ListView LVScore;
    private List<Score> rows = new ArrayList<Score>() {};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score_activity);

        db = FirebaseFirestore.getInstance();
        LVScore = findViewById(R.id.LVScore);
        fillListItems(this);
    }

    private void fillListItems(final Context context) {
        db.collection("scores")
                .orderBy("points")
                .limit(10)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot documentSnapshots) {
                        if (!documentSnapshots.isEmpty()) {
                            List<Score> types = documentSnapshots.toObjects(Score.class);
                            rows.addAll(types);
                            ScoreAdapter adapter = new ScoreAdapter(context, R.layout.list_score_layout, rows);
                            LVScore.setAdapter(adapter);
                        }
                    }});
    }
}
