package com.tanksgame.Sprites.Other;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.tanksgame.Screens.PlayScreen;
import com.tanksgame.Sprites.Player;
import com.tanksgame.Sprites.TileObjects.Tank;

public class Bullet {
    private float bulletSpeed;
    private float angleOfShoot = 0;
    private Body bullet;
    private BodyDef bodyDef;
    private FixtureDef fixtureDef;
    private float x;
    private float y;
    private PlayScreen playScreen;
    private Vector2 position;


    public Bullet(PlayScreen playScreen, float angleOfShoot, Body bullet, BodyDef bodyDef, FixtureDef fixtureDef, float x, float y, float bulletSpeed) {
        this.x = x;
        this.y = y;
        this.angleOfShoot = angleOfShoot;
        this.bullet = bullet;
        this.bodyDef = bodyDef;
        this.fixtureDef = fixtureDef;
        this.bulletSpeed = bulletSpeed;
        this.playScreen = playScreen;
        position = new Vector2();
    }


    public Vector2 getPosition() {
        return bullet.getPosition();
    }


    public void createBullet() {

        bullet = playScreen.getPlayer().tank.hull.getWorld().createBody(bodyDef);
        bullet.createFixture(fixtureDef);


        bullet.setLinearVelocity(bulletSpeed * x, bulletSpeed * y);
    }

    public float getAngleOfShoot() {
        return angleOfShoot;
    }
}
