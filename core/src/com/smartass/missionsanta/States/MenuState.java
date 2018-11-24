package com.smartass.missionsanta.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.smartass.missionsanta.SantaGame;

/**
 * Created by Cristian on 12/5/2015.
 */
public class MenuState extends State {

    private Texture background;
    private Texture playBtn, optionsBtn, exitBtn;
    private Vector3 playPos, optionsPos, exitPos;

    public MenuState(GameStateManager gsm) {
        super(gsm);
        background = new Texture("menubackground.jpg");
        cam.setToOrtho(false, SantaGame.WIDTH, SantaGame.HEIGHT);

        SantaGame.checkMusic();

        playBtn = new Texture("labels/play.png");
        playPos = new Vector3(520, 280, 0);

        optionsBtn = new Texture("labels/options.png");
        optionsPos = new Vector3(550, 160, 0);

        exitBtn = new Texture("labels/exit.png");
        exitPos = new Vector3(590, 85, 0);


    }

    @Override
    public void handleInput() {
        Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        cam.unproject(touchPos);
        if (touchPos.x > playPos.x && touchPos.x < playPos.x + playBtn.getWidth() && touchPos.y > playPos.y && touchPos.y < playPos.y + playBtn.getHeight()) {
            if (SantaGame.getPref().getBoolean("firstGame")) {
                gsm.set(new TutorialState(gsm));
                SantaGame.getPref().putBoolean("firstGame", false);
                SantaGame.getPref().flush();
            } else gsm.set(new MapState(gsm));
        }
        if (touchPos.x > optionsPos.x && optionsPos.x < optionsPos.x + optionsBtn.getWidth() && touchPos.y > optionsPos.y && touchPos.y < optionsPos.y + optionsBtn.getHeight()) {
            gsm.set(new OptionState(gsm));
        }
        if (touchPos.x > exitPos.x && exitPos.x < exitPos.x + exitBtn.getWidth() && touchPos.y > exitPos.y && touchPos.y < exitPos.y + exitBtn.getHeight()) {
            System.exit(1);
            //gsm.set(new TutorialState(gsm));
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
        sb.draw(playBtn, playPos.x, playPos.y);
        sb.draw(optionsBtn, optionsPos.x, optionsPos.y);
        sb.draw(exitBtn, exitPos.x, exitPos.y);
        sb.end();

    }

    @Override
    public void dispose() {
        background.dispose();
    }
}
