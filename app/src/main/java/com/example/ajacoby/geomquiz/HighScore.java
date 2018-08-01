package com.example.ajacoby.geomquiz;

import android.support.annotation.NonNull;

import java.util.Date;

public class HighScore implements Comparable<HighScore> {
    private String _name;
    private int _score;
    private Date _date;

    public HighScore() { }

    public HighScore(String name, int score, long date) {
        _name = name;
        _score = score;
        _date = new Date(date);
    }

    public String getName() {
        return _name;
    }

    public void setName(String name) {
        _name = name;
    }

    public int getScore() {
        return _score;
    }

    public void setScore(int score) {
        _score = score;
    }

    public long getDate() {
        return _date.getTime();
    }

    public void setDate(long date) {
        _date = new Date(date);
    }

    @Override
    public String toString() {
        return "HighScore{" +
                "Name='" + _name + '\'' +
                ", Score=" + _score +
                ", Date=" + _date +
                '}';
    }

    /** Compares scores, using date as tie-breaker (older dates are "bigger"). */
    @Override
    public int compareTo(@NonNull HighScore other) {
        if (this.getScore() == other.getScore()) {
            return -1 * (_date.compareTo(other._date)); // reverse stand date comparison
        } else {
            return (getScore() - other.getScore());
        }
    }
}
