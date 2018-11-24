package com.smartass.missionsanta.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

/**
 * Created by Cristian on 12/5/2015.
 */
public class Cloud {
    private Texture cloud;
    private Vector2 posCloud;
    private Random rand;

    //private Rectangle cloudBox;
    private int width;
    private int height;

    public Cloud(float x, float y) {

        rand = new Random();
        //cloudBox = new Rectangle();

        if (rand.nextInt() % 2 == 0) {
            cloud = new Texture("cloud1.png");
            width = cloud.getWidth();
            height = cloud.getHeight();

        } else {
            cloud = new Texture("cloud1.png");
            width = cloud.getWidth();
            height = cloud.getHeight();
        }

        posCloud = new Vector2(x, y);

    }


    public Texture getCloud() {
        return cloud;
    }

    public Vector2 getPosCloud() {
        return posCloud;
    }

    public void reposition(float x) {
        posCloud = new Vector2(x, posCloud.y);
    }

//    public void setCloudBox(float x, float y, int width, int height) {
//        cloudBox.set(x, y, width, height);
//    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

//    public boolean collides(Rectangle player) {
//        return player.overlaps(cloudBox);
//    }
//
//    public Rectangle getBounds() {
//        return cloudBox;
//    }

    public void dispose(){
        cloud.dispose();
    }
}
