package com.tanksgame.Sprites.TileObjects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.tanksgame.Screens.PlayScreen;

import com.tanksgame.Sprites.Other.Bullet;
import com.tanksgame.Sprites.Player;
import com.tanksgame.TanksGame;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;


public class Tank extends Sprite {
    private TanksGame game;

    public Body hull, tower, bullet;
    private RevoluteJoint joint;

    private BodyDef bulletBodyDef;
    private FixtureDef bulletFixtureDef;

    public float acceleration = 999999999;
    public float leftAcc;
    public float rightAcc;

    public float tankSpeed = 50;
    public float bulletSpeed = 500000;

    private float forwardX = 0;
    private float forwardY = 0;

    private float width, height;

    private float mouseX;
    private float mouseY;

    private float angle;

    private PlayScreen playScreen;

    private boolean isGoingForward = false;
    private boolean isGoingBackward = false;

    private Fixture hullFixture;
    private Fixture towerFixture;

    private float angleOfShoot = 0;

    public ArrayList<Bullet> bullets;




    public Tank(World world, float x, float y, float width, float height, PlayScreen playScreen) {


        this.playScreen = playScreen;

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x, y);
        this.width = width;
        this.height = height;


        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 3, height / 2.5f);

        FixtureDef fixDef = new FixtureDef();
        fixDef.shape = shape;
        fixDef.density = (float) Math.pow(2, 15);
        fixDef.restitution = .1f;
        fixDef.friction = .5f;


        hull = world.createBody(bodyDef);
        hullFixture = hull.createFixture(fixDef);

        shape.setAsBox(width / 10, height / 3);

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

        bulletFixtureDef = fixDef;
        bullets = new ArrayList<>();
    }

    public void shoot() {
        float rotation = (float) ((float) tower.getTransform().getRotation() + Math.PI / 2);
        float x = MathUtils.cos(rotation);
        float y = MathUtils.sin(rotation);

        bulletBodyDef.position.set(tower.getWorldPoint(tmp.set(0, height)));

        angleOfShoot = tower.getAngle();


        bullets.add(new Bullet(playScreen, angleOfShoot, bullet, bulletBodyDef, bulletFixtureDef, x, y, bulletSpeed));

        bullets.get(bullets.size() - 1).createBullet();

    }

    Vector2 tmp = new Vector2();
    Vector2 tmp2 = new Vector2();

    public void draw(Batch batch) {
        Sprite hullSprite = new Sprite(new Texture("hull.png"));
        hullSprite.setRotation(hull.getAngle() * 180 / (float) Math.PI);
        hullSprite.setOrigin(width / 2, height / 2);
        hullSprite.setPosition(hull.getPosition().x - width / 2, hull.getPosition().y - height / 2);
        hullSprite.setSize(width, height);
        hullSprite.draw(batch);

        Sprite towerSprite = new Sprite(new Texture("tower.png"));
        towerSprite.setRotation(tower.getAngle() * 180 / (float) Math.PI);
        towerSprite.setOrigin(13 / 2f, 16);
        towerSprite.setPosition(tower.getPosition().x - 13 / 2f, tower.getPosition().y - 16);
        towerSprite.setSize(13, 32);
        towerSprite.draw(batch);


        if (bullets != null && bullets.size() > 0) {
            for (Bullet bulletTmp : bullets) {
                Sprite bulletSprite = new Sprite(new Texture("bullet.png"));
                bulletSprite.setRotation(bulletTmp.getAngleOfShoot() * 180 / (float) Math.PI);
                bulletSprite.setOrigin(width/2, height/2);
                bulletSprite.setPosition(bulletTmp.getPosition().x - width/2, bulletTmp.getPosition().y - height/2);
                bulletSprite.setSize(width, height);
                bulletSprite.draw(batch);
            }
        }

        if (playScreen.getPlayer().isShoot()) {
            Sprite flameSprite = new Sprite(new Texture("flame.png"));
            flameSprite.setRotation(tower.getAngle() * 180 / (float) Math.PI+90);
            flameSprite.setOrigin(width/2,height/2);
            flameSprite.setPosition(tower.getWorldPoint(tmp.set(0, height)).x-width/2,tower.getWorldPoint(tmp.set(0, height)).y-height/2);
            flameSprite.setSize(width, height);
            flameSprite.draw(batch);
        }
    }


    public void update() {

        float rotation = (float) ((float) hull.getTransform().getRotation() + Math.PI / 2);
        float x = MathUtils.cos(rotation);
        float y = MathUtils.sin(rotation);


        turnHullCalculation(x, y);

        moveHullCalculation(x, y);



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


    public TanksGame getGame() {
        return game;
    }

    public RevoluteJoint getJoint() {
        return joint;
    }

    public BodyDef getBulletBodyDef() {
        return bulletBodyDef;
    }

    public FixtureDef getBulletFixtureDef() {
        return bulletFixtureDef;
    }

    public float getAcceleration() {
        return acceleration;
    }

    public float getLeftAcc() {
        return leftAcc;
    }

    public float getRightAcc() {
        return rightAcc;
    }

    public float getSpeed() {
        return tankSpeed;
    }

    public float getForwardX() {
        return forwardX;
    }

    public float getForwardY() {
        return forwardY;
    }

    @Override
    public float getWidth() {
        return width;
    }

    @Override
    public float getHeight() {
        return height;
    }

    public float getMouseX() {
        return mouseX;
    }

    public float getMouseY() {
        return mouseY;
    }

    public float getAngle() {
        return angle;
    }

    public PlayScreen getPlayScreen() {
        return playScreen;
    }

    public boolean isGoingForward() {
        return isGoingForward;
    }

    public boolean isGoingBackward() {
        return isGoingBackward;
    }

    public void setGame(TanksGame game) {
        this.game = game;
    }

    public void setHull(Body hull) {
        this.hull = hull;
    }

    public void setTower(Body tower) {
        this.tower = tower;
    }

    public void setJoint(RevoluteJoint joint) {
        this.joint = joint;
    }

    public void setBulletBodyDef(BodyDef bulletBodyDef) {
        this.bulletBodyDef = bulletBodyDef;
    }

    public void setBulletFixtureDef(FixtureDef bulletFixtureDef) {
        this.bulletFixtureDef = bulletFixtureDef;
    }

    public void setAcceleration(float acceleration) {
        this.acceleration = acceleration;
    }

    public void setLeftAcc(float leftAcc) {
        this.leftAcc = leftAcc;
    }

    public void setRightAcc(float rightAcc) {
        this.rightAcc = rightAcc;
    }

    public void setSpeed(float speed) {
        this.tankSpeed = speed;
    }

    public void setForwardX(float forwardX) {
        this.forwardX = forwardX;
    }

    public void setForwardY(float forwardY) {
        this.forwardY = forwardY;
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

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public void setPlayScreen(PlayScreen playScreen) {
        this.playScreen = playScreen;
    }

    public void setGoingForward(boolean goingForward) {
        isGoingForward = goingForward;
    }

    public void setGoingBackward(boolean goingBackward) {
        isGoingBackward = goingBackward;
    }

    public Fixture getHullFixture() {
        return hullFixture;
    }

    public Fixture getTowerFixture() {
        return towerFixture;
    }
}
