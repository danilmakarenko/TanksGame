package com.tanksgame.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tanksgame.TanksGame;

public class PauseWindow extends Window {

    private PlayScreen playScreen;

    private Table table;

    private Texture resumeTexture;
    private Texture levelsTexture;
    private Texture exitTexture;

    PauseWindow(String name, Skin skin, PlayScreen playScreen, float width, float height) {
        super(name, skin);
        this.playScreen = playScreen;

        setSize(width, height);

        table = new Table();
        table.top();
        table.setFillParent(true);

        table.setSize(getWidth() * 0.8f, getHeight() * 0.8f);
        table.setPosition(0, -50);

        resumeTexture = playScreen.getGame().assetManager.get("pauseWindow/resume_button.png");
        Image resumeButton = new Image(resumeTexture);
        resumeButton.setSize(getWidth() / 7, getHeight() / 2);
        final PlayScreen playScreen1 = playScreen;
        resumeButton.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);
                System.out.println("Resume");
                playScreen1.stateNew = PlayScreen.State.RUN;
            }
        });

        levelsTexture = playScreen.getGame().assetManager.get("pauseWindow/levels_button.png");
        Image levelsButton = new Image(levelsTexture);
        levelsButton.setSize(getWidth() / 7, getHeight() / 2);
        levelsButton.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);
                System.out.println("Levels");
                playScreen1.screenManager.setOnLevelsScreen();
            }
        });

        exitTexture = playScreen.getGame().assetManager.get("pauseWindow/exit_to_menu_button.png");
        Image exitButton = new Image(exitTexture);
        exitButton.setSize(getWidth() / 2, getHeight() / 4);
        exitButton.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);
                System.out.println("Exit");
                playScreen1.screenManager.setOnMenuScreenFirst();
            }
        });

        table.add(resumeButton).row();
        table.add(levelsButton).padTop(table.getHeight() / 10).row();
        table.add(exitButton).padTop(table.getHeight() / 10).row();

        addActor(table);
    }

}
