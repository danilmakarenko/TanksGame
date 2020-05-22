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

public class PlayScreen implements Screen {

    //Reference to our Game, used to set Screens
    private TanksGame game;
//    private TextureAtlas atlas;

    //basic playscreen variables
    private OrthographicCamera camera;
    private Viewport viewport;

    //Tiled map variables
//    private TmxMapLoader maploader;
//    private TiledMap map;
//    private OrthogonalTiledMapRenderer renderer;

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
//        maploader = new TmxMapLoader();
//        map = maploader.load("TestMap.tmx");
//        renderer = new OrthogonalTiledMapRenderer(map, 1 / TanksGame.PPM);
        //create our Box2D world, setting no gravity in X, -10 gravity in Y, and allow bodies to sleep
        world = new World(new Vector2(0, 0), true);
        //allows for debug lines of our box2d world.
        b2dr = new Box2DDebugRenderer();

        creator = new Box2DWorldCreator(this);


        player = new Player(this);
        world.setContactListener(new WorldContactListener());
        Gdx.input.setInputProcessor(player.tank);

    }

    @Override
    public void show() {

    }

    public void update(float dt) {

        //takes 1 step in the physics simulation(60 times per second)
        world.step(1 / 60f, 8, 3);

        player.update(dt);

        //update our gamecam with correct coordinates after changes
        camera.position.set(player.tank.hull.getPosition().x, player.tank.hull.getPosition().y, 0);

        camera.update();
        //tell our renderer to draw only what our camera can see in our game world.
//        renderer.setView(camera);

    }

    @Override
    public void render(float delta) {
        //Clear the game screen with Black
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //separate our update logic from
        update(delta);
        //render our game map
//        renderer.render();

        //renderer our Box2DDebugLines
        b2dr.render(world, camera.combined);

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
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
//        map.dispose();
//        renderer.dispose();
        world.dispose();
        b2dr.dispose();

    }


//    public TiledMap getMap() {
//        return map;
//    }

    public World getWorld() {
        return world;
    }



}
