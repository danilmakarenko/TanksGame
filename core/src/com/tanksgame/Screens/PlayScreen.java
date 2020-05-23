package com.tanksgame.Screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
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

public class PlayScreen extends ScreenAdapter {

    private TanksGame game;

    public OrthographicCamera camera;
    private Viewport viewport;


    //Box2d variables
    private World world;
    private Box2DDebugRenderer b2dr;
    private Box2DWorldCreator creator;


    private SpriteBatch batch;

    private Texture backgroundTexture;

    private Player player;

//    private Tank tank;


    public PlayScreen(TanksGame game) {
        this.game = game;
    }

    @Override
    public void show() {

        batch = new SpriteBatch();

        world = new World(new Vector2(0, 0), true);

        camera = new OrthographicCamera(200, 200 * ((float) Gdx.graphics.getHeight() / (float) Gdx.graphics.getWidth()));
        camera.position.set(0, 0, 0);
        viewport = new FitViewport(TanksGame.WIDTH / TanksGame.PPM, TanksGame.HEIGHT / TanksGame.PPM, camera);

        b2dr = new Box2DDebugRenderer();

        player = new Player(this);
        Gdx.input.setInputProcessor(player);

        backgroundTexture = new Texture(Gdx.files.internal("stripes.png"));

        player.tank.getHull().setAngularDamping(6);
        player.tank.getTower().setAngularDamping(6);


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

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(backgroundTexture, 0, 0);
        batch.end();

        b2dr.render(world, camera.combined);


    }

    private void clearScreen() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }


    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = width / 25;
        camera.viewportHeight = height / 25;
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


}
