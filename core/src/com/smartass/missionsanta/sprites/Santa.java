package com.smartass.missionsanta.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.smartass.missionsanta.SantaGame;
import com.smartass.missionsanta.animation.Animation;

/**
 * Created by Cristian on 12/5/2015.
 */
public class Santa {

    public static final int GRAVITY = -15;

    private Vector3 position;
    private Vector3 velocity;
    private Rectangle santaBox;
    private Texture texture;
    private static int goldenboxNext = 0;

    private Animation santaAnimation;

    public Santa(int x, int y) {
        position = new Vector3(x, y, 0);
        velocity = new Vector3(0, 0, 0);

        texture = new Texture("santaanimation.png");
        santaAnimation = new Animation(new TextureRegion(texture), 3, 0.5f);

        santaBox = new Rectangle();

        setSantaBox(position.x, position.y, texture.getWidth() / 3, texture.getHeight());


    }

    public void update(float dt) {
        santaAnimation.update(dt);
        if (position.y > 0)
            velocity.add(0, GRAVITY, 0);

        velocity.scl(dt);

        position.add(0, velocity.y, 0);
        if (position.y < 0) {
            position.y = 0;
        }
        if (position.y + santaAnimation.getFrame().getTexture().getHeight() / 2 > SantaGame.HEIGHT) {
            position.y = SantaGame.HEIGHT - santaAnimation.getFrame().getTexture().getHeight() / 2;
            velocity.set(0, 0, 0);
        }


        velocity.scl(1 / dt);

        santaBox.setPosition(position.x, position.y);

    }

    public void dispose() {
        texture.dispose();
    }

    public void setSantaBox(float x, float y, int width, int height) {
        santaBox.set(x, y, width, height);
    }

    public Vector3 getPosition() {
        return position;
    }

    public TextureRegion getTexture() {
        return santaAnimation.getFrame();
    }

    public void jump() {
        velocity.y = 300;
    }

    public Rectangle getBounds() {
        return santaBox;
    }

    public static int getGoldenboxNext() {
        return goldenboxNext;
    }

    public static void setGoldenboxNext(int goldenboxNext) {
        Santa.goldenboxNext = goldenboxNext;
    }
}
