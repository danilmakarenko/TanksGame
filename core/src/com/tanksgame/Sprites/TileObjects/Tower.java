package com.tanksgame.Sprites.TileObjects;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.tanksgame.Screens.PlayScreen;
import com.tanksgame.Sprites.Enemies.Enemy;
import com.tanksgame.Sprites.Other.Bullet;
import com.tanksgame.TanksGame;
import org.w3c.dom.ls.LSOutput;

import java.util.ArrayList;
import java.util.List;

public class Tower extends Sprite {
    private float stateTime;
    private boolean setToDestroy;
    private boolean destroyed;
    public World world;
    public PlayScreen screen;
    float angle;
    public Body b2body;
    private float x;
    private float y;
    public List<Bullet> bullets;
    private double timeOfShooting;
    private double reloadProgress;
    private boolean isReloaded;
    private BodyDef bulletBodyDef;
    private FixtureDef bulletFixtureDef;
    public float bulletSpeed = 20;
    private boolean isShoot;
    private double shootingTime;
    private int reloadTime = 2;
    private float width = getWidth();
    private float height = getHeight();

    private float angleOfShoot;

    public Tower(PlayScreen screen, float x, float y) {
        this.world = screen.getWorld();
        this.screen = screen;
        this.x = x;
        this.y = y;
        defineEnemy();
        b2body.setActive(false);
        stateTime = 0;
//??
//        setBounds(x, y, 32, 32);
        setToDestroy = false;
        destroyed = false;
        bullets = new ArrayList<>();
    }

    public void update(float dt) {
        stateTime += dt;
        for (Bullet bullet : bullets)
            bullet.update(dt);
//        if (setToDestroy && !destroyed) {
//            world.destroyBody(b2body);
//            destroyed = true;
//            //добавить текстуру уничтоженной башни
////            setRegion(new TextureRegion(screen.getAtlas().findRegion("goomba"), 32, 0, 16, 16));
//            stateTime = 0;
//        } else if (!destroyed) {
//            setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2 + getHeight() / 10);
//        }
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2 + getHeight() / 10);


        if (!isReloaded) {
            double now = System.nanoTime();
            reloadProgress = (now - timeOfShooting) / 1000000000 / screen.getPlayer().getReloadTime();
//            System.out.println(reloadProgress);
        }
//        System.out.println(isReloaded);
        if (isReloaded && b2body.isActive()) {
            shoot();
            isReloaded = false;
            shootingTime = System.nanoTime();
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                }
            }, 0.5f);
        }
        double now = System.nanoTime();
        if ((now - shootingTime) / 1000000000 >= reloadTime)
            isReloaded = true;
    }

    Vector2 tmp = new Vector2();
    Vector2 tmp2 = new Vector2();

    public void shoot() {

        float rotation = (float) ((float) b2body.getTransform().getRotation() + Math.PI / 2);
        float xTower = MathUtils.cos(rotation);
        float yTower = MathUtils.sin(rotation);

        angleOfShoot = b2body.getAngle();

        float x = screen.getPlayer().tank.hull.getPosition().x;
        float y = screen.getPlayer().tank.hull.getPosition().y;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(this.x, this.y);
        // making bullet body
        bulletBodyDef = bodyDef;

        Vector2 sp2 = new Vector2(x, y);
        Vector2 sss = new Vector2(this.x, this.y);
        Vector2 d = sp2.sub(sss);
        b2body.setTransform(b2body.getPosition(), (float) (d.angleRad() - Math.PI / 2));

        CircleShape bulletShape = new CircleShape();
        bulletShape.setRadius(10 / TanksGame.PPM);


        FixtureDef fixDef = new FixtureDef();
        fixDef.shape = bulletShape;
        fixDef.density = (float) Math.pow(2, 15);
        fixDef.restitution = .1f;
        fixDef.friction = .5f;

        bulletFixtureDef = fixDef;

        bulletFixtureDef.filter.categoryBits = TanksGame.TOWER_BULLET_BIT;
        bulletFixtureDef.filter.maskBits = TanksGame.EDGE_BIT |
                TanksGame.PLAYER_BIT |
                TanksGame.TREE_BIT;

        bulletBodyDef.position.set(b2body.getWorldPoint(tmp.set(0, getHeight())));

        bullets.add(new Bullet(screen, angleOfShoot, b2body, bulletBodyDef, bulletFixtureDef, xTower, yTower, bulletSpeed));
        isReloaded = true;
        bullets.get(bullets.size() - 1).createBullet();

        timeOfShooting = System.nanoTime();
//        System.out.println("Tower Reloaded!");
        System.out.println("Fire");
    }


    private void defineEnemy() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(x - getWidth() / 2, y - getHeight() / 2);
        bdef.type = BodyDef.BodyType.StaticBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(25 / TanksGame.PPM, 35 / TanksGame.PPM);
        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);
        Sprite tower = new Sprite(new Texture("stoneTower.png"));
        tower.setPosition(b2body.getPosition().x, b2body.getPosition().y);
        tower.setSize(100 / TanksGame.PPM, 100 / TanksGame.PPM);
        setBounds(x, y, 100 / TanksGame.PPM, 100 / TanksGame.PPM);
        setRegion(tower);
        shape.dispose();
    }

    public void draw(Batch batch) {
        super.draw(batch);
        if (bullets != null && bullets.size() > 0) {
            for (Bullet bulletTmp : bullets) {
                if (!bulletTmp.isDestroyed()) {
                    Sprite bulletSprite = new Sprite(new Texture("bullet.png"));
                    bulletSprite.setRotation(bulletTmp.getAngleOfShoot() * 180 / (float) Math.PI);
                    bulletSprite.setOrigin(100 / 2 / TanksGame.PPM, 100 / 2 / TanksGame.PPM);
                    bulletSprite.setPosition(bulletTmp.getPosition().x - 100 / 2 / TanksGame.PPM, bulletTmp.getPosition().y - 100 / 2 / TanksGame.PPM);
                    bulletSprite.setSize(100 / TanksGame.PPM, 100 / TanksGame.PPM);
                    bulletSprite.draw(batch);
                }
            }
        }
    }
}
