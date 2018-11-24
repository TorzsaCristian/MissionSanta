package com.smartass.missionsanta.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

/**
 * Created by Cristian on 12/5/2015.
 */
public class Present {
    private static final int FLUCTUATION = 130;
    private Texture present;
    private Vector2 posPresent;
    private Random rand;

    public Rectangle presentBox;

    public Present(float x, float y) {

//        rand = new Random();
//
//        if (rand.nextInt() % 2 == 0) {
//            house = new Texture("smallhouse.png");
//        } else {
//
//            house = new Texture("bighouse.png");
//
//        }
        int random = MathUtils.random(4) + 1;


        present = new Texture(Gdx.files.internal("presents/pres" + random + ".png"));

        //present = new Texture("cadou.png");

        posPresent = new Vector2(x, y);

        presentBox = new Rectangle(posPresent.x, posPresent.x, present.getWidth(), present.getHeight());

    }


    public Texture getPresent() {
        return present;
    }

    public Vector2 getPosPresent() {
        return posPresent;
    }

    public void reposition(float y) {
        posPresent = new Vector2(posPresent.x, y);
        presentBox.setPosition(posPresent.x, y);

        if (y < -50) {
            dispose();
        }
    }

    public void dispose() {
        present.dispose();
    }

    public Rectangle getBounds() {
        return presentBox;
    }


}
