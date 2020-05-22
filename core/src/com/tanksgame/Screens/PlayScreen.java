package com.tanksgame.Screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tanksgame.Sprites.Enemies.Enemy;
import com.tanksgame.Sprites.Player;
import com.tanksgame.Sprites.TileObjects.Tank;
import com.tanksgame.TanksGame;
import com.tanksgame.Tools.Box2DWorldCreator;
import com.tanksgame.Tools.WorldContactListener;

public class PlayScreen implements Screen, InputProcessor {
    //Reference to our Game, used to set Screens
    private TanksGame game;
//    private TextureAtlas atlas;

    //basic playscreen variables
    private OrthographicCamera camera;
    private Viewport viewport;

    //Tiled map variables
    private TmxMapLoader maploader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    //Box2d variables
    private World world;
    private Box2DDebugRenderer b2dr;
    private Box2DWorldCreator creator;


    //sprites
    private Player player;

//    private Music music;


    public PlayScreen(TanksGame game) {
        this.game = game;
        camera = new OrthographicCamera();
        viewport = new FitViewport(TanksGame.WIDTH / TanksGame.PPM, TanksGame.HEIGHT / TanksGame.PPM, camera);
        //initially set our gamcam to be centered correctly at the start of of map
        camera.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);
        //Load our map and setup our map renderer
        maploader = new TmxMapLoader();
        map = maploader.load("TestMap.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / TanksGame.PPM);
        //create our Box2D world, setting no gravity in X, -10 gravity in Y, and allow bodies to sleep
        world = new World(new Vector2(0, 0), true);
        //allows for debug lines of our box2d world.
        b2dr = new Box2DDebugRenderer();

        creator = new Box2DWorldCreator(this);

        //create mario in our game world
        player = new Player(this);
//        directionIsRight = true;
//        directionIsUp = true;
        world.setContactListener(new WorldContactListener());
        Gdx.input.setInputProcessor(this);

    }

    @Override
    public void show() {

    }


//    public void handleInput(float dt) {
//        //control our player using immediate impulses
//        if (Gdx.input.isKeyPressed(Input.Keys.UP)&&player.b2body.getLinearVelocity().y<=0.5)
//            player.b2body.applyLinearImpulse(new Vector2(0, 0.1f), player.b2body.getWorldCenter(), true);
//        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.b2body.getLinearVelocity().x <= 0.5) {
//            player.b2body.setAngularVelocity(-0.5f);
//        }
////            player.b2body.applyLinearImpulse(new Vector2(0.1f, 0), player.b2body.getWorldCenter(), true);
//        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.b2body.getLinearVelocity().x >= -0.5) {
////            player.b2body.setAngularVelocity(0.5f);
//            player.b2body.setTransform(player.b2body.getWorldCenter(),5);
//        }
//
////            player.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), player.b2body.getWorldCenter(), true);
//        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)&&player.b2body.getLinearVelocity().y>=-0.5)
//            player.b2body.applyLinearImpulse(new Vector2(0, -0.1f), player.b2body.getWorldCenter(), true);
////            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE))
////                player.fire();
//
//    }

    public void update(float dt) {

        //takes 1 step in the physics simulation(60 times per second)
        world.step(1 / 60f, 6, 2);

        player.update(dt);


        //attach our gamecam to our players.x coordinate
//        if(player.currentState != Mario.State.DEAD) {
//            gamecam.position.x = player.b2body.getPosition().x;
//        }

        //update our gamecam with correct coordinates after changes
        camera.update();
        //tell our renderer to draw only what our camera can see in our game world.
        renderer.setView(camera);

    }

    @Override
    public void render(float delta) {
        //separate our update logic from render
        update(delta);
        //Clear the game screen with Black
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //render our game map
        renderer.render();

        //renderer our Box2DDebugLines
        b2dr.render(world, camera.combined);

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();

        player.group.draw(game.batch,0);
//        player.draw(game.batch);
        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        //dispose of all our opened resources
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();

    }


    public TiledMap getMap() {
        return map;
    }

    public World getWorld() {
        return world;
    }

    @Override
    public boolean keyDown(int keycode) {
        //go forward
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            if (player.b2body.getLinearVelocity().y <= 0.5) {
                player.b2body.applyLinearImpulse(new Vector2(0, 0.1f), player.b2body.getWorldCenter(), true);
            }
        }
        //turn right
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            player.b2body.setAngularVelocity(-0.5f);

        }
        //turn left
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            player.b2body.setAngularVelocity(0.5f);

        }

        //go backward
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN))
            if (player.b2body.getLinearVelocity().y >= -0.5) {
                player.b2body.applyLinearImpulse(new Vector2(0, -0.1f), player.b2body.getWorldCenter(), true);
            }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode== Input.Keys.UP) {
            player.b2body.setLinearVelocity(0,0);
        }
        if (keycode== Input.Keys.DOWN) {
            player.b2body.setLinearVelocity(0,0);

        }
        if (keycode== Input.Keys.LEFT) {
            player.b2body.setAngularVelocity(0);


        }
        if (keycode== Input.Keys.RIGHT) {
            player.b2body.setAngularVelocity(0);


        }
        return true;
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
