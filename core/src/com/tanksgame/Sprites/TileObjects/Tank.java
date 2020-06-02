package com.tanksgame.Sprites.TileObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.tanksgame.Screens.PlayScreen;

import com.tanksgame.Sprites.Other.Bullet;
import com.tanksgame.Sprites.Player;
import com.tanksgame.TanksGame;

import java.util.ArrayList;


public class Tank extends Sprite {
    private TanksGame game;

    public Body hull, tower, bullet;
    private RevoluteJoint joint;

    private BodyDef bulletBodyDef;
    private FixtureDef bulletFixtureDef;

    public float acceleration = 100000;
    public float leftAcc;
    public float rightAcc;

    public float tankSpeed = 6;
    public float bulletSpeed = 15;

    private float forwardX = 0;
    private float forwardY = 0;

    private float width, height;

    private float mouseX;
    private float mouseY;

    private float angle;

    private PlayScreen playScreen;

    private boolean isGoingForward = false;
    private boolean isGoingBackward = false;

    private boolean isReloaded = true;
    private boolean isReady = false;
    private boolean soundPlayed = false;

    private Fixture hullFixture;


    private Fixture towerFixture;

    private float angleOfShoot = 0;

    private double timeOfShooting;

    private float animationTimer = 0f;

    public ArrayList<Bullet> bullets;

    private Texture flameA;
    private Texture flameB;
    private Texture flameC;
    private Texture flameD;
    private Texture flameE;
    private Texture flameF;
    private Texture flameG;
    private Texture flameH;


    private Animation animation;

    private Player player;

    private Sprite flameASprite;
    private Sprite flameBSprite;
    private Sprite flameCSprite;
    private Sprite flameDSprite;
    private Sprite flameESprite;
    private Sprite flameFSprite;
    private Sprite flameGSprite;
    private Sprite flameHSprite;

    private ShapeRenderer shapeRenderer;

    private double reloadProgress;


    public Tank(World world, float x, float y, float width, float height, PlayScreen playScreen, Player player) {

        this.game = playScreen.getGame();

        this.player = player;

        this.playScreen = playScreen;

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x, y);
        this.width = width;
        this.height = height;

        PolygonShape shapeTank = new PolygonShape();
        shapeTank.setAsBox(width / 3, height / 2.5f);

        FixtureDef fixDefHull = new FixtureDef();
        fixDefHull.filter.categoryBits = TanksGame.PLAYER_BIT;
        fixDefHull.filter.maskBits = TanksGame.TREE_BIT |
                TanksGame.LAKE_BIT |
                TanksGame.EDGE_BIT |
                TanksGame.BUILDING_BIT |
                TanksGame.TOWER_BULLET_BIT|
                TanksGame.TOWER_GROUND_BIT;
        fixDefHull.shape = shapeTank;
        fixDefHull.density = (float) Math.pow(2, 15);
        fixDefHull.restitution = .1f;
        fixDefHull.friction = .5f;


        hull = world.createBody(bodyDef);
        hullFixture = hull.createFixture(fixDefHull);


        PolygonShape shape = new PolygonShape();
        FixtureDef fixDef = new FixtureDef();
        fixDef.filter.categoryBits = TanksGame.PLAYER_BIT;
        fixDef.filter.maskBits = TanksGame.NOTHING_BIT;
//        fixDef.filter.maskBits = TanksGame.EDGE_BIT;
//                TanksGame.BUILDING_BIT|
//                TanksGame.TREE_BIT;


        shape.setAsBox(width / 10, height / 3);
        fixDef.shape = shape;
        fixDef.density = (float) Math.pow(2, 15);
        fixDef.restitution = .1f;
        fixDef.friction = .5f;

        tower = world.createBody(bodyDef);

        towerFixture = tower.createFixture(fixDef);


        RevoluteJointDef jointDef = new RevoluteJointDef();
        jointDef.bodyA = hull;
        jointDef.bodyB = tower;
        jointDef.localAnchorB.y = -height / 3;

        // set tower mass to 0
        // потому что мешало
        MassData md = new MassData();
        md.mass = 0;
        tower.setMassData(md);

        joint = (RevoluteJoint) world.createJoint(jointDef);

        // making bullet body
        bulletBodyDef = bodyDef;

        CircleShape bulletShape = new CircleShape();
        bulletShape.setRadius(width / 12);

