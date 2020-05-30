package com.tanksgame.Screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
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
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tanksgame.Sprites.Enemies.Enemy;
import com.tanksgame.Sprites.Other.Bullet;
import com.tanksgame.Sprites.Player;
import com.tanksgame.Sprites.TileObjects.Tower;
import com.tanksgame.TanksGame;
import com.tanksgame.Tools.Box2DWorldCreator;
import com.tanksgame.Tools.OrthogonalTiledMapRendererWithSprites;
import com.tanksgame.Tools.WorldContactListener;

import java.util.Iterator;

public class PlayScreen extends ScreenAdapter implements InputProcessor {

    private TanksGame game;

    private ScreenManager screenManager;

    public OrthographicCamera camera;
    private Viewport viewport;

    InputMultiplexer multiplexer = new InputMultiplexer();

    //Box2d variables
    private World world;
    private Box2DDebugRenderer b2dr;
    private Box2DWorldCreator creator;

    private Stage stage;

    private SpriteBatch batch;

    private Player player;

    private Window pauseWindow;

    private Texture pauseBackgroundTexture;
    private Texture exitTexture;


    public TiledMap getMap() {
        return map;
    }

    public enum State {
        RUN,
        PAUSE
    }

    private State state;
    private State stateNew;
    private State stateButton;

    private Skin skin;

    private Image resumeButton;
    private Image exitButton;

    //Tiled map variables
    private TmxMapLoader maploader;
    private TiledMap map;
    private OrthogonalTiledMapRendererWithSprites renderer;
    private boolean towersDrawn = false;


    public PlayScreen(TanksGame game) {
        this.game = game;
        screenManager = new ScreenManager(game);
    }

    @Override
    public void show() {

        stage = new Stage(new FitViewport(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2));

        batch = new SpriteBatch();

        world = new World(new Vector2(0, 0), true);

//        camera = new OrthographicCamera(200, 200 * ((float) Gdx.graphics.getHeight() / (float) Gdx.graphics.getWidth()));
        camera = new OrthographicCamera();
//        camera.position.set(0, 0, 0);
        viewport = new FitViewport(TanksGame.WIDTH, TanksGame.HEIGHT, camera);

        b2dr = new Box2DDebugRenderer();

        player = new Player(this);
//        Gdx.input.setInputProcessor(player);


        player.tank.getHull().setAngularDamping(6);
        player.tank.getTower().setAngularDamping(6);

        state = State.RUN;

        skin = new Skin(Gdx.files.internal("uiskin.json"));

        pauseWindow = new Window("Pause", skin);
        pauseWindow.setPosition(Gdx.graphics.getWidth() / 4, Gdx.graphics.getHeight() / 4, Align.center);

        pauseBackgroundTexture = new Texture(Gdx.files.internal("pause_frame_filled.png"));
        Image pauseBackground = new Image(pauseBackgroundTexture);
        pauseBackground.getColor().a = 0.05f;

        Texture resumeTexture = new Texture(Gdx.files.internal("resume_button.png"));
        resumeButton = new Image(resumeTexture);
        resumeButton.setSize(pauseWindow.getWidth() / 2, pauseWindow.getHeight() / 4);
        resumeButton.setPosition(pauseWindow.getX() + resumeButton.getWidth(), (float) (pauseWindow.getY() + 2 * pauseWindow.getHeight() / 3), Align.center);
//        System.out.println(resumeButton.getX() + "; " + resumeButton.getY());
//        System.out.println("Mouse: " + Gdx.input.getX() + "; " + Gdx.input.getY());
        resumeButton.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);
                System.out.println("Resume");
                stateNew = State.RUN;
//                resumeIsPressed = true;
            }
        });


        exitTexture = new Texture(Gdx.files.internal("exit_to_menu_button.png"));
        exitButton = new Image(exitTexture);
        exitButton.setSize(pauseWindow.getWidth() / 2, pauseWindow.getHeight() / 4);
        exitButton.setPosition(pauseWindow.getX() + resumeButton.getWidth(), (float) (pauseWindow.getY() + 0.8 * pauseWindow.getHeight() / 3), Align.center);
        exitButton.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);
                screenManager.setOnMenuScreenFirst();
                dispose();
            }
        });

        stage.addActor(pauseWindow);
        stage.addActor(resumeButton);
        stage.addActor(exitButton);

        multiplexer.addProcessor(stage);
        multiplexer.addProcessor(player); // Your screen
        Gdx.input.setInputProcessor(multiplexer);


        //Load our map and setup our map renderer
        maploader = new TmxMapLoader();
        map = maploader.load("level1.tmx");
        renderer = new OrthogonalTiledMapRendererWithSprites(map, player.tank);
        creator = new Box2DWorldCreator(this);
        world.setContactListener(new WorldContactListener());
    }


    public void update(float dt) {

        player.update(dt);
        renderer.setView(camera);
        world.step(1 / 60f, 8, 3);

        for (Tower tower : creator.getTowers()) {
            tower.update(dt);
            //чтобы не стреляла просто так, подобрать значения
            if (tower.getX() < player.tank.hull.getPosition().x + 50) {
                tower.b2body.setActive(true);
            }
        }

        for (Bullet bullet : player.tank.bullets) {
            bullet.update(dt);
        }

        float startX = camera.viewportWidth / 2;
        float startY = camera.viewportHeight / 2;


        cameraToPlayer(camera, new Vector2(player.tank.hull.getPosition().x, player.tank.hull.getPosition().y));
        setBoundariesForCamera(camera, startX, startY, map.getProperties().get("width", Integer.class) * 32 - startX * 2, map.getProperties().get("height", Integer.class) * 32 - startY * 2);

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

                batch.end();

                b2dr.render(world, camera.combined);
            }
            break;
            case PAUSE: {
                stage.act(delta);
                stage.draw();
//                Gdx.input.setInputProcessor(this);
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

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.ESCAPE:
                stateNew = State.RUN;
                break;
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (screenX >= pauseWindow.getX() + resumeButton.getWidth() && screenX <= resumeButton.getX() + resumeButton.getWidth() &&
                screenY >= resumeButton.getY() && screenX <= resumeButton.getY() + resumeButton.getHeight()) {
            System.out.println("Resume");
            stateNew = State.RUN;
        }
//        System.out.println("Screen x and y = " + screenX + "; " + screenY);
//        System.out.println(pauseWindow.getX() + resumeButton.getWidth());
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
}
