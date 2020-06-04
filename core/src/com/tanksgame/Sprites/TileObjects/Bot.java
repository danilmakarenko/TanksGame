package com.tanksgame.Sprites.TileObjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.utils.Timer;
import com.tanksgame.Screens.PlayScreen;
import com.tanksgame.Sprites.Other.Bullet;
import com.tanksgame.TanksGame;

import java.util.ArrayList;
import java.util.List;

public class Bot extends Sprite {
    private float stateTime;
    private boolean setToDestroy;
    private boolean destroyed;
    public World world;
    public PlayScreen screen;
    float angle;
    public Body botHull, botTower, botBullet, deadHull;

    private float x;
    private float y;

    private float botX;
    private float botY;

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

    private float botWidth = 32 / TanksGame.PPM;
    private float botHeight = 32 / TanksGame.PPM;

    private Texture bulletTexture;


    private Texture botHullTexture;
    private Texture botTowerTexture;

    private float angleOfShoot;


    public int hits = 0;

    private int newHits;


    public boolean isDestroyed = false;

    private Vector2 velocity;

    public Bot(PlayScreen screen, float x, float y) {
        this.world = screen.getWorld();
        this.screen = screen;
        this.x = x;
        this.y = y;
        defineEnemy();
        stateTime = 0;
//??
//        setBounds(x, y, 32, 32);
        setToDestroy = false;
        destroyed = false;
        bullets = new ArrayList<>();
        velocity = new Vector2();
        velocity.x = 5;
    }

