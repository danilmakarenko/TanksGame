package com.tanksgame.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.utils.Align;


public class ExitWindow extends Window {

    private MenuScreen menuScreen;

    private Texture yesButtonTexture;
    private Texture noButtonTexture;

    private Label exitLabel;

    BitmapFont font;


    public ExitWindow(String name, Skin skin, MenuScreen menuScreen, float width, float height) {
        super(name, skin);
        this.menuScreen = menuScreen;

        setSize(width, height);

        yesButtonTexture = changeSizeOfTexture("exitWindow/yes_button.png", (int) (getWidth() / 3), (int) (getHeight() / 5));
        Image yesButton = new Image(yesButtonTexture);
        yesButton.setPosition(getX() + 0.5f * getWidth(), getY() + getHeight() / 3);
        yesButton.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);
                Gdx.app.exit();
            }
        });

        noButtonTexture = changeSizeOfTexture("exitWindow/no_button.png", (int) (int) (getWidth() / 3), (int) (getHeight() / 5));
        Image noButton = new Image(noButtonTexture);
        noButton.setPosition(getX() + 1.6f * getWidth(), getY() + getHeight() / 3);
        final MenuScreen menuScreen1 = menuScreen;
        noButton.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);
                System.out.println("No");
                menuScreen1.isExitWindow = false;
            }
        });

        font = new BitmapFont();

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 32;
        font = generator.generateFont(parameter);
        generator.dispose();

        exitLabel = new Label("Are you sure?", new Label.LabelStyle(font, Color.WHITE));
        exitLabel.setPosition(exitLabel.getWidth()*1.2f, getHeight() * 0.8f, Align.center);

        addActor(exitLabel);
        addActor(yesButton);
        addActor(noButton);

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
