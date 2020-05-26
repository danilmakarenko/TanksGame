package com.tanksgame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tanksgame.Screens.MenuScreen;
import com.tanksgame.Screens.PlayScreen;
import com.tanksgame.Screens.ScreenManager;
import com.tanksgame.Sprites.Player;

public class TanksGame extends Game {
    //Virtual Screen size and Box2D Scale(Pixels Per Meter)
    public static final int WIDTH = 400;
    public static final int HEIGHT = 208;
    public static final float PPM = 100;

    private ScreenManager screenManager;


    @Override
    public void create() {
        screenManager = new ScreenManager(this);
        screenManager.setOnMenuScreenFirst();
    }

    @Override
    public void render() {
        super.render();

    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
