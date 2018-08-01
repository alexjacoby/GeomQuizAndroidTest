package com.example.ajacoby.geomquiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class HintActivity extends AppCompatActivity {

    private TextView _hintText;
    private boolean _sawHint = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hint);
        boolean answer = getIntent().getBooleanExtra("hint", false);

        _hintText = (TextView) findViewById(R.id.hint_text);
        _hintText.setVisibility(View.INVISIBLE);
        _hintText.setText(answer? "True!" : "False!");
    }

    public void confirmHint(View view) {
        _hintText.setVisibility(View.VISIBLE);
        _sawHint = true;
    }

    /** Back button onClick: equivalent to pressing back button. */
    public void goBack(View view) {
        setResult(_sawHint? RESULT_OK : RESULT_CANCELED);
        finish();
    }
}