        fixDef.shape = bulletShape;
        fixDef.density = (float) Math.pow(2, 15);
        fixDef.restitution = .1f;
        fixDef.friction = .5f;

        fixDef.filter.categoryBits = TanksGame.BULLET_BIT;
        fixDef.filter.maskBits = TanksGame.EDGE_BIT |
                TanksGame.TREE_BIT |
                TanksGame.BUILDING_BIT |
                TanksGame.TOWER_BIT;

        bulletFixtureDef = fixDef;
        bullets = new ArrayList<>();


        flameA = game.assetManager.get("flame/Flame_A.png");
        flameB = game.assetManager.get("flame/Flame_B.png");
        flameC = game.assetManager.get("flame/Flame_C.png");
        flameD = game.assetManager.get("flame/Flame_D.png");
        flameE = game.assetManager.get("flame/Flame_E.png");
        flameF = game.assetManager.get("flame/Flame_F.png");
        flameG = game.assetManager.get("flame/Flame_G.png");
        flameH = game.assetManager.get("flame/Flame_H.png");

        flameASprite = new Sprite(flameA);
        flameBSprite = new Sprite(flameB);
        flameCSprite = new Sprite(flameC);
        flameDSprite = new Sprite(flameD);
        flameESprite = new Sprite(flameE);
        flameFSprite = new Sprite(flameF);
        flameGSprite = new Sprite(flameG);
        flameHSprite = new Sprite(flameH);

        animation = new Animation(1 / 16f, flameASprite, flameBSprite, flameCSprite, flameDSprite, flameESprite,
                flameFSprite, flameGSprite, flameHSprite);
        animation.setPlayMode(Animation.PlayMode.LOOP);

