package com.tanksgame.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.tanksgame.TanksGame;

public class ScreenManager {

    private TanksGame game;

    private MenuScreen menuScreen;
    private PlayScreen playScreen;
    private CreditsScreen creditsScreen;

    public boolean backgroundMusicIsPlaying = true;

    public Music backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("music/background_menuScreen.mp3"));


    public ScreenManager(TanksGame game) {
        this.game = game;
        backgroundMusic.setVolume(0.4f);
        backgroundMusic.setLooping(true);
    }

    public void setOnMenuScreenFirst() {
        backgroundMusic.play();

        menuScreen = new MenuScreen(game);
        game.setScreen(menuScreen);
    }

    public void setOnMenuScreen() {
        menuScreen = new MenuScreen(game);
        game.setScreen(menuScreen);
    }

    public void setOnCreditsScene() {
//        creditsScreen = new CreditsScreen(game);
//        menuScreen.dispose();
        game.setScreen(new CreditsScreen(game));
    }

    public void setOnPlayScene() {
//        backgroundMusic = null;
        game.setScreen(new PlayScreen(game));
    }

    public void setOnLevelsScreen() {
        game.setScreen(new LevelsScreen(game));
    }

}
