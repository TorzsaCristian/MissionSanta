package com.smartass.missionsanta.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.smartass.missionsanta.SantaGame;
import com.smartass.missionsanta.sprites.Cloud;
import com.smartass.missionsanta.sprites.House;
import com.smartass.missionsanta.sprites.Present;
import com.smartass.missionsanta.sprites.Santa;
import com.smartass.missionsanta.sprites.Snowflake;

import java.util.Iterator;
import java.util.Random;

/**
 * Created by Cristian on 12/5/2015.
 */
public class PlayState extends State implements InputProcessor {

    private Array<House> houses;
    private Array<Present> presents;
    private Array<Cloud> clouds;
    private Array<ParticleEffect> explosions;
    private Array<Snowflake> snowflakes;

    private Vector3 touchPos;

    private Sound dropSound;

    private int score = 0;
    private int lastID = 0;


    private Santa santa;
    private Texture bg;
    private Texture ground;
    private Vector2 groundPos1, groundPos2;
    private Texture scoreBg;

    private int allow = 0;

    private long lastDropTime;

    private long lastSnowFlakeDropTime;

    private long lastCloudTime;

    private ParticleEffect effectGood, effectBad;

    ParticleEffectPool bombEffectPoolBad, bombEffectPoolGood;
    Array<ParticleEffectPool.PooledEffect> effects = new Array();

    private BitmapFont font;

    FreeTypeFontGenerator generator;
    FreeTypeFontGenerator.FreeTypeFontParameter parameter;

    private Texture snowFlakeimg;


    private Random rand;



    public PlayState(GameStateManager gsm) {
        super(gsm);
        santa = new Santa(100, 200);
        cam.setToOrtho(false, SantaGame.WIDTH, SantaGame.HEIGHT);

        SantaGame.checkMusic();
       // SantaGame.getAdsController().showBannerAd();

        bg = new Texture(Gdx.files.internal("backgrounds/" + SantaGame.getBackgroundName() + ".jpg"));
        ground = new Texture("ground.png");
        scoreBg = new Texture("scorebg.png");


        dropSound = Gdx.audio.newSound(Gdx.files.internal("sleighbell.wav"));


        groundPos1 = new Vector2(cam.position.x - cam.viewportWidth / 2, 0);
        groundPos2 = new Vector2(cam.position.x - cam.viewportWidth / 2 + ground.getWidth(), 0);

        generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/impasto.ttf"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 30;
        parameter.shadowOffsetX = 3;
        parameter.shadowOffsetY = 3;
        font = generator.generateFont(parameter); // font size 12 pixels
        font.setColor(new Color(255, 192, 0, 1));
        generator.dispose();


        houses = new Array<House>();
        presents = new Array<Present>();
        clouds = new Array<Cloud>();
        explosions = new Array<ParticleEffect>();

        // font = new BitmapFont();
        //font.setColor(Color.WHITE);


        houses.add(new House(SantaGame.WIDTH));

        rand = new Random();

        snowflakes = new Array<Snowflake>();
        spawnSnowflakes();

        touchPos = new Vector3();

        effectGood = new ParticleEffect();
        effectGood.load(Gdx.files.internal("effects/presentExplosionGood.p"), Gdx.files.internal(""));

//
        effectBad = new ParticleEffect();
        effectBad.load(Gdx.files.internal("effects/presentExplosionBad.p"), Gdx.files.internal(""));

        ParticleEffect bombGood = new ParticleEffect();
        bombGood.load(Gdx.files.internal("effects/presentExplosionGood.p"), Gdx.files.internal(""));

        bombGood.setEmittersCleanUpBlendFunction(true);

        bombEffectPoolGood = new ParticleEffectPool(bombGood, 1, 2);

        ParticleEffect bombBad = new ParticleEffect();
        bombBad.load(Gdx.files.internal("effects/presentExplosionBad.p"), Gdx.files.internal(""));

        bombBad.setEmittersCleanUpBlendFunction(true);

        bombEffectPoolBad = new ParticleEffectPool(bombBad, 1, 2);


    }

    @Override
    protected void handleInput() {
        if (Gdx.input.isTouched() && Gdx.input.getX() < Gdx.graphics.getWidth() / 2) {
            santa.jump();
        }

        if (Gdx.input.isTouched() && Gdx.input.getX() > Gdx.graphics.getWidth() / 2) {
            if (allow == 1)
                spawnPresents();
        }

    }

