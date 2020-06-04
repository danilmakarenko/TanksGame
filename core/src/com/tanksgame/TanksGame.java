package com.tanksgame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Logger;
import com.tanksgame.Screens.ScreenManager;

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
    public static final short TOWER_BIT = 128;
    public static final short TOWER_GROUND_BIT = 256;
    public static final short BASE_BIT = 512;
    public static final short HEART_BIT = 1024;
    public static final short BOT_BIT = 2048;
    public static final short BOT_BULLET_BIT = 4096;
    public static final short DEAD_BOT_BIT = 8192;


    private ScreenManager screenManager;

    public AssetManager assetManager;


    @Override
    public void create() {
        assetManager = new AssetManager();
        load();
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

    public void load() {
        assetManager.load("creditsScreen/background.jpg", Texture.class);
        assetManager.load("creditsScreen/return_button.png", Texture.class);

        assetManager.load("exitWindow/background.png", Texture.class);
        assetManager.load("exitWindow/no_button.png", Texture.class);
        assetManager.load("exitWindow/yes_button.png", Texture.class);

        assetManager.load("pauseWindow/background.jpg", Texture.class);
        assetManager.load("pauseWindow/exit_to_menu_button.png", Texture.class);
        assetManager.load("pauseWindow/resume_button.png", Texture.class);
        assetManager.load("pauseWindow/levels_button.png", Texture.class);

        assetManager.load("flame/flame.png", Texture.class);
        assetManager.load("flame/Flame_A.png", Texture.class);
        assetManager.load("flame/Flame_B.png", Texture.class);
        assetManager.load("flame/Flame_C.png", Texture.class);
        assetManager.load("flame/Flame_D.png", Texture.class);
        assetManager.load("flame/Flame_E.png", Texture.class);
        assetManager.load("flame/Flame_F.png", Texture.class);
        assetManager.load("flame/Flame_G.png", Texture.class);
        assetManager.load("flame/Flame_H.png", Texture.class);

        assetManager.load("explosion/Explosion_A.png", Texture.class);
        assetManager.load("explosion/Explosion_B.png", Texture.class);
        assetManager.load("explosion/Explosion_C.png", Texture.class);
        assetManager.load("explosion/Explosion_D.png", Texture.class);
        assetManager.load("explosion/Explosion_E.png", Texture.class);
        assetManager.load("explosion/Explosion_F.png", Texture.class);
        assetManager.load("explosion/Explosion_G.png", Texture.class);
        assetManager.load("explosion/Explosion_H.png", Texture.class);

        assetManager.load("GameOverScreen/background.jpg", Texture.class);

        assetManager.load("levelsScreen/background.jpg", Texture.class);
        assetManager.load("levelsScreen/first_level_button.png", Texture.class);
        assetManager.load("levelsScreen/second_level_button.png", Texture.class);
        assetManager.load("levelsScreen/third_level_button.png", Texture.class);
        assetManager.load("levelsScreen/fourth_level_button.png", Texture.class);
        assetManager.load("levelsScreen/fifth_level_button.png", Texture.class);

        assetManager.load("menuScreen/background.png", Texture.class);
        assetManager.load("menuScreen/credits_button.png", Texture.class);
        assetManager.load("menuScreen/exit_button.png", Texture.class);
        assetManager.load("menuScreen/play_button.png", Texture.class);

        assetManager.load("hulls/hullBlue.png", Texture.class);
        assetManager.load("hulls/hullBrown.png", Texture.class);
        assetManager.load("hulls/hullGreen.png", Texture.class);
        assetManager.load("hulls/hullRed.png", Texture.class);
        assetManager.load("hulls/hullSea.png", Texture.class);

        assetManager.load("towers/towerBlue.png", Texture.class);
        assetManager.load("towers/towerBrown.png", Texture.class);
        assetManager.load("towers/towerGreen.png", Texture.class);
        assetManager.load("towers/towerRed.png", Texture.class);
        assetManager.load("towers/towerSea.png", Texture.class);

        assetManager.load("stoneTower/stoneTower.png", Texture.class);
        assetManager.load("stoneTower/stoneTowerAfterOneHit.png", Texture.class);
        assetManager.load("stoneTower/stoneTowerAfterSecondHit.png", Texture.class);
        assetManager.load("stoneTower/stoneTowerAfterThirdHit.png", Texture.class);

        assetManager.load("stoneTower/stoneWinterTower.png", Texture.class);
        assetManager.load("stoneTower/stoneWinterTowerAfterOneHit.png", Texture.class);
        assetManager.load("stoneTower/stoneWinterTowerAfterSecondHit.png", Texture.class);
        assetManager.load("stoneTower/stoneWinterTowerAfterThirdHit.png", Texture.class);

        assetManager.load("stoneTower/stoneDesertTower.png", Texture.class);
        assetManager.load("stoneTower/stoneDesertTowerAfterOneHit.png", Texture.class);
        assetManager.load("stoneTower/stoneDesertTowerAfterSecondHit.png", Texture.class);
        assetManager.load("stoneTower/stoneDesertTowerAfterThirdHit.png", Texture.class);

        assetManager.load("stoneTower/ship.png", Texture.class);
        assetManager.load("stoneTower/shipAfterOneHit.png", Texture.class);
        assetManager.load("stoneTower/shipAfterSecondHit.png", Texture.class);
        assetManager.load("stoneTower/shipAfterThirdHit.png", Texture.class);

        assetManager.load("stoneTower/stoneCityTower.png", Texture.class);
        assetManager.load("stoneTower/stoneCityTowerAfterOneHit.png", Texture.class);
        assetManager.load("stoneTower/stoneCityTowerAfterSecondHit.png", Texture.class);
        assetManager.load("stoneTower/stoneCityTowerAfterThirdHit.png", Texture.class);

        assetManager.load("bullet.png", Texture.class);
        assetManager.load("heart.png", Texture.class);


        assetManager.finishLoading();
    }

}
