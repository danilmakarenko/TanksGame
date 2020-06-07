package com.tanksgame.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tanksgame.Screens.PlayScreen;
import com.tanksgame.TanksGame;


public class Info {

    private PlayScreen playScreen;

    public Stage stage;

    public Viewport viewport;

    private int health;
    private int level;
    private int baseTime;

    private Label levelLabel;
    private Label baseTimeLabel;
    private Label baseTimeNameLabel;
    private Label levelNameLabel;

    private BitmapFont font;

    private ProgressBar healthBar;


    public Info(SpriteBatch batch, PlayScreen playScreen) {
        this.playScreen = playScreen;

        health = 100;
        level = playScreen.level;
        baseTime = 0;

        viewport = new FitViewport(TanksGame.WIDTH, TanksGame.HEIGHT, new OrthographicCamera());

        stage = new Stage(viewport, batch);

        Pixmap pixmap = new Pixmap(0, 5, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.RED);
        pixmap.fill();
        TextureRegionDrawable drawable = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));
        pixmap.dispose();

        ProgressBar.ProgressBarStyle progressBarStyle = new ProgressBar.ProgressBarStyle();
        progressBarStyle.knob = drawable;

        Pixmap pixmap1 = new Pixmap(100, 5, Pixmap.Format.RGBA8888);
        pixmap1.setColor(Color.GREEN);
        pixmap1.fill();
        drawable = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap1)));
        pixmap1.dispose();

        progressBarStyle.knobBefore = drawable;

        healthBar = new ProgressBar(0.0f, 100f, 1f, false, progressBarStyle);
        healthBar.setValue(health);
        healthBar.setBounds(10, 10, 50, 5);

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 10;
        font = generator.generateFont(parameter);
        generator.dispose();

        levelNameLabel = new Label("  Level", new Label.LabelStyle(font, Color.WHITE));
        baseTimeNameLabel = new Label(" Base time", new Label.LabelStyle(font, Color.WHITE));

        Table tableTop = new Table();
        tableTop.top();
        tableTop.setFillParent(true);

        levelLabel = new Label(String.format("%01d", level), new Label.LabelStyle(font, Color.WHITE));
        baseTimeLabel = new Label(String.format("%02d", baseTime), new Label.LabelStyle(font, Color.WHITE));

        tableTop.add(levelNameLabel).expandX();
        tableTop.add(baseTimeNameLabel).expandX();
        tableTop.row();
        tableTop.add(levelLabel).expandX().padTop(5);
        tableTop.add(baseTimeLabel).expandX().padTop(5);

        Table tableBottom = new Table();
        tableBottom.bottom();
        tableBottom.setFillParent(true);
        tableBottom.add(healthBar).padBottom(10).expandX();

        stage.addActor(tableTop);
        stage.addActor(tableBottom);
    }

    public void update(float delta) {
        health = (int) playScreen.getPlayer().health;
        healthBar.setValue(health);
        baseTimeCheck();
    }

    public void baseTimeCheck() {
        if (playScreen.getPlayer().baseTime >= 1) {
            if (playScreen.level < 5) {
                int newLevel = level + 1;
//                level += 1;
                System.out.println("New level = " + newLevel);
                System.out.println("Level = " + level);

//                playScreen.dispose();
                playScreen.screenManager.setOnPlayScene(newLevel);
            }
        }
        if (playScreen.getPlayer().isOnBase) {
            playScreen.getPlayer().baseTime += 1 / 60f;
            baseTimeLabel.setText((int) playScreen.getPlayer().baseTime);
        } else {
            playScreen.getPlayer().baseTime = 0;
            baseTimeLabel.setText(0);
        }
    }

    public void setLevelInfo(int level) {
        levelLabel.setText(level);
    }

}
