package com.tanksgame.Sprites.Other;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Timer;
import com.tanksgame.Screens.PlayScreen;
import com.tanksgame.Sprites.Player;
import com.tanksgame.Sprites.TileObjects.Tank;
import com.tanksgame.TanksGame;

public class Bullet extends Sprite {
    private float bulletSpeed;
    private float angleOfShoot = 0;
    private Body bullet;
    private BodyDef bodyDef;
    private FixtureDef fixtureDef;
    private float x;
    private float y;
    private PlayScreen playScreen;
    private boolean setToDestroy = false;
    private boolean destroyed = false;
    private boolean explosion = false;

    private Animation animation;

    private int animationTimer = 0;

    private Texture bulletTexture;

    private Texture explosion_ATexture;
    private Texture explosion_BTexture;
    private Texture explosion_CTexture;
    private Texture explosion_DTexture;
    private Texture explosion_ETexture;
    private Texture explosion_FTexture;
    private Texture explosion_GTexture;
    private Texture explosion_HTexture;

    private Sprite explosion_ASprite;
    private Sprite explosion_BSprite;
    private Sprite explosion_CSprite;
    private Sprite explosion_DSprite;
    private Sprite explosion_ESprite;
    private Sprite explosion_FSprite;
    private Sprite explosion_GSprite;
    private Sprite explosion_HSprite;

    public Bullet(PlayScreen playScreen, float angleOfShoot, Body bullet, BodyDef bodyDef, FixtureDef fixtureDef, float x, float y, float bulletSpeed) {
        this.x = x;
        this.y = y;
        this.angleOfShoot = angleOfShoot;
        this.bullet = bullet;
        this.bodyDef = bodyDef;
        this.fixtureDef = fixtureDef;
        this.bulletSpeed = bulletSpeed;
        this.playScreen = playScreen;
        setToDestroy = false;
        destroyed = false;

        explosion_ATexture = playScreen.getGame().assetManager.get("explosion/Explosion_A.png");
        explosion_BTexture = playScreen.getGame().assetManager.get("explosion/Explosion_B.png");
        explosion_CTexture = playScreen.getGame().assetManager.get("explosion/Explosion_C.png");
        explosion_DTexture = playScreen.getGame().assetManager.get("explosion/Explosion_D.png");
        explosion_ETexture = playScreen.getGame().assetManager.get("explosion/Explosion_E.png");
        explosion_FTexture = playScreen.getGame().assetManager.get("explosion/Explosion_F.png");
        explosion_GTexture = playScreen.getGame().assetManager.get("explosion/Explosion_G.png");
        explosion_HTexture = playScreen.getGame().assetManager.get("explosion/Explosion_H.png");

        explosion_ASprite = new Sprite(explosion_ATexture);
        explosion_BSprite = new Sprite(explosion_BTexture);
        explosion_CSprite = new Sprite(explosion_CTexture);
        explosion_DSprite = new Sprite(explosion_DTexture);
        explosion_ESprite = new Sprite(explosion_ETexture);
        explosion_FSprite = new Sprite(explosion_FTexture);
        explosion_GSprite = new Sprite(explosion_GTexture);
        explosion_HSprite = new Sprite(explosion_HTexture);

        animation = new Animation(0.25f, explosion_ASprite, explosion_BSprite, explosion_CSprite,
                explosion_DSprite, explosion_ESprite, explosion_FSprite, explosion_GSprite, explosion_HSprite);
        animation.setPlayMode(Animation.PlayMode.LOOP);

        bulletTexture = playScreen.getGame().assetManager.get("bullet.png");


    }

    public void draw(Batch batch, float width, float height) {
        float x = getPosition().x - width / 2;
        float y = getPosition().y - height / 2;
        if (!destroyed) {
            Sprite bulletSprite = new Sprite(bulletTexture);
            bulletSprite.setRotation(getAngleOfShoot() * 180 / (float) Math.PI);
            bulletSprite.setOrigin(width / 2, height / 2);
            bulletSprite.setPosition(getPosition().x - width / 2, getPosition().y - height / 2);
            bulletSprite.setSize(width, height);
            bulletSprite.draw(batch);
            System.out.println("!!!!!");
        }
        if (explosion) {
            Sprite ani = (Sprite) animation.getKeyFrame(animationTimer);
            ani.setOrigin(width / 2, height / 2);
            ani.setPosition(x, y);
            ani.setSize(width * 2, height * 2);
            ani.draw(batch);
            explosion = false;
        }
    }

    public Vector2 getPosition() {
        return bullet.getPosition();
    }


    public void update(float dt) {
        animationTimer += dt;
        if (setToDestroy && !destroyed) {
            playScreen.getWorld().destroyBody(bullet);
            destroyed = true;
            explosion = true;
        }
    }

    public void createBullet() {

        bullet = playScreen.getWorld().createBody(bodyDef);
        bullet.createFixture(fixtureDef).setUserData(this);


        bullet.setLinearVelocity(bulletSpeed * x, bulletSpeed * y);
    }

    public float getAngleOfShoot() {
        return angleOfShoot;
    }

    public void setToDestroyMethod() {
        this.setToDestroy = true;
    }

    public boolean isSetToDestroy() {
        return setToDestroy;
    }

    public boolean isDestroyed() {
        return destroyed;
    }

}