        shapeRenderer = new ShapeRenderer();
        shape.dispose();
    }

    public void shoot() {
        float rotation = (float) ((float) tower.getTransform().getRotation() + Math.PI / 2);
        float x = MathUtils.cos(rotation);
        float y = MathUtils.sin(rotation);

        bulletBodyDef.position.set(tower.getWorldPoint(tmp.set(0, height)));

        angleOfShoot = tower.getAngle();


        bullets.add(new Bullet(playScreen, angleOfShoot, bullet, bulletBodyDef, bulletFixtureDef, x, y, bulletSpeed));

        bullets.get(bullets.size() - 1).createBullet();
        isReloaded = false;
        timeOfShooting = System.nanoTime();
        System.out.println("Reloaded!");
        System.out.println(bullets.size());
    }

    Vector2 tmp = new Vector2();
    Vector2 tmp2 = new Vector2();

    public void draw(Batch batch) {
        Sprite hullSprite = new Sprite(new Texture("hulls/hullSea.png"));
        hullSprite.setRotation(hull.getAngle() * 180 / (float) Math.PI);
        hullSprite.setOrigin(width / 2, height / 2);
        hullSprite.setPosition(hull.getPosition().x - width / 2, hull.getPosition().y - height / 2);
        hullSprite.setSize(width, height);
//        hullSprite.draw(batch);
        playScreen.getRenderer().addSprite(hullSprite);


        Sprite towerSprite = new Sprite(new Texture("towers/towerSea.png"));
        towerSprite.setRotation(tower.getAngle() * 180 / (float) Math.PI);
        towerSprite.setOrigin(13 / 2f / TanksGame.PPM, 16 / TanksGame.PPM);
        towerSprite.setPosition(tower.getPosition().x - 13 / 2f / TanksGame.PPM, tower.getPosition().y - 16 / TanksGame.PPM);
        towerSprite.setSize(13 / TanksGame.PPM, 32 / TanksGame.PPM);
//        towerSprite.draw(batch);
        playScreen.getRenderer().addSprite(towerSprite);


        //        System.out.println(Gdx.graphics.getWidth() / 2 + "; " + Gdx.graphics.getHeight() / 2);

//        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
////        if (isReloaded == false) {
//        shapeRenderer.setColor(Color.WHITE);
////        if ((int)(reloadProgress * 10) % 2 == 0)
//
//            shapeRenderer.arc(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2 + 2 * height, 10, 0, (float) reloadProgress * 360);
//        System.out.println((reloadProgress * 10));
//        shapeRenderer.end();
//        }

//        Pixmap pixmap = new Pixmap(40, 40, Pixmap.Format.RGBA8888);
//        pixmap.setColor(Color.BLACK);
//        pixmap.fillCircle(Gdx.graphics.getWidth() / 2, (int) (Gdx.graphics.getHeight() / 2 + 2 * height), 10);
//        Texture texture = new Texture(pixmap);
//
//        batch.draw(texture,Gdx.graphics.getWidth() / 2, (int) (Gdx.graphics.getHeight() / 2 + 2 * height));

        if (bullets != null && bullets.size() > 0) {
            for (Bullet bulletTmp : bullets) {
                if (!bulletTmp.isDestroyed()) {
                    Sprite bulletSprite = new Sprite(new Texture("bullet.png"));
                    bulletSprite.setRotation(bulletTmp.getAngleOfShoot() * 180 / (float) Math.PI);
                    bulletSprite.setOrigin(width / 2, height / 2);
                    bulletSprite.setPosition(bulletTmp.getPosition().x - width / 2, bulletTmp.getPosition().y - height / 2);
                    bulletSprite.setSize(width, height);
                    bulletSprite.draw(batch);
                }
            }
        }


        if (playScreen.getPlayer().isShoot())
            isReady = true;
        if (player.isSound) {
            if (soundPlayed == false) {
                Sound sound = Gdx.audio.newSound(Gdx.files.internal("sounds/shot_sound.mp3"));
                long id = sound.play(0.2f);
                sound.setPitch(id, 1.5f);
                soundPlayed = true;
            }
        } else
            soundPlayed = false;
        if (player.isFlame) {
            Sprite ani = (Sprite) animation.getKeyFrame(animationTimer);
//            System.out.println(ani.getTexture().toString());
//            System.out.println(animationTimer);
            ani.setRotation(tower.getAngle() * 180 / (float) Math.PI);
            ani.setOrigin(width / 2, height / 2);
            ani.setPosition(tower.getWorldPoint(tmp.set(0, height)).x - width / 2, tower.getWorldPoint(tmp.set(0, height)).y - height / 2);
            ani.setSize(width, height);
            ani.draw(batch);
        }
    }


    public void update(float delta) {

        float rotation = (float) ((float) hull.getTransform().getRotation() + Math.PI / 2);
        float x = MathUtils.cos(rotation);
        float y = MathUtils.sin(rotation);

        animationTimer += delta;

        turnHullCalculation(x, y);

        moveHullCalculation(x, y);

        if (!isReloaded) {
            double now = System.nanoTime();
            reloadProgress = (now - timeOfShooting) / 1000000000 / player.getReloadTime();
//            System.out.println(reloadProgress);
        }
    }


    private void turnHullCalculation(float x, float y) {
        hull.applyForce(tmp.set(leftAcc * x, leftAcc * y), hull.getWorldPoint(tmp2.set(-width / 2, 0)), true);
        hull.applyForce(tmp.set(rightAcc * x, rightAcc * y), hull.getWorldPoint(tmp2.set(width / 2, 0)), true);
    }

    private void moveHullCalculation(float x, float y) {
        forwardX = tankSpeed * x;
        forwardY = tankSpeed * y;

        if (isGoingForward)
            hull.setLinearVelocity(forwardX, forwardY);
        if (isGoingBackward)
            hull.setLinearVelocity(-forwardX, -forwardY);
    }


    public Body getHull() {
        return hull;
    }

    public Body getTower() {
        return tower;
    }


    @Override
    public float getWidth() {
        return width;
    }

    @Override
    public float getHeight() {
        return height;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public void setMouseX(float mouseX) {
        this.mouseX = mouseX;
    }

    public void setMouseY(float mouseY) {
        this.mouseY = mouseY;
    }

    public void setGoingForward(boolean goingForward) {
        isGoingForward = goingForward;
    }

    public void setGoingBackward(boolean goingBackward) {
        isGoingBackward = goingBackward;
    }

    public void dispose() {
        flameA.dispose();
        flameASprite.getTexture().dispose();
        flameB.dispose();
        flameBSprite.getTexture().dispose();
        flameC.dispose();
        flameCSprite.getTexture().dispose();
        flameD.dispose();
        flameDSprite.getTexture().dispose();
        flameE.dispose();
        flameESprite.getTexture().dispose();
        flameF.dispose();
        flameFSprite.getTexture().dispose();
        flameH.dispose();
        flameHSprite.getTexture().dispose();
        flameG.dispose();
        flameGSprite.getTexture().dispose();
        shapeRenderer.dispose();
    }
}
