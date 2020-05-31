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
    public static final int WIDTH = 410;
    public static final int HEIGHT = 208;
    public static final float PPM = 30;

    public static final short NOTHING_BIT = 0;
    public static final short EDGE_BIT = 1;
    public static final short BUILDING_BIT = 2;
    public static final short TREE_BIT = 4;
    public static final short PLAYER_BIT = 8;
    public static final short BULLET_BIT = 16;
    public static final short LAKE_BIT = 32;
    public static final short TOWER_BULLET_BIT = 64;


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
