package com.smartass.missionsanta.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.smartass.missionsanta.SantaGame;

/**
 * Created by Cristian on 12/6/2015.
 */
public class HelpState extends State {

    private int touches = 1;

    private Texture background;

    public HelpState(GameStateManager gsm) {
        super(gsm);
        background = new Texture("tuts/tut" + touches + ".jpg");
        cam.setToOrtho(false, SantaGame.WIDTH, SantaGame.HEIGHT);


    }

    @Override
    public void handleInput() {
        if (Gdx.input.justTouched()) {
            if (touches < 4) {
                touches++;
                background = new Texture("tuts/tut" + touches + ".jpg");
            } else
                gsm.set(new MapState(gsm));

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

        sb.end();

    }

    @Override
    public void dispose() {
        background.dispose();
    }
}
