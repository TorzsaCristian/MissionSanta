package com.smartass.missionsanta.assets;

/**
 * Created by Cristian on 12/6/2015.
 */
public class HighScore {

    private String key;
    private int score;

    public HighScore(String key, int score) {
        this.key = key;
        this.score = score;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
