package com.example.ajacoby.geomquiz;

public class Question {
    private int _textResId;
    private boolean _answerTrue;

    public Question(int textResId, boolean answerTrue) {
        _textResId = textResId;
        _answerTrue = answerTrue;
    }

    public int getTextResId() {
        return _textResId;
    }

    public void setTextResId(int textResId) {
        _textResId = textResId;
    }

    public boolean isAnswerTrue() {
        return _answerTrue;
    }

    public void setAnswerTrue(boolean answerTrue) {
        _answerTrue = answerTrue;
    }
}
