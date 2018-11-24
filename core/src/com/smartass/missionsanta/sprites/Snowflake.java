package com.smartass.missionsanta.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

/**
 * Created by Cristian on 12/6/2015.
 */
public class Snowflake {

    private Texture snowflake;
    private Vector2 posSnowflake;
    private Random rand;

    private Rectangle snowflakeBox;
    private int width;
    private int height;

    public Snowflake(float x, float y) {

        rand = new Random();
        snowflakeBox = new Rectangle();

        if (rand.nextInt() % 2 == 0) {
            snowflake = new Texture("fulg1.png");
            width = snowflake.getWidth();
            height = snowflake.getHeight();

        } else {
            snowflake = new Texture("fulg2.png");
            width = snowflake.getWidth();
            height = snowflake.getHeight();
        }

        posSnowflake = new Vector2(x, y);

    }


    public Texture getSnowflake() {
        return snowflake;
    }

    public Vector2 getPosSnowflake() {
        return posSnowflake;
    }

    public void reposition(float x) {
        posSnowflake = new Vector2(x, posSnowflake.y);
    }

//    public void setCloudBox(float x, float y, int width, int height) {
//        snowflakeBox.set(x, y, width, height);
//    }

    public void dispose() {
        snowflakeBox = null;
        snowflake.dispose();
    }


    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Rectangle getBounds() {
        return snowflakeBox;
    }
}
