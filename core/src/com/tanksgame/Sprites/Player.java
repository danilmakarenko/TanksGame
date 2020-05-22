package com.tanksgame.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.tanksgame.Screens.PlayScreen;
import com.tanksgame.Sprites.TileObjects.Tank;
import com.tanksgame.TanksGame;

import java.awt.geom.RectangularShape;

public class Player extends Sprite{

    public World world;
    private PlayScreen screen;
    public Tank tank;

    public Player(PlayScreen playScreen) {
        //initialize default values
        this.screen = playScreen;
        this.world = playScreen.getWorld();
        setBounds(0, 0, 32 / TanksGame.PPM, 32 / TanksGame.PPM);
        tank = new Tank(world, 0, 0, 20/ TanksGame.PPM, 40/ TanksGame.PPM, new Texture("hull.png"), new Texture("tower.png"), new Texture("flame.png"));

    }

    public void draw(Batch batch) {

    }

    public void update(float dt) {
        tank.update();
        setPosition(tank.hull.getPosition().x - getWidth() / 2, tank.hull.getPosition().y - getHeight() / 2);

    }



}