    @Override
    public void update(float dt) {
        handleInput();
        updateGround(dt);
        santa.update(dt);
        if (houses.peek().getPosHouse().x + houses.peek().getHouse().getWidth() < SantaGame.WIDTH) {
            houses.add(new House(SantaGame.WIDTH));
        }


        for (int i = 0; i < houses.size; i++) {
            if (houses.get(i).collides(santa.getBounds())) {
                SantaGame.setHighScore(SantaGame.getBackgroundName(), score);
                gsm.set(new GameOverState(gsm));
                //SantaGame.getAdsController().hideBannerAd();
            }
        }

        Iterator<House> iterHouse = houses.iterator();
        while (iterHouse.hasNext()) {
            House house = iterHouse.next();
            house.reposition(house.getPosHouse().x - dt * 200);
            if (house.getPosHouse().x < -250) {
                house.dispose();
                iterHouse.remove();
            }
        }

        Iterator<Cloud> iterClouds = clouds.iterator();
        while (iterClouds.hasNext()) {
            Cloud cloud = iterClouds.next();
            cloud.reposition(cloud.getPosCloud().x - dt * 100);
            if (cloud.getPosCloud().x < -200) {
                cloud.dispose();
                iterClouds.remove();
            }
        }

        // System.out.println("Houses nr: " + houses.size);


//        for (Present present : presents) {
//            if (present.getPosPresent().y > 0) {
//                present.reposition(present.getPosPresent().y - dt * 300);
//            } else {
//                present.dispose();
//            }
//
//
//        }

        Iterator<Present> iterPresent = presents.iterator();
        while (iterPresent.hasNext()) {
            Present present = iterPresent.next();
            present.reposition(present.getPosPresent().y - dt * 500);

//            //checkHouseHit();
            if (present.getPosPresent().y < -50) {
                //present.dispose();
                iterPresent.remove();
            }


        }


//        for (Cloud cloud : clouds) {
//            if (cloud.getPosCloud().x + cloud.getCloud().getWidth() > 0) {
//                cloud.reposition(cloud.getPosCloud().x - dt * 100);
//            }
//        }

        for (Snowflake snowflake : snowflakes) {
            if (snowflake.getPosSnowflake().x + snowflake.getSnowflake().getWidth() > 0) {
                snowflake.reposition(snowflake.getPosSnowflake().x - dt * 100);
            }
            if (snowflake.getPosSnowflake().y < 0) {
                snowflake.dispose();
            }

        }

        checkHouseHit();

        effectGood.update(dt);
        effectBad.update(dt);

        if (score < 0) score = 0;


    }

    @Override
    public void render(SpriteBatch sb) {
        if (TimeUtils.millis() - lastDropTime > 1000) allow = 1;
        if (TimeUtils.millis() - lastCloudTime > ((rand.nextInt(7 - 3) + 3) * 2000)) spawnClouds();
        if (TimeUtils.nanoTime() - lastSnowFlakeDropTime > 100000000 * 0.75) spawnSnowflakes();

        Iterator<Snowflake> iter = snowflakes.iterator();
        while (iter.hasNext()) {
            Snowflake snowflake = iter.next();
            snowflake.getPosSnowflake().y -= 100 * Gdx.graphics.getDeltaTime();
            if (snowflake.getPosSnowflake().y + 64 < 0) iter.remove();
        }


        sb.setProjectionMatrix(cam.combined);
        sb.begin();


        sb.draw(bg, cam.position.x - (cam.viewportWidth / 2), 0);
        sb.draw(santa.getTexture(), santa.getPosition().x, santa.getPosition().y);
        for (Present present : presents) {
            sb.draw(present.getPresent(), present.getPosPresent().x, present.getPosPresent().y);
        }
        for (House house : houses) {
            sb.draw(house.getHouse(), house.getPosHouse().x, house.getPosHouse().y);
        }
        for (Cloud cloud : clouds) {
            sb.draw(cloud.getCloud(), cloud.getPosCloud().x, cloud.getPosCloud().y);
        }
        effectBad.draw(sb);
        effectGood.draw(sb);

        for (int i = effects.size - 1; i >= 0; i--) {
            ParticleEffectPool.PooledEffect effect = effects.get(i);
            effect.draw(sb, Gdx.graphics.getDeltaTime());
            if (effect.isComplete()) {
                effect.free();
                effects.removeIndex(i);
            }
        }

        for (Snowflake snowflake : snowflakes) {
            sb.draw(snowflake.getSnowflake(), snowflake.getPosSnowflake().x, snowflake.getPosSnowflake().y);
        }

        sb.draw(ground, groundPos1.x, groundPos1.y);
        sb.draw(ground, groundPos2.x, groundPos2.y);
        sb.draw(scoreBg, SantaGame.WIDTH - scoreBg.getWidth() - 5, SantaGame.HEIGHT - scoreBg.getHeight() - 5);

        font.draw(sb, "" + score, SantaGame.WIDTH - scoreBg.getWidth() + 40, SantaGame.HEIGHT - scoreBg.getHeight() + 30);


        sb.end();


    }

