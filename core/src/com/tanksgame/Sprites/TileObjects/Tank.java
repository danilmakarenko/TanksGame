package com.tanksgame.Sprites.TileObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.tanksgame.TanksGame;


public class Tank extends Sprite implements InputProcessor{
    private TanksGame game;
    public TextureRegion tankHullTexture;
    public TextureRegion tankTowerTexture;
    public TextureRegion flameTexture;

    public Body hull, tower;
    private RevoluteJoint joint;
    public float acceleration = 20000, leftAcc, rightAcc;
    private float width,height;


    public Tank(World world, float x, float y, float width, float height, Texture tankHull, Texture tankTower, Texture flame) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x, y);
        this.width = width;
        this.height = height;

        PolygonShape shape = new PolygonShape();
        // ????????????????????????????????????????????????????????????????
        shape.setAsBox(width/2 , height/2);

        FixtureDef fixDef = new FixtureDef();
        fixDef.shape = shape;
        fixDef.density = (float) Math.pow(4, 10);
        fixDef.restitution = .1f;
        fixDef.friction = .5f;

        hull = world.createBody(bodyDef);
        hull.createFixture(fixDef);

        shape.setAsBox(width / 10 , height / 2);

        fixDef.density = 100;

        tower = world.createBody(bodyDef);
        tower.createFixture(fixDef);

        RevoluteJointDef jointDef = new RevoluteJointDef();
        jointDef.bodyA = hull;
        jointDef.bodyB = tower;
        jointDef.localAnchorB.y = -height / 3;

        joint = (RevoluteJoint) world.createJoint(jointDef);

    }

    Vector2 tmp = new Vector2(), tmp2 = new Vector2();


    public void update() {
        float rotation = (float) ((float) hull.getTransform().getRotation() + Math.PI / 2);
        float x = MathUtils.cos(rotation);
        float y = MathUtils.sin(rotation);

//        hull.applyLinearImpulse(tmp.set(leftAcc * x, leftAcc * y), hull.getWorldPoint(tmp2.set(-width / 2, 0)), true);
//        hull.applyLinearImpulse(tmp.set(rightAcc * x, rightAcc * y), hull.getWorldPoint(tmp2.set(width / 2, 0)), true);

        hull.applyForce(tmp.set(leftAcc * x, leftAcc * y), hull.getWorldPoint(tmp2.set(-width/2, 0)), true);
        hull.applyForce(tmp.set(rightAcc * x, rightAcc * y), hull.getWorldPoint(tmp2.set(width / 2, 0)), true);

    }
    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.Q:
                leftAcc = acceleration;
                break;
            case Input.Keys.A:
                leftAcc = -acceleration;
                break;
            case Input.Keys.E:
                rightAcc = acceleration;
                break;
            case Input.Keys.D:
                rightAcc = -acceleration;
                break;
            default:
                return false;
//            case Input.Keys.UP:
//                player.tank.rightAcc = player.tank.acceleration;
//                player.tank.leftAcc = player.tank.acceleration;
//                break;
//            case Input.Keys.DOWN:
//                player.tank.rightAcc = -player.tank.acceleration;
//                player.tank.leftAcc = -player.tank.acceleration;
//                break;
//            case Input.Keys.LEFT:
//                player.tank.leftAcc = player.tank.acceleration;
//                break;
//            case Input.Keys.RIGHT:
//                player.tank.rightAcc = player.tank.acceleration;
//                break;
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.Q:
            case Input.Keys.A:
                leftAcc = 0;
                break;
            case Input.Keys.E:
            case Input.Keys.D:
                rightAcc = 0;
                break;
            default:
                return false;
            //            case Input.Keys.UP:
//                player.tank.rightAcc = 0;
//                player.tank.leftAcc = 0;
//                break;
//            case Input.Keys.DOWN:
//                player.tank.rightAcc = 0;
//                player.tank.leftAcc = 0;
//                break;
//            case Input.Keys.LEFT:
//                player.tank.leftAcc = 0;
//                break;
//            case Input.Keys.RIGHT:
//                player.tank.rightAcc = 0;
//                break;
//        }

        }
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
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
