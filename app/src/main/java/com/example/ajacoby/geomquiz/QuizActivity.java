package com.example.ajacoby.geomquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class QuizActivity extends AppCompatActivity {

    // Data Fields
    private int _currentIndex = 0;
    private int _score = 0;
    private Question[] _questionBank = {
            new Question(R.string.question1, false),
            new Question(R.string.question2, false),
            new Question(R.string.question3, true),
            new Question(R.string.question4, false),
            new Question(R.string.question5, true),
    };

    // UI Element Fields
    private TextView _textView;
    private TextView _scoreView;
    private Button _trueBtn;
    private Button _falseBtn;
    private Button _nextBtn;

    // Methods
    private void updateScreen() {
        int question = _questionBank[_currentIndex].getTextResId();
        _textView.setText(question);
        _scoreView.setText("Score: " + _score + " / " + _currentIndex);
    }

    private void checkAnswer(boolean userChoice) {
        boolean answer = _questionBank[_currentIndex].isAnswerTrue();
        if (userChoice == answer) {
            _score++;
        }
        int stringId = (userChoice == answer)? R.string.correct_toast : R.string.incorrect_toast;
        Toast.makeText(this, stringId, Toast.LENGTH_SHORT).show();
        next();
    }

    private void next() {
        _currentIndex++;
        if (_currentIndex == _questionBank.length) {
            Intent i = new Intent(QuizActivity.this, EndActivity.class);
            i.putExtra("SCORE", _score);
            i.putExtra("SCORE_STR", "Score: " + _score + " / " + _questionBank.length);
            startActivity(i);
            finish();
            return;
        }
        updateScreen();
    }

    /** onClick for hintButton: Shows HintActivity. */
    public void giveHint(View view) {
        Intent i = new Intent(this, HintActivity.class);
        i.putExtra("hint", _questionBank[_currentIndex].isAnswerTrue());
        startActivityForResult(i, 0); // Ignore request code
    }

    /**
     * Called after returning from hint screen: subtracts point if they looked at hint, but
     * not if they cancelled w/out seeing hint.
     */
    @Override
    protected void onActivityResult(int requestCode,  int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            // User saw hint
            _score--;
            updateScreen();
        } else if (resultCode == RESULT_CANCELED) {
            // User didn't see hint
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        // Inflate UI elements
        _trueBtn = (Button) findViewById(R.id.true_btn);
        _falseBtn = (Button) findViewById(R.id.false_btn);
        _nextBtn = (Button) findViewById(R.id.next_btn);
        _textView = (TextView) findViewById(R.id.question_text);
        _scoreView = (TextView) findViewById(R.id.score);

        updateScreen();

        // Add listeners
        _trueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(true);
            }
        });

        _falseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(false);
            }
        });

        _nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                next();
            }
        });

    }

}
