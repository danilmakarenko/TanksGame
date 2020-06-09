package com.tanksgame.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.tanksgame.Sprites.TileObjects.Tank;
import com.tanksgame.TanksGame;

public class WinScreen extends ScreenAdapter implements InputProcessor {

    private TanksGame game;

    private ScreenManager screenManager;

    private Stage stage;

    private SpriteBatch batch;

    private Texture backgroundTexture;

    private BitmapFont fontWon;
    private BitmapFont font;

    private GlyphLayout glyphLayoutWon;
    private GlyphLayout glyphLayout;

    WinScreen(TanksGame game) {
        this.game = game;
        screenManager = new ScreenManager(game);
    }

    public void show() {

        stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        Gdx.input.setInputProcessor(this);

        batch = new SpriteBatch();

        backgroundTexture = new Texture(Gdx.files.internal("background_win.jpg"));
        Image background = new Image(backgroundTexture);
        background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameterWon = new FreeTypeFontGenerator.FreeTypeFontParameter();
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameterWon.size = 90;
        fontWon = generator.generateFont(parameterWon);
        parameter.size = 48;
        font = generator.generateFont(parameter);
        generator.dispose();

        glyphLayoutWon = new GlyphLayout();
        glyphLayout = new GlyphLayout();

        stage.addActor(background);


    }

    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    public void render(float delta) {
        stage.act(delta);
        stage.draw();

        batch.begin();

        String s = "You won!\n\n";
        String s1 = "Press on the screen to get to the menu";

        glyphLayoutWon.setText(fontWon, s);
        glyphLayout.setText(font, s1);
        fontWon.draw(batch, s, Gdx.graphics.getWidth() / 2 - glyphLayoutWon.width / 2, Gdx.graphics.getHeight() * 0.5f + glyphLayoutWon.height / 2);
        font.draw(batch, s1, Gdx.graphics.getWidth() / 2 - glyphLayout.width / 2, Gdx.graphics.getHeight() * 0.5f - glyphLayoutWon.height/2);

        batch.end();

    }

    public void dispose() {
        stage.dispose();
        backgroundTexture.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
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
        screenManager.setOnMenuScreen();
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
