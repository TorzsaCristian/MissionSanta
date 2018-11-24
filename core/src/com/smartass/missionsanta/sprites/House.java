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
public class House {
    private Texture house;
    private Vector2 posHouse;
    private Random rand;
    private int ID;

    private Rectangle houseBox;

    private int bonus;

    private int points = 0;

    public House(float x) {

        rand = new Random();
        houseBox = new Rectangle();

        int random = MathUtils.random(15) + 1;

        ID = MathUtils.random(1000);

        if (random % 2 == 1) {
            bonus = 1;
            if (random >= 13)
                points = 10;
            else points = 15;
        } else {
            bonus = 0;
            points = -25;
        }

        house = new Texture(Gdx.files.internal("houses/house" + random + ".png"));


        posHouse = new Vector2(x, 15);
        setHouseBox(posHouse.x, posHouse.y, house.getWidth(), house.getHeight());

    }


    public Texture getHouse() {
        return house;
    }

    public Vector2 getPosHouse() {
        return posHouse;
    }

    public void reposition(float x) {
        posHouse = new Vector2(x, 15);
        houseBox.setPosition(posHouse.x, 15);
    }

    public void setHouseBox(float x, float y, int width, int height) {
        houseBox.set(x, y, width, height);
    }

    public boolean collides(Rectangle player) {
        return player.overlaps(houseBox);
    }

    public Rectangle getBounds() {
        return houseBox;
    }

    public void dispose() {
        house.dispose();
       // houseBox = null;

    }

    public int getPoints() {
        return points;
    }

    public int getBonus() {
        return bonus;
    }

    public int getID(){
        return ID;
    }

}
