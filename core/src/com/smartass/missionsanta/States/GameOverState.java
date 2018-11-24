package com.smartass.missionsanta.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.smartass.missionsanta.SantaGame;

/**
 * Created by Cristian on 12/6/2015.
 */
public class GameOverState extends State {

    private Texture background;

    public GameOverState(GameStateManager gsm) {
        super(gsm);
        background = new Texture("restartmenu.jpg");
        cam.setToOrtho(false, SantaGame.WIDTH, SantaGame.HEIGHT);


    }

    @Override
    public void handleInput() {
        Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        cam.unproject(touchPos);
        if (Gdx.input.isTouched()) {
            if (touchPos.x > 250 && touchPos.x < 550 && touchPos.y > 240 && touchPos.y < 350) {
                gsm.set(new PlayState(gsm));
            }

            if (touchPos.x > 320 && touchPos.x < 480 && touchPos.y > 150 && touchPos.y < 210) {
                gsm.set(new MenuState(gsm));
            }
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