    public void update(float dt) {
        stateTime += dt;
        for (Bullet bullet : bullets) {
            bullet.update(dt);
        }


        if (hits != newHits && hits <= 3)
            updateBot();

        setPosition(botHull.getPosition().x - getWidth() / 2, botHull.getPosition().y - getHeight() / 2 + getHeight() / 10);


        if (!isReloaded) {
            double now = System.nanoTime();
            reloadProgress = (now - timeOfShooting) / 1000000000 / screen.getPlayer().getReloadTime();
        }
        if (isReloaded && botHull.isActive()) {
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

        newHits = hits;

        if (!isDestroyed) {
            float x = screen.getPlayer().tank.hull.getPosition().x;
            float y = screen.getPlayer().tank.hull.getPosition().y;
            Vector2 sp2 = new Vector2(x, y);
            Vector2 sss = new Vector2(this.x, this.y);
            Vector2 d = sp2.sub(sss);
            botTower.setTransform(botTower.getPosition(), (float) (d.angleRad() - Math.PI / 2));
        }

        if (!isDestroyed)
            botHull.setLinearVelocity(velocity);
    }

    Vector2 tmp = new Vector2();
    Vector2 tmp2 = new Vector2();

    public void shoot() {
        float rotation = (float) ((float) botTower.getTransform().getRotation() + Math.PI / 2);
        float xBotTower = MathUtils.cos(rotation);
        float yBotTower = MathUtils.sin(rotation);

        angleOfShoot = botTower.getAngle();

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(this.x, this.y);
        // making bullet body
        bulletBodyDef = bodyDef;

        CircleShape bulletShape = new CircleShape();
        bulletShape.setRadius(5 / TanksGame.PPM);


        FixtureDef fixDef = new FixtureDef();
        fixDef.shape = bulletShape;
        fixDef.density = (float) Math.pow(2, 15);
        fixDef.restitution = .1f;
        fixDef.friction = .5f;

        bulletFixtureDef = fixDef;

        bulletFixtureDef.filter.categoryBits = TanksGame.BOT_BULLET_BIT;
        bulletFixtureDef.filter.maskBits = TanksGame.EDGE_BIT |
                TanksGame.PLAYER_BIT |
                TanksGame.TREE_BIT |
                TanksGame.BUILDING_BIT;

        bulletBodyDef.position.set(botTower.getWorldPoint(tmp.set(0, getHeight())));

        bullets.add(new Bullet(screen, angleOfShoot, botBullet, bulletBodyDef, bulletFixtureDef, xBotTower, yBotTower, bulletSpeed));
        isReloaded = true;
        bullets.get(bullets.size() - 1).createBullet();

        timeOfShooting = System.nanoTime();


    }

    private void updateBot() {
        System.out.println("Hits = " + hits);

        if (hits == 1) {
            botHullTexture = screen.getGame().assetManager.get("hulls/hullRed.png");
            botTowerTexture = screen.getGame().assetManager.get("towers/towerRed.png");
        }

        if (hits == 2) {
            botHullTexture = screen.getGame().assetManager.get("hulls/hullRed.png");
            botTowerTexture = screen.getGame().assetManager.get("towers/towerRed.png");
        }
        if (hits >= 3) {
            botHullTexture = screen.getGame().assetManager.get("hulls/hullRed.png");
            botTowerTexture = screen.getGame().assetManager.get("towers/towerRed.png");
        }
        if (hits >= 3) {
            isDestroyed = true;
            velocity.x = 0;
            botHull.setActive(false);
            botTower.setActive(false);

            BodyDef bodyDef = new BodyDef();
            bodyDef.type = BodyDef.BodyType.StaticBody;
            bodyDef.position.set(botHull.getPosition().x, botHull.getPosition().y);
            PolygonShape shapeTank = new PolygonShape();
            shapeTank.setAsBox(botWidth / 3, botHeight / 3f);
            FixtureDef fixDefHull = new FixtureDef();
            fixDefHull.filter.categoryBits = TanksGame.BOT_BIT;
            fixDefHull.filter.maskBits = TanksGame.PLAYER_BIT;
            fixDefHull.shape = shapeTank;
            deadHull = world.createBody(bodyDef);
            deadHull.createFixture(fixDefHull).setUserData(this);

        }
    }

    private void defineEnemy() {

        bulletTexture = screen.getGame().assetManager.get("bullet.png");
        botX = x - getWidth() / 2;
        botY = y - getHeight() / 2;

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(botX, botY);


        PolygonShape shapeTank = new PolygonShape();
        shapeTank.setAsBox(botWidth / 3, botHeight / 2.5f);

        FixtureDef fixDefHull = new FixtureDef();
        fixDefHull.filter.categoryBits = TanksGame.BOT_BIT;
        fixDefHull.filter.maskBits = TanksGame.TREE_BIT |
                TanksGame.LAKE_BIT |
                TanksGame.EDGE_BIT |
                TanksGame.BUILDING_BIT |
                TanksGame.TOWER_BULLET_BIT |
                TanksGame.TOWER_GROUND_BIT |
                TanksGame.BASE_BIT |
                TanksGame.HEART_BIT |
                TanksGame.PLAYER_BIT |
                TanksGame.BULLET_BIT;
        fixDefHull.shape = shapeTank;
        fixDefHull.density = (float) Math.pow(5, 15);
        fixDefHull.restitution = .1f;
        fixDefHull.friction = 5f;


        botHull = world.createBody(bodyDef);
        botHull.createFixture(fixDefHull).setUserData(this);
        botHull.setTransform(botHull.getPosition(), (float) Math.PI / 2);


        PolygonShape shape = new PolygonShape();
        FixtureDef fixDef = new FixtureDef();
        fixDef.filter.categoryBits = TanksGame.BOT_BIT;
        fixDef.filter.maskBits = TanksGame.NOTHING_BIT;
        //        fixDef.filter.maskBits = TanksGame.EDGE_BIT;
        //                TanksGame.BUILDING_BIT|
        //                TanksGame.TREE_BIT;


        shape.setAsBox(botWidth / 10, botHeight / 3);
        fixDef.shape = shape;
        fixDef.density = (float) Math.pow(2, 15);
        fixDef.restitution = .1f;
        fixDef.friction = .5f;

        botTower = world.createBody(bodyDef);

        botTower.createFixture(fixDef).setUserData(this);


        RevoluteJointDef jointDef = new RevoluteJointDef();
        jointDef.bodyA = botHull;
        jointDef.bodyB = botTower;
        jointDef.localAnchorB.y = -botHeight / 3;

        // set tower mass to 0
        // потому что мешало
        MassData md = new MassData();
        md.mass = 0;
        botTower.setMassData(md);

        world.createJoint(jointDef);
        shape.dispose();
    }


    public void draw(Batch batch) {
        if (bullets != null && bullets.size() > 0) {
            for (Bullet bulletTmp : bullets)
                bulletTmp.draw(batch, 50 / TanksGame.PPM, 50 / TanksGame.PPM);
        }
        Sprite hullSprite = new Sprite(new Texture("hulls/hullRed.png"));
        hullSpriteSettings(hullSprite, botHull, botWidth, botHeight);
        hullSprite.draw(batch);


        Sprite towerSprite = new Sprite(new Texture("towers/towerRed.png"));
        towerSpriteSettings(towerSprite, botTower);
        towerSprite.draw(batch);
    }

    static void towerSpriteSettings(Sprite towerSprite, Body botTower) {
        towerSprite.setRotation(botTower.getAngle() * 180 / (float) Math.PI);
        towerSprite.setOrigin(13 / 2f / TanksGame.PPM, 16 / TanksGame.PPM);
        towerSprite.setPosition(botTower.getPosition().x - 13 / 2f / TanksGame.PPM, botTower.getPosition().y - 16 / TanksGame.PPM);
        towerSprite.setSize(13 / TanksGame.PPM, 32 / TanksGame.PPM);
    }

    static void hullSpriteSettings(Sprite hullSprite, Body botHull, float botWidth, float botHeight) {
        hullSprite.setRotation(botHull.getAngle() * 180 / (float) Math.PI);
        hullSprite.setOrigin(botWidth / 2, botHeight / 2);
        hullSprite.setPosition(botHull.getPosition().x - botWidth / 2, botHull.getPosition().y - botHeight / 2);
        hullSprite.setSize(botWidth, botHeight);
    }

    public void dispose() {
        screen.dispose();
        world.dispose();
    }


    public void reverseVelocity(boolean x, boolean y) {
        if (x)
            velocity.x = -velocity.x;
        if (y)
            velocity.y = -velocity.y;
    }
}
