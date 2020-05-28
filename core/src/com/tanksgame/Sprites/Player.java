package com.tanksgame.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Timer;
import com.tanksgame.Screens.PlayScreen;
import com.tanksgame.Sprites.TileObjects.Tank;
import com.tanksgame.TanksGame;

public class Player extends Sprite implements InputProcessor {

    public World world;
    private PlayScreen playScreen;
    public Tank tank;
    private boolean shoot = false;
    private boolean isReloaded = true;
    public boolean isSound = false;
    public boolean isFlame = false;
    private boolean isEscapePressed = false;

    private double shootingTime;

    private int reloadTime = 2;

    private PlayScreen.State state;

    private ShapeRenderer shapeRenderer;

    public Player(PlayScreen playScreen) {
        this.playScreen = playScreen;
        this.world = playScreen.getWorld();
        tank = new Tank(world, 50, 50, 32, 32, playScreen, this);
        shapeRenderer = new ShapeRenderer();
    }

    public void draw(Batch batch) {
        super.draw(batch);
//        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
//        shapeRenderer.setColor(Color.WHITE);
//        shapeRenderer.arc(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2, 10, 0, (float) 180);
//        shapeRenderer.end();
    }

    public void update(float dt) {
        tank.update(dt);

        double now = System.nanoTime();
        if ((now - shootingTime) / 1000000000 >= reloadTime)
            isReloaded = true;

    }

    public PlayScreen.State getState() {
        return state;
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
            case Input.Keys.ESCAPE:
                if (state == PlayScreen.State.PAUSE)
                    state = PlayScreen.State.RUN;
                else
                    state = PlayScreen.State.PAUSE;
                isEscapePressed = true;
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
        if (isReloaded) {
            tank.shoot();
            isReloaded = false;
            isSound = true;
            isFlame = true;
            shootingTime = System.nanoTime();
            shoot = true;
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    isFlame = false;
                }
            }, 0.5f);
        }
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        shoot = false;
        isSound = false;
//        isFlame = false;
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


//        System.out.println(sp2);
//        System.out.println("Tower: " + a);

        // Now you can set the angle;
        tank.tower.setTransform(tank.tower.getPosition(), (float) (d.angleRad() - Math.PI / 2));


        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    public boolean isShoot() {
        return shoot;
    }

    public void setShoot(boolean shoot) {
        this.shoot = shoot;
    }

    public boolean isEscapePressed() {
        return isEscapePressed;
    }

    public void setEscapePressed(boolean escapePressed) {
        isEscapePressed = escapePressed;
    }

    public int getReloadTime() {
        return reloadTime;
    }
}
