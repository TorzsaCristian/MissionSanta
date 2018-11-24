package com.smartass.missionsanta;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.smartass.missionsanta.States.GameStateManager;
import com.smartass.missionsanta.States.MenuState;
import com.smartass.missionsanta.States.SplashScreenState;
import com.smartass.missionsanta.assets.HighScore;

import java.util.ArrayList;

public class SantaGame extends Game {

    public static final int WIDTH = 800;
    public static final int HEIGHT = 480;

    public static final String TITLE = "Mission: Santa";

    private GameStateManager gsm;

    private SpriteBatch batch;

    private static AdsController adsController;

    private static String backgroundName = "europe";

    private static Preferences prefs;

    private static int highScore;

    private static String[] continents = {"africa", "americas", "american", "antarctica", "asia", "australia", "europe"};


    private static Music bgMusic;

    private static int musicOn = 1;
    private static int soudnOn = 1;

    public SantaGame(AdsController ads) {
        adsController = ads;
    }

    public static AdsController getAdsController(){
        return adsController;
    }

    @Override
    public void create() {

        bgMusic = Gdx.audio.newMusic(Gdx.files.internal("bgMusic.wav"));
        adsController.hideBannerAd();


        prefs = Gdx.app.getPreferences("MissionSantaScores");// We store the value 10 with the key of "highScore"

        for (int i = 0; i < continents.length; i++) {
            if (!prefs.contains(continents[i])) {
                prefs.putInteger(continents[i], 0);
                prefs.flush();
            }
        }

        if (!prefs.contains("firstGame")) {
            prefs.putBoolean("firstGame", true);
            prefs.flush();
        } else {
            prefs.putBoolean("firstGame", false);
            prefs.flush();
        }

        batch = new SpriteBatch();
        gsm = new GameStateManager();
        Gdx.gl.glClearColor(1, 0, 0, 1);
        //gsm.push(new MenuState(gsm));
        gsm.push(new SplashScreenState(gsm));

    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        gsm.update(Gdx.graphics.getDeltaTime());
        gsm.render(batch);
    }

    @Override
    public void resume() {
        super.resume();
        gsm.pop();
        gsm.push(new MenuState(gsm));
    }

    @Override
    public void dispose() {

    }

    public static String getBackgroundName() {
        return backgroundName;
    }

    public static void setBackgroundName(String backgroundName1) {
        backgroundName = backgroundName1;
        if (!prefs.contains(backgroundName1)) {
            prefs.putInteger(backgroundName1, 0);
            prefs.flush();
        }
    }

    public static ArrayList<HighScore> getallHighScores() {
        ArrayList<HighScore> list = new ArrayList<HighScore>();

        for (int i = 0; i < continents.length; i++) {
            if (prefs.contains(continents[i])) {
                HighScore highscore = new HighScore("", 0);
                highscore.setKey(continents[i]);
                highscore.setScore(prefs.getInteger(continents[i]));

                list.add(highscore);
            }
        }
        return list;
    }

    public static void setHighScore(String key, int score) {
        highScore = prefs.getInteger(key);
        if (score > highScore) {
            prefs.putInteger(key, score);
            prefs.flush(); // This saves the preferences file.
        }
    }

    public static Preferences getPref() {
        return prefs;
    }

    public static int getWIDTH() {
        return WIDTH;
    }

    public static int getSoudnOn() {
        return soudnOn;
    }

    public static void setSoudnOn(int varSound) {
        soudnOn = varSound;
    }

    public static int getMusicOn() {
        return musicOn;
    }

    public static void setMusicOn(int varMusic) {
        musicOn = varMusic;
    }

    public static void checkMusic() {
        if (musicOn == 1) {
            bgMusic.setLooping(true);
            bgMusic.setVolume(0.5f);
            bgMusic.play();
        } else {
            bgMusic.setLooping(true);
            bgMusic.setVolume(0.0f);
            bgMusic.play();

        }

    }
}
