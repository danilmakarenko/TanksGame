package com.tanksgame.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.tanksgame.Sprites.TileObjects.Tank;
import com.tanksgame.TanksGame;

import javax.swing.*;

public class LevelsScreen extends ScreenAdapter {

    private TanksGame game;

    private ScreenManager screenManager;

    private Stage stage;

    private Texture backgroundTexture;
    private Texture firstLevelTexture;
    private Texture secondLevelTexture;
    private Texture thirdLevelTexture;
    private Texture fourthLevelTexture;
    private Texture fifthLevelTexture;

    private float actorWidth = Gdx.graphics.getWidth() / 3;
    private float actorHeight = Gdx.graphics.getHeight() / 2;


    public LevelsScreen(TanksGame game) {
        this.game = game;
        screenManager = new ScreenManager(game);
    }

    public void show() {

        stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        Gdx.input.setInputProcessor(stage);

        backgroundTexture = game.assetManager.get("levelsScreen/background.jpg");
//        backgroundTexture = new Texture(Gdx.files.internal("levelsScreen/background.jpg"));
        Image background = new Image(backgroundTexture);
        background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        firstLevelTexture = game.assetManager.get("levelsScreen/first_level_button.png");
        Image firstLevelButton = new Image(firstLevelTexture);
        firstLevelButton.setSize(actorWidth, actorHeight);
        firstLevelButton.setPosition(0, Gdx.graphics.getHeight() - actorHeight);
        firstLevelButton.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);
                screenManager.setOnPlayScene(1);
                dispose();
            }
        });

        secondLevelTexture = game.assetManager.get("levelsScreen/second_level_button.png");
        Image secondLevelButton = new Image(secondLevelTexture);
        secondLevelButton.setSize(actorWidth, actorHeight);
        secondLevelButton.setPosition(actorWidth, Gdx.graphics.getHeight() - actorHeight);
        secondLevelButton.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);
                screenManager.setOnPlayScene(2);
                dispose();
            }
        });

        thirdLevelTexture = game.assetManager.get("levelsScreen/third_level_button.png");
        Image thirdLevelButton = new Image(thirdLevelTexture);
        thirdLevelButton.setSize(actorWidth, actorHeight);
        thirdLevelButton.setPosition(actorWidth * 2, Gdx.graphics.getHeight() - actorHeight);
        thirdLevelButton.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);
                screenManager.setOnPlayScene(3);
                dispose();
            }
        });

        fourthLevelTexture = game.assetManager.get("levelsScreen/fourth_level_button.png");
        Image fourthLevelButton = new Image(fourthLevelTexture);
        fourthLevelButton.setSize(actorWidth, actorHeight);
        fourthLevelButton.setPosition(0, Gdx.graphics.getHeight() - 2 * actorHeight);
        fourthLevelButton.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);
                screenManager.setOnPlayScene(4);
                dispose();
            }
        });

        fifthLevelTexture = game.assetManager.get("levelsScreen/fifth_level_button.png");
        Image fifthLevelButton = new Image(fifthLevelTexture);
        fifthLevelButton.setSize(actorWidth, actorHeight);
        fifthLevelButton.setPosition(actorWidth, Gdx.graphics.getHeight() - 2 * actorHeight);
        fifthLevelButton.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);
                screenManager.setOnPlayScene(5);
                dispose();
            }
        });

        stage.addActor(background);
        stage.addActor(firstLevelButton);
        stage.addActor(secondLevelButton);
        stage.addActor(thirdLevelButton);
        stage.addActor(fourthLevelButton);
        stage.addActor(fifthLevelButton);

    }

    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    public void render(float delta) {
        stage.act(delta);
        stage.draw();
    }

    public void dispose() {
        stage.dispose();
        firstLevelTexture.dispose();
        secondLevelTexture.dispose();
        thirdLevelTexture.dispose();
        fourthLevelTexture.dispose();
        fifthLevelTexture.dispose();
        game.dispose();
        backgroundTexture.dispose();
        fifthLevelTexture.dispose();
        firstLevelTexture.dispose();
        secondLevelTexture.dispose();
        thirdLevelTexture.dispose();
        fourthLevelTexture.dispose();
    }

    private Texture changeSizeOfTexture(String path, int prefferedWidth, int prefferedHeight) {
        Pixmap originalPixmap = new Pixmap(Gdx.files.internal(path));
        Pixmap formattedPixmap = new Pixmap(prefferedWidth, prefferedHeight, originalPixmap.getFormat());
        formattedPixmap.drawPixmap(originalPixmap,
                0, 0, originalPixmap.getWidth(), originalPixmap.getHeight(),
                0, 0, formattedPixmap.getWidth(), formattedPixmap.getHeight()
        );
        Texture temp = new Texture(formattedPixmap);
        originalPixmap.dispose();
        formattedPixmap.dispose();
        return temp;
    }

}
