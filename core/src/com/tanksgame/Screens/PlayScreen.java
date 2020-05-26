package com.tanksgame.Screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tanksgame.Sprites.Enemies.Enemy;
import com.tanksgame.Sprites.Other.Bullet;
import com.tanksgame.Sprites.Player;
import com.tanksgame.Sprites.TileObjects.Tank;
import com.tanksgame.TanksGame;
import com.tanksgame.Tools.Box2DWorldCreator;


import com.tanksgame.Tools.OrthogonalTiledMapRendererWithSprites;
import com.tanksgame.Tools.WorldContactListener;

public class PlayScreen extends ScreenAdapter {

    private TanksGame game;

    public OrthographicCamera camera;
    private Viewport viewport;


    //Box2d variables
    private World world;
    private Box2DDebugRenderer b2dr;
    private Box2DWorldCreator creator;

    //Tiled map variables
    private TmxMapLoader maploader;
    private TiledMap map;
    private OrthogonalTiledMapRendererWithSprites renderer;


    public SpriteBatch batch;


    private Texture backgroundTexture;
    private MapLayer playerLayer;

    private Player player;
    private boolean updated = false;
//    private Tank tank;


    public PlayScreen(TanksGame game) {
        this.game = game;
    }

    @Override
    public void show() {

        batch = new SpriteBatch();

        world = new World(new Vector2(0, 0), true);

        camera = new OrthographicCamera(200, 200 * ((float) Gdx.graphics.getHeight() / (float) Gdx.graphics.getWidth()));


        viewport = new FitViewport(TanksGame.WIDTH / TanksGame.PPM, TanksGame.HEIGHT / TanksGame.PPM, camera);

        b2dr = new Box2DDebugRenderer();

        player = new Player(this);
        Gdx.input.setInputProcessor(player);

//        backgroundTexture = new Texture(Gdx.files.internal("stripes.png"));

        player.tank.getHull().setAngularDamping(6);
        player.tank.getTower().setAngularDamping(6);

        maploader = new TmxMapLoader();
        map = maploader.load("level1.tmx");
        renderer = new OrthogonalTiledMapRendererWithSprites(map, player.tank);




//        renderer = new OrthogonalTiledMapRenderer(map);


    }

    public void update(float dt) {

        player.update(dt);


        world.step(1 / 60f, 8, 3);
        camera.position.set(player.tank.getHull().getPosition().x, player.tank.getHull().getPosition().y, 0);
        camera.update();


    }

    @Override
    public void render(float delta) {


        clearScreen();
        update(delta);
        renderer.setView(camera);
        renderer.render();

        camera.update();



        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        player.tank.draw(batch);


        batch.end();

        b2dr.render(world, camera.combined);


    }

    private void clearScreen() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }


    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = width / 2;
        camera.viewportHeight = height / 2;
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
        world.dispose();
        batch.dispose();
        b2dr.dispose();
    }

    public World getWorld() {
        return world;
    }

    public Player getPlayer() {
        return player;
    }

    public OrthogonalTiledMapRendererWithSprites getRenderer() {
        return renderer;
    }
}
