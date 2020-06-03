package com.tanksgame.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.tanksgame.TanksGame;

public class MenuScreen extends ScreenAdapter {

    private TanksGame game;

    private ScreenManager screenManager;

    private Stage stage;
    private Stage stageExitWindow;

    private Texture backgroundTexture;
    private Texture playButtonTexture;
    private Texture creditsButtonTexture;
    private Texture exitButtonTexture;

    private ExitWindow exitWindow;

    public boolean isExitWindow = false;

    public MenuScreen(TanksGame game) {
        this.game = game;
    }

    public void show() {
        stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        stageExitWindow = new Stage(new FitViewport((Gdx.graphics.getWidth()), Gdx.graphics.getHeight()));
        Gdx.input.setInputProcessor(stage);

        exitWindow = new ExitWindow("", new Skin(Gdx.files.internal("uiskin.json")), this, Gdx.graphics.getWidth() / 5, Gdx.graphics.getHeight() / 2.5f);
        exitWindow.setMovable(false);
        exitWindow.setSize(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
        exitWindow.setPosition(Gdx.graphics.getWidth() / 4, Gdx.graphics.getHeight() / 4);

        stageExitWindow.addActor(exitWindow);

        screenManager = new ScreenManager(game);

        backgroundTexture = new Texture(Gdx.files.internal("menuScreen/background.png"));
        Image background = new Image(backgroundTexture);
        background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        playButtonTexture = new Texture(Gdx.files.internal("menuScreen/play_button.png"));
        ImageButton play = new ImageButton(new TextureRegionDrawable(new TextureRegion(playButtonTexture)));
//        play.setSize(Gdx.graphics.getWidth() / 3, Gdx.graphics.getHeight() / 6);
        play.setPosition(Gdx.graphics.getWidth() / 2, 3 * Gdx.graphics.getHeight() / 4, Align.center);
        play.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);
                screenManager.setOnLevelsScreen();
                dispose();
            }
        });

        creditsButtonTexture = new Texture(Gdx.files.internal("menuScreen/credits_button.png"));
        ImageButton credits = new ImageButton(new TextureRegionDrawable(new TextureRegion(creditsButtonTexture)));
        credits.setPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2, Align.center);
        credits.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);
                screenManager.setOnCreditsScene();
                dispose();

            }
        });

        exitButtonTexture = new Texture(Gdx.files.internal("menuScreen/exit_button.png"));
        ImageButton exit = new ImageButton(new TextureRegionDrawable(new TextureRegion(exitButtonTexture)));
        exit.setPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 4, Align.center);
        exit.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);
                isExitWindow = true;
            }
        });


        stage.addActor(background);
        stage.addActor(play);
        stage.addActor(credits);
        stage.addActor(exit);

    }

    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    public void render(float delta) {
        if (isExitWindow) {
            stageExitWindow.draw();
            Gdx.input.setInputProcessor(stageExitWindow);
        } else {
            stage.act(delta);
            stage.draw();
            Gdx.input.setInputProcessor(stage);
        }
    }

    public void dispose() {
        stage.dispose();
        backgroundTexture.dispose();
        playButtonTexture.dispose();
        creditsButtonTexture.dispose();
        exitButtonTexture.dispose();
        stageExitWindow.dispose();
//        game.dispose();
    }

}
