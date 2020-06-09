package com.tanksgame.Screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tanksgame.Sprites.Other.Bullet;
import com.tanksgame.Sprites.Player;
import com.tanksgame.Sprites.TileObjects.Bot;
import com.tanksgame.Sprites.TileObjects.Tower;
import com.tanksgame.TanksGame;
import com.tanksgame.Tools.Box2DWorldCreator;
import com.tanksgame.Tools.Info;
import com.tanksgame.Tools.OrthogonalTiledMapRendererWithSprites;
import com.tanksgame.Tools.WorldContactListener;

public class PlayScreen extends ScreenAdapter {

    private TanksGame game;

    public int level;

    public ScreenManager screenManager;

    public OrthographicCamera camera;
    private Viewport viewport;

    InputMultiplexer multiplexer = new InputMultiplexer();

    //Box2d variables
    private World world;
    private Box2DDebugRenderer b2dr;
    private Box2DWorldCreator creator;

    private Stage stage;

    private SpriteBatch batch;

    public Player player;

    private PauseWindow pauseWindow;

    private Info info;

    public TiledMap getMap() {
        return map;
    }

    public enum State {
        RUN,
        PAUSE
    }

    private State state;
    public State stateNew;
    private State stateButton;

    private Skin skin;

    //Tiled map variables
    private TmxMapLoader maploader;
    private TiledMap map;
    private OrthogonalTiledMapRendererWithSprites renderer;
    private boolean towersDrawn = false;


    public PlayScreen(TanksGame game, int l) {
        this.game = game;
        screenManager = new ScreenManager(game);
        this.level = l;
        System.out.println("Level in class = " + level);
    }

    @Override
    public void show() {

        stage = new Stage(new FitViewport(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2));
        batch = new SpriteBatch();

        info = new Info(batch, this);

        world = new World(new Vector2(0, 0), true);

//        camera = new OrthographicCamera(200, 200 * ((float) Gdx.graphics.getHeight() / (float) Gdx.graphics.getWidth()));
        camera = new OrthographicCamera();
//        camera.position.set(0, 0, 0);
        viewport = new FitViewport(TanksGame.WIDTH / TanksGame.PPM * 2f, TanksGame.HEIGHT / TanksGame.PPM * 2, camera);

        b2dr = new Box2DDebugRenderer();

        player = new Player(this);
//        Gdx.input.setInputProcessor(player);


        player.tank.getHull().setAngularDamping(6);
        player.tank.getTower().setAngularDamping(6);

        state = State.RUN;

        skin = new Skin(Gdx.files.internal("uiskin.json"));

        pauseWindow = new PauseWindow("", skin, this, Gdx.graphics.getWidth() / 5, Gdx.graphics.getHeight() / 2.5f);
//        System.out.println("Size = " + pauseWindow.getWidth() + "; " + pauseWindow.getHeight());
        pauseWindow.setPosition(Gdx.graphics.getWidth() / 4, Gdx.graphics.getHeight() / 4, Align.center);
//        System.out.println("Position = " + pauseWindow.getX() + "; " + pauseWindow.getY());


        stage.addActor(pauseWindow);

        multiplexer.addProcessor(stage);
        multiplexer.addProcessor(player); // Your screen
        Gdx.input.setInputProcessor(multiplexer);

        info.setLevelInfo(level);

        //Load our map and setup our map renderer
        maploader = new TmxMapLoader();
        map = maploader.load("level" + level + ".tmx");
        renderer = new OrthogonalTiledMapRendererWithSprites(map, 1 / TanksGame.PPM, player.tank);
        creator = new Box2DWorldCreator(this);
        world.setContactListener(new WorldContactListener(player, screenManager, this));
    }


    public void update(float dt) {

        player.update(dt);
        renderer.setView(camera);
        world.step(1 / 60f, 8, 3);

        for (Tower tower : creator.getTowers()) {
            tower.update(dt);
            //чтобы не стреляла просто так, подобрать значения
            if (tower.getX() < player.tank.hull.getPosition().x + Gdx.graphics.getWidth() / 3f / TanksGame.PPM && tower.isDestroyed == false) {
                tower.b2body.setActive(true);
            } else {
                tower.b2body.setActive(false);
            }
        }

        for (Bot bot : creator.getBots()) {
            bot.update(dt);
            //чтобы не стреляла просто так, подобрать значения
            if (bot.botHull.getPosition().x < player.tank.hull.getPosition().x + Gdx.graphics.getWidth() / 3 / TanksGame.PPM && !bot.isDestroyed) {
                bot.botHull.setActive(true);
                bot.botTower.setActive(true);
            } else {
                bot.botHull.setActive(false);
                bot.botTower.setActive(false);
            }
        }

        for (Bullet bullet : player.tank.bullets) {
            bullet.update(dt);
        }
        float startX = camera.viewportWidth / 2;
        float startY = camera.viewportHeight / 2;

        info.update(dt);

        cameraToPlayer(camera, new Vector2(player.tank.hull.getPosition().x, player.tank.hull.getPosition().y));
        setBoundariesForCamera(camera, startX, startY, map.getProperties().get("width", Integer.class) - startX * 1.22f, map.getProperties().get("height", Integer.class) - startY * 1.9f);

        camera.update();
    }

    @Override
    public void render(float delta) {

        if (player.isEscapePressed()) {
            stateNew = player.getState();
            player.setEscapePressed(false);
        }
        if (stateNew == null)
            stateNew = State.RUN;


        switch (stateNew) {
            case RUN: {
                Gdx.input.setInputProcessor(multiplexer);
                clearScreen();
                update(delta);
                renderer.render();
                batch.setProjectionMatrix(camera.combined);
                batch.begin();


                player.tank.draw(batch);
                for (Tower tower : creator.getTowers()) {
                    tower.draw(batch);
                }

                for (Bot bot : creator.getBots()) {
                    bot.draw(batch);
                }

                batch.end();

                batch.setProjectionMatrix(info.stage.getCamera().combined);
                info.stage.draw();

//                b2dr.render(world, camera.combined);
            }
            break;
            case PAUSE: {

                stage.draw();
                Gdx.input.setInputProcessor(multiplexer);
            }
            break;
        }


    }

    private void clearScreen() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    @Override
    public void resize(int width, int height) {

        viewport.update(width, height);

    }

    @Override
    public void dispose() {
        world.dispose();
        batch.dispose();
        b2dr.dispose();
        map.dispose();
        renderer.dispose();
        stage.dispose();
        skin.dispose();
        screenManager.dispose();
        player.tank.dispose();
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

    private void setBoundariesForCamera(Camera camera, float startX, float startY, float width, float height) {
        Vector3 position = camera.position;
        if (position.x < startX) {
            position.x = startX;
        }
        if (position.y < startY) {
            position.y = startY;
        }
        if (position.x > startX + width) {
            position.x = startX + width;

        }
        if (position.y > startY + height) {
            position.y = startY + height;
        }
        camera.position.set(position);
        camera.update();
    }

    public void cameraToPlayer(Camera camera, Vector2 player) {
        Vector3 position = camera.position;
        position.x = camera.position.x + (player.x - camera.position.x) * .1f;
        position.y = camera.position.y + (player.y - camera.position.y) * .1f;
        camera.position.set(position);
        camera.update();
    }

    public TanksGame getGame() {
        return game;
    }
}