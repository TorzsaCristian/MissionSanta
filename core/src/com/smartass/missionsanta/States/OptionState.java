package com.smartass.missionsanta.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.smartass.missionsanta.SantaGame;

/**
 * Created by Cristian on 12/6/2015.
 */
public class OptionState extends State {


    private Vector2 posMusic, posSound, posBack;

    private Texture music, sound, background, back;

    public OptionState(GameStateManager gsm) {
        super(gsm);
        cam.setToOrtho(false, SantaGame.WIDTH, SantaGame.HEIGHT);

        SantaGame.checkMusic();

        background = new Texture("menubackground.jpg");
        if (SantaGame.getMusicOn() == 0) {
            music = new Texture("labels/musicoff.png");
        } else {
            music = new Texture("labels/musicon.png");
        }

        if (SantaGame.getSoudnOn() == 0) {
            sound = new Texture("labels/soundoff.png");
        } else {
            sound = new Texture("labels/soundon.png");
        }

        back = new Texture("labels/backbut.png");


        posMusic = new Vector2(600, 270);
        posSound = new Vector2(600, 175);
        posBack = new Vector2(700, 10);


    }

    @Override
    public void handleInput() {
        Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        cam.unproject(touchPos);

        if (Gdx.input.justTouched() && touchPos.x > posMusic.x && touchPos.x < posMusic.x + music.getWidth() && touchPos.y > posMusic.y && touchPos.y < posMusic.y + music.getHeight()) {
            if (SantaGame.getMusicOn() == 1) {
                SantaGame.setMusicOn(0);
                music = new Texture("labels/musicoff.png");
                SantaGame.checkMusic();
            } else {
                SantaGame.setMusicOn(1);
                music = new Texture("labels/musicon.png");
                SantaGame.checkMusic();
            }
        }

        if (Gdx.input.justTouched() && touchPos.x > posSound.x && touchPos.x < posSound.x + sound.getWidth() && touchPos.y > posSound.y && touchPos.y < posSound.y + sound.getHeight()) {
            if (SantaGame.getSoudnOn() == 1) {
                SantaGame.setSoudnOn(0);
                sound = new Texture("labels/soundoff.png");
                SantaGame.checkMusic();
            } else {
                SantaGame.setSoudnOn(1);
                sound = new Texture("labels/soundon.png");
                SantaGame.checkMusic();
            }
        }

        if (Gdx.input.justTouched() && touchPos.x < posBack.x + back.getWidth() && touchPos.y < posBack.y + back.getHeight()) {
            gsm.set(new MenuState(gsm));
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

        sb.draw(music, posMusic.x, posMusic.y);
        sb.draw(sound, posSound.x, posSound.y);
        sb.draw(back, posBack.x, posBack.y);

        sb.end();

    }

    @Override
    public void dispose() {

    }


}
