package com.smartass.missionsanta.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.smartass.missionsanta.SantaGame;

/**
 * Created by Cristian on 12/17/2015.
 */
public class EndState extends State {

    private Texture background;

    public EndState(GameStateManager gsm) {
        super(gsm);
        background = new Texture("end.jpg");
        cam.setToOrtho(false, SantaGame.WIDTH, SantaGame.HEIGHT);


    }

    @Override
    public void handleInput() {
        Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        cam.unproject(touchPos);
        if (Gdx.input.isTouched()) {
            gsm.set(new PlayState(gsm));
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