    private void spawnSnowflakes() {
//        Snowflake snowflake = new Snowflake(MathUtils.random(0, 800 - 64), SantaGame.HEIGHT);
//
//        if (rand.nextInt() % 2 == 0) {
//            snowFlakeimg = new Texture(Gdx.files.internal("fulg1.png"));
//        } else {
//            snowFlakeimg = new Texture(Gdx.files.internal("fulg2.png"));
//        }
        snowflakes.add(new Snowflake(MathUtils.random(0, 800 - 64) * 2, SantaGame.HEIGHT));
        lastSnowFlakeDropTime = TimeUtils.nanoTime();
    }

    private void spawnPresents() {
        presents.add(new Present(santa.getPosition().x + santa.getTexture().getRegionWidth() / 4, santa.getPosition().y));

        lastDropTime = TimeUtils.millis();

        allow = 0;

    }

    private void spawnClouds() {
        if (!SantaGame.getBackgroundName().equalsIgnoreCase("antarctica")) {
            if (rand.nextInt() % 2 == 0) {
                clouds.add(new Cloud(SantaGame.WIDTH, 180));

            } else {
                clouds.add(new Cloud(SantaGame.WIDTH, 335));
            }

            lastCloudTime = TimeUtils.millis();
        }

    }


    @Override
    public void dispose() {
        bg.dispose();
        santa.dispose();
        for (House house : houses) {
            house.dispose();
        }
        for (Present present : presents) {
            present.dispose();
        }

    }

    private void updateGround(float dt) {
        if (groundPos1.x + ground.getWidth() < 0) {
            groundPos1.add(ground.getWidth() * 2, 0);
        }
        if (groundPos2.x + ground.getWidth() < 0) {
            groundPos2.add(ground.getWidth() * 2, 0);
        }
        groundPos1.x -= 200 * dt;
        groundPos2.x -= 200 * dt;

    }

    public void checkHouseHit() {

        Iterator<House> iterHouse = houses.iterator();

        while (iterHouse.hasNext()) {
            House h = iterHouse.next();

            //initialise the bullet iterator each time here
            Iterator<Present> iterPres = presents.iterator();

            while (iterPres.hasNext()) {
                Present p = iterPres.next();

                if (p.getBounds().overlaps(h.getBounds())) {
                    if (h.getBonus() == 1) {
                        if (lastID == h.getID())
                            System.out.println("BOOONUUUSS!!!");
                        else
                            lastID = h.getID();
                        //effectGood.setPosition(p.getPosPresent().x, p.getPosPresent().y);
                        // effectGood.start();
                        ParticleEffectPool.PooledEffect effect = bombEffectPoolGood.obtain();
                        effect.setPosition(p.getPosPresent().x, p.getPosPresent().y);
                        effects.add(effect);
                        score += h.getPoints();
                        //dropSound.play();
                        if (SantaGame.getSoudnOn() == 1) {
                            dropSound.setVolume(dropSound.play(), 0.3f);
                        }

                    } else {
                        //effectBad.setPosition(p.getPosPresent().x, p.getPosPresent().y);
                        //effectBad.start();
                        ParticleEffectPool.PooledEffect effect = bombEffectPoolBad.obtain();
                        effect.setPosition(p.getPosPresent().x, p.getPosPresent().y);
                        effects.add(effect);
                        score += h.getPoints();

                    }

                    iterPres.remove();
                    //iterHouse.remove();

                }
            }
        }

    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (screenX < Gdx.graphics.getWidth() / 2) {
            santa.jump();
        }

        if (screenX > Gdx.graphics.getWidth() / 2) {
            if (allow == 1)
                spawnPresents();
        }
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
