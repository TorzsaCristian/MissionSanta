package com.smartass.missionsanta.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.smartass.missionsanta.SantaGame;
import com.smartass.missionsanta.assets.HighScore;

import java.util.ArrayList;

/**
 * Created by Cristian on 12/6/2015.
 */
public class MapState extends State {

    private Texture background;

    private BitmapFont fontAfrica, fontAmericas, fontAmerican, fontAntarctica, fontAustralia, fontAsia, fontEurope;

    FreeTypeFontGenerator generator;
    FreeTypeFontGenerator.FreeTypeFontParameter parameter;

    private Vector2 posAfrica, posAmericas, posAmerican, posAntarctica, posAustralia, posAsia, posEurope;

    private ArrayList<HighScore> list;

    private int gameEnd = 1;

    private String[] continents = {"africa", "americas", "american", "antarctica", "asia", "australia", "europe"};

    public MapState(GameStateManager gsm) {
        super(gsm);
        background = new Texture("santamap.jpg");
        cam.setToOrtho(false, SantaGame.WIDTH, SantaGame.HEIGHT);

        list = new ArrayList<HighScore>();
        for (HighScore highscore : SantaGame.getallHighScores())
            list.add(highscore);

        for(int i = 0; i <= 6; i++ ){
            if(list.get(i).getScore() < 5000){
                gameEnd = 0;
            }
        }

        if(gameEnd == 1){
            gsm.set(new EndState(gsm));
        }


        SantaGame.checkMusic();

        posAustralia = new Vector2(570, 130);
        posAsia = new Vector2(545, 240);
        posAfrica = new Vector2(355, 185);
        posEurope = new Vector2(350, 275);
        posAntarctica = new Vector2(425, 35);
        posAmericas = new Vector2(180, 125);
        posAmerican = new Vector2(135, 240);


        generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/louisville.ttf"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 25;
        parameter.shadowColor = Color.BLACK;
        parameter.borderWidth = 3;
        parameter.shadowOffsetX = 3;
        parameter.shadowOffsetY = 3;
        fontAfrica = generator.generateFont(parameter); // font size 12 pixels
        fontAmericas = generator.generateFont(parameter);
        fontAmerican = generator.generateFont(parameter);
        fontAntarctica = generator.generateFont(parameter);
        fontAustralia = generator.generateFont(parameter);
        fontAsia = generator.generateFont(parameter);
        fontEurope = generator.generateFont(parameter);
        generator.dispose();


    }

    @Override
    public void handleInput() {
        Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        cam.unproject(touchPos);
        if (touchPos.x > 570 && touchPos.x < 695 && touchPos.y > 145 && touchPos.y < 180) {
            SantaGame.setBackgroundName("australia");
            gsm.set(new PlayState(gsm));
            //australia
        }
        if (touchPos.x > 545 && touchPos.x < 630 && touchPos.y > 255 && touchPos.y < 305) {
            SantaGame.setBackgroundName("asia");
            gsm.set(new PlayState(gsm));
            //asia
        }
        if (touchPos.x > 350 && touchPos.x < 440 && touchPos.y > 195 && touchPos.y < 235) {
            SantaGame.setBackgroundName("africa");
            gsm.set(new PlayState(gsm));
            //africa
        }
        if (touchPos.x > 350 && touchPos.x < 440 && touchPos.y > 290 && touchPos.y < 320) {
            SantaGame.setBackgroundName("europe");
            gsm.set(new PlayState(gsm));
            //europe
        }
        if (touchPos.x > 425 && touchPos.x < 545 && touchPos.y > 45 && touchPos.y < 80) {
            SantaGame.setBackgroundName("antarctica");
            gsm.set(new PlayState(gsm));
            //antarctica
        }
        if (touchPos.x > 180 && touchPos.x < 360 && touchPos.y > 140 && touchPos.y < 170) {
            SantaGame.setBackgroundName("americas");
            gsm.set(new PlayState(gsm));
            //americaS
        }
        if (touchPos.x > 135 && touchPos.x < 310 && touchPos.y > 255 && touchPos.y < 290) {
            SantaGame.setBackgroundName("american");
            gsm.set(new PlayState(gsm));
            //americaN
        }


    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(background, cam.position.x - (cam.viewportWidth / 2), 0);
        fontAfrica.draw(sb, "" + list.get(0).getScore() + "/5000", posAfrica.x, posAfrica.y);
        fontAmericas.draw(sb, "" + list.get(1).getScore() + "/5000", posAmericas.x, posAmericas.y);
        fontAmerican.draw(sb, "" + list.get(2).getScore() + "/5000", posAmerican.x, posAmerican.y);
        fontAntarctica.draw(sb, "" + list.get(3).getScore() + "/5000", posAntarctica.x, posAntarctica.y);
        fontAsia.draw(sb, "" + list.get(4).getScore() + "/5000", posAsia.x, posAsia.y);
        fontAustralia.draw(sb, "" + list.get(5).getScore() + "/5000", posAustralia.x, posAustralia.y);
        fontEurope.draw(sb, "" + list.get(6).getScore() + "/5000", posEurope.x, posEurope.y);

        sb.end();

    }

    @Override
    public void dispose() {
        background.dispose();
    }
}
