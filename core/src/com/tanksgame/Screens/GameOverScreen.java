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
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.tanksgame.TanksGame;
import org.w3c.dom.Text;

public class GameOverScreen extends ScreenAdapter implements InputProcessor {

    private TanksGame game;

    private ScreenManager screenManager;

    private Stage stage;

    private SpriteBatch batch;

    private Texture backgroundTexture;

    private BitmapFont font;
    private BitmapFont font1;

    private GlyphLayout glyphLayout;
    private GlyphLayout glyphLayout1;

    private int level;


    public GameOverScreen(TanksGame game, int level) {
        this.game = game;
        this.level = level;
        screenManager = new ScreenManager(game);
    }

    public void show() {

        stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        Gdx.input.setInputProcessor(this);

        batch = new SpriteBatch();

        backgroundTexture = new Texture(Gdx.files.internal("GameOverScreen/background.jpeg"));
        Image background = new Image(backgroundTexture);
        background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 75;
        font = generator.generateFont(parameter);
        parameter.size = 50;
        font1 = generator.generateFont(parameter);
        generator.dispose();

        glyphLayout = new GlyphLayout();
        glyphLayout1 = new GlyphLayout();

        stage.addActor(background);


    }

    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    public void render(float delta) {
        stage.act(delta);
        stage.draw();

        batch.begin();

        String s = "Try again!\n\n";
        String s1 = "Press on the screen to restart";

        glyphLayout.setText(font, s);
        glyphLayout1.setText(font, s1);
//        font.draw(batch, s, Gdx.graphics.getWidth() / 2 - glyphLayout.width / 2, Gdx.graphics.getHeight() / 2 + glyphLayout.height / 2);
        font1.draw(batch, s1, Gdx.graphics.getWidth() / 1.5f - glyphLayout1.width / 2, Gdx.graphics.getHeight() / 2 - glyphLayout.height);

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
        screenManager.setOnPlayScene(level);
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
