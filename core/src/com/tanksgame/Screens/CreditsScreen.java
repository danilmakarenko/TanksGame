package com.tanksgame.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
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

    private GlyphLayout glyphLayout;

    CreditsScreen(TanksGame game) {
        this.game = game;
        screenManager = new ScreenManager(game);
    }

    public void show() {

        stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        Gdx.input.setInputProcessor(stage);
        batch = new SpriteBatch();

        backgroundTexture = game.assetManager.get("creditsScreen/background.jpg");
        Image background = new Image(backgroundTexture);
        background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());


        returnButtonTexture = game.assetManager.get("creditsScreen/return_button.png");
        Image returnButton = new Image(returnButtonTexture);
        returnButton.setSize(returnButtonTexture.getWidth(), returnButtonTexture.getHeight());
        returnButton.setPosition(Gdx.graphics.getWidth() / 100, 98 * Gdx.graphics.getHeight() / 100 - returnButton.getHeight());
        returnButton.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);
                screenManager.setOnMenuScreen();
                dispose();
            }
        });

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 32;
        font = generator.generateFont(parameter);
        generator.dispose();

        glyphLayout = new GlyphLayout();

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

        String s = "The game was written by:\n\n\nDania Makarenko\nKostia Korzh\n\n\nAs a summer project for\nNaUKMA Java course";
        String s1 = "May-June 2020";

        glyphLayout.setText(font, s);
        font.draw(batch, s, Gdx.graphics.getWidth() / 2 - glyphLayout.width / 2, Gdx.graphics.getHeight() * 0.90f);
        glyphLayout.setText(font, s1);
        font.draw(batch, s1, Gdx.graphics.getWidth() / 2 - glyphLayout.width / 2, Gdx.graphics.getHeight() * 0.1f);

        batch.end();
    }

    public void dispose() {
        font.dispose();
        backgroundTexture.dispose();
        stage.dispose();
        batch.dispose();
//        returnButtonTexture.dispose();
    }

}
