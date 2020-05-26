package com.tanksgame.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.tanksgame.TanksGame;

public class CreditsScreen extends ScreenAdapter {

    private TanksGame game;

    private ScreenManager screenManager;

    private Stage stage;

    private SpriteBatch batch;

    BitmapFont font = new BitmapFont();

    private Texture backgroundTexture;
    private Texture returnButtonTexture;

    CreditsScreen(TanksGame game) {
        this.game = game;
        screenManager = new ScreenManager(game);
    }

    public void show() {

        stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        Gdx.input.setInputProcessor(stage);
        batch = new SpriteBatch();

        backgroundTexture = new Texture(Gdx.files.internal("creditsScreen/background_credits.png"));
        Image background = new Image(backgroundTexture);
        background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        Pixmap originalPixmap = new Pixmap(Gdx.files.internal("creditsScreen/return_button.png"));
        Pixmap formattedPixmap = new Pixmap(Gdx.graphics.getWidth() / 10, Gdx.graphics.getHeight() / 7, originalPixmap.getFormat());
        formattedPixmap.drawPixmap(originalPixmap,
                0, 0, originalPixmap.getWidth(), originalPixmap.getHeight(),
                0, 0, formattedPixmap.getWidth(), formattedPixmap.getHeight()
        );

        returnButtonTexture = new Texture(formattedPixmap);
        originalPixmap.dispose();
        formattedPixmap.dispose();
        Image returnButton = new Image(returnButtonTexture);
        returnButton.setSize(returnButtonTexture.getWidth(), returnButtonTexture.getHeight());
        returnButton.setPosition(50, Gdx.graphics.getHeight() - 200);
        returnButton.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);
                screenManager.setOnMenuScreen();
                dispose();
            }
        });

        stage.addActor(background);
        stage.addActor(returnButton);

    }

    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    public void render(float delta) {

        stage.act(delta);
        stage.draw();

        batch.begin();

        font.draw(batch, "Dania Makarenk0\nKostla Korzh", Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);

        batch.end();
    }

    public void dispose() {
        font.dispose();
        backgroundTexture.dispose();
    }

}
