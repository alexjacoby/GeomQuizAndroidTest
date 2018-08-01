package com.example.ajacoby.geomquiz;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class EndActivity extends AppCompatActivity {

    private static final String LOCAL_HIGH_SCORE_KEY = "com.example.ajacoby.geomquiz.highscore";
    private static final String DB_TEST_KEY = "test";
    private static final String DB_HIGH_SCORES_KEY = "highscores";
    private static final String TAG = "EndAct";

    private String _globalHighScoreStr;
    private int _localHighScore;
    private List<HighScore> _globalHighScores = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);

        String scoreStr = getIntent().getStringExtra("SCORE_STR");
        if (scoreStr == null) { scoreStr = "No score found!"; }
        TextView scoreView = (TextView) findViewById(R.id.final_score);
        scoreView.setText(scoreStr);
        SharedPreferences prefs = getSharedPreferences(
                "com.example.ajacoby.geomquiz", Context.MODE_PRIVATE);
        int oldLocalHighScore = prefs.getInt(LOCAL_HIGH_SCORE_KEY, 0);
        _localHighScore = oldLocalHighScore;
        int score = getIntent().getIntExtra("SCORE", 0);
        if (score > oldLocalHighScore) {
            _localHighScore = score;
            prefs.edit().putInt(LOCAL_HIGH_SCORE_KEY, score).apply();
        }
        if (score >= oldLocalHighScore) { showStar(); }
        TextView highScoreView = (TextView) findViewById(R.id.high_score);
        highScoreView.setText("High Score: " + _localHighScore);

        // Connect to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference(DB_TEST_KEY);

        // Get old global high score list and update as needed
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Log.v(TAG, "in onDataChange");
                HighScore hs = new HighScore();
                hs.setDate(System.currentTimeMillis());
                hs.setScore(_localHighScore);
                hs.setName("Goober");
                myRef.setValue(hs);
                // _globalHighScores = dataSnapshot.getValue(List.class);
                // Log.d(TAG, "Value is: " + _globalHighScores);
                // updateGlobalHighScore(myRef, _localHighScore);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

    }

    /** Called whenever database alerts us to reading a new high score value. */
    private void updateGlobalHighScore(DatabaseReference myRef, int localHighScore) {
        TextView ghsView = (TextView) findViewById(R.id.global_high_score);
        if (_globalHighScores == null) {
            // Nothing in DB yet!
            Log.i(TAG, "Initializing global high score list!");
            _globalHighScores = new ArrayList<HighScore>();
            HighScore hs = new HighScore();
            hs.setDate(System.currentTimeMillis());
            hs.setScore(_localHighScore);
            hs.setName("Goober");
            myRef.setValue(_globalHighScores);
        }
        Log.d(TAG, "globalHighScores.size=" + _globalHighScores.size());
        /**
        int ghs = Integer.parseInt(_globalHighScoreStr);
        if (localHighScore > ghs) {
            // Set new global high score
            _globalHighScoreStr = "" + localHighScore;
            myRef.setValue(_globalHighScoreStr);
        }
         */

        _globalHighScoreStr = "FOOBAR!";
        ghsView.setText("Global high score: " + _globalHighScoreStr);
    }

    private void showStar() {
        // Show star for new high score!
        ImageView newHighScore = (ImageView) findViewById(R.id.new_high_score);
        newHighScore.setVisibility(View.VISIBLE);
    }

    public void playAgain(View view) {
        Intent i = new Intent(this, QuizActivity.class);
        startActivity(i);
        finish();
    }

    public void resetHighScore(View view) {
        // I AM HERE!
    }
}
