package com.tanksgame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
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


    private ScreenManager screenManager;

    public AssetManager assetManager;


    @Override
    public void create() {
        assetManager = new AssetManager();
//        load();
        assetManager.load("creditsScreen/background.jpg", Texture.class);
        assetManager.load("creditsScreen/return_button.png", Texture.class);

        assetManager.load("exitWindow/background.png", Texture.class);
        assetManager.load("exitWindow/no_button.png", Texture.class);
        assetManager.load("exitWindow/yes_button.png", Texture.class);

        assetManager.load("flame/flame.png", Texture.class);
        assetManager.load("flame/Flame_A.png", Texture.class);
        assetManager.load("flame/Flame_B.png", Texture.class);
        assetManager.load("flame/Flame_C.png", Texture.class);
        assetManager.load("flame/Flame_D.png", Texture.class);
        assetManager.load("flame/Flame_E.png", Texture.class);
        assetManager.load("flame/Flame_F.png", Texture.class);
        assetManager.load("flame/Flame_G.png", Texture.class);
        assetManager.load("flame/Flame_H.png", Texture.class);

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
        assetManager.finishLoading();
//        assets.assetManager.finishLoading();
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
        assetManager.load("creditsScreen/return_button.jpg", Texture.class);

        assetManager.load("exitWindow/background.png", Texture.class);
        assetManager.load("exitWindow/no_button.png", Texture.class);
        assetManager.load("exitWindow/yes_button.png", Texture.class);

        assetManager.load("flame/flame.png", Texture.class);
        assetManager.load("flame/Flame_A.png", Texture.class);
        assetManager.load("flame/Flame_B.png", Texture.class);
        assetManager.load("flame/Flame_C.png", Texture.class);
        assetManager.load("flame/Flame_D.png", Texture.class);
        assetManager.load("flame/Flame_E.png", Texture.class);
        assetManager.load("flame/Flame_F.png", Texture.class);
        assetManager.load("flame/Flame_G.png", Texture.class);
        assetManager.load("flame/Flame_H.png", Texture.class);

        assetManager.load("GameOverScreen/background.jpg", Texture.class);

        assetManager.load("levelsScreen/background.jpg", Texture.class);
        assetManager.load("levelsScreen/first_level_button.png", Texture.class);
        assetManager.load("levelsScreen/second_level_button.png", Texture.class);
        assetManager.load("levelsScreen/third_level_button.png", Texture.class);
        assetManager.load("levelsScreen/fourth_level_button.png", Texture.class);
        assetManager.load("levelsScreen/fifth_level_button.png", Texture.class);

        assetManager.load("menuScreen/background.png", Texture.class);
        assetManager.load("menuScreen/credit_button.png", Texture.class);
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
        assetManager.load("stoneTower/stoneTowerAfterHit.png", Texture.class);
        assetManager.load("stoneTower/stoneTowerAfterSecondHit.png", Texture.class);
        assetManager.load("stoneTower/stoneTowerAfterThirdHit.png", Texture.class);
    }

}
