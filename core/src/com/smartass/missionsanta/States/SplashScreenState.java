package com.smartass.missionsanta.States;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;
import com.smartass.missionsanta.SantaGame;

/**
 * Created by Cristian on 12/17/2015.
 */
public class SplashScreenState extends State {

    private Texture background;
    private int rendCount; //** render count **//
    private long startTime; //** time app started **//
    private long endTime; //** time app ended **//

    public SplashScreenState(GameStateManager gsm) {
        super(gsm);
        background = new Texture("loading.jpg");
        cam.setToOrtho(false, SantaGame.WIDTH, SantaGame.HEIGHT);

        startTime = TimeUtils.millis();


    }

    @Override
    public void handleInput() {


    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void render(SpriteBatch sb) {

        if (TimeUtils.millis() > (startTime + 2000)) {
            gsm.set(new MenuState(gsm));
            gsm.push(new MenuState(gsm));
        }

        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(background, cam.position.x - (cam.viewportWidth / 2), 0);
        sb.end();

    }

    @Override
    public void dispose() {
        background.dispose();

        endTime = TimeUtils.millis();
    }
}

