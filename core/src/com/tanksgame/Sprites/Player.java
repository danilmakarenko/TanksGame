package com.tanksgame.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.tanksgame.Screens.PlayScreen;
import com.tanksgame.Sprites.TileObjects.Tank;
import com.tanksgame.TanksGame;

import java.awt.geom.RectangularShape;

public class Player extends Sprite implements InputProcessor {

    public World world;
    private PlayScreen playScreen;
    public Tank tank;

    public Player(PlayScreen playScreen) {
        this.playScreen = playScreen;
        this.world = playScreen.getWorld();
        tank = new Tank(world, 0, 0, 32, 32, playScreen);

    }

    public void draw(Batch batch) {
        super.draw(batch);
    }

    public void update(float dt) {
        tank.update();
//        setPosition(tank.hull.getPosition().x - getWidth() / 2, tank.hull.getPosition().y - getHeight() / 2);

    }


    public void render(float delta) {

    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.W:
                tank.setGoingForward(true);
                tank.hull.setLinearDamping(0);
                break;
            case Input.Keys.A:
                tank.leftAcc = -tank.acceleration;
                break;
            case Input.Keys.S:
                tank.setGoingBackward(true);
                tank.hull.setLinearDamping(0);
                break;
            case Input.Keys.D:
                tank.rightAcc = -tank.acceleration;
                break;
            default:
                return false;
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.A:
                tank.leftAcc = 0;
                break;
            case Input.Keys.W:
                tank.setGoingForward(false);
                tank.hull.setLinearVelocity(0, 0);
                tank.hull.setLinearDamping(6);
                break;
            case Input.Keys.S:
                tank.setGoingBackward(false);
                tank.hull.setLinearVelocity(0, 0);
                tank.hull.setLinearDamping(6);
            case Input.Keys.D:
                tank.rightAcc = 0;
                break;
            default:
                return false;
        }
        return false;
    }


    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        tank.shoot();
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
        tank.setMouseX(Gdx.input.getX());
        tank.setMouseY(Gdx.input.getY());

        Vector3 sp3 = playScreen.camera.unproject(new Vector3(screenX, screenY, 0));
        Vector2 sp2 = new Vector2(sp3.x, sp3.y);

        // Take the vector that goes from body origin to mouse in camera space
        Vector2 a = tank.tower.getPosition();
        Vector2 d = sp2.sub(a);


        System.out.println(sp2);
        System.out.println("Tower: " + a);

        // Now you can set the angle;
        tank.tower.setTransform(tank.tower.getPosition(), (float) (d.angleRad() - Math.PI / 2));


        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
