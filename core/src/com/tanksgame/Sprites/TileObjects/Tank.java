package com.tanksgame.Sprites.TileObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.tanksgame.TanksGame;


public class Tank extends Sprite {
    private TanksGame game;
    public TextureRegion tankHullTexture;
    public TextureRegion tankTowerTexture;
    public TextureRegion flameTexture;
    private Group group;
    public Stage stage;

    public Tank(Texture tankHull, Texture tankTower, Texture flame) {
        this.tankHullTexture = new TextureRegion(tankHull);
        this.tankTowerTexture = new TextureRegion(tankTower);
        this.flameTexture = new TextureRegion(flame);
        stage = new Stage();
        final Actor tankHullActor = new Actor(){
            public void draw(Batch batch, float alpha){
                batch.draw(tankHullTexture, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(),
                        getScaleX(), getScaleY(), getRotation());
            }
        };
        tankHullActor.setBounds(tankHullActor.getX(), tankHullActor.getY(), tankHullTexture.getRegionWidth()/TanksGame.PPM, tankHullTexture.getRegionHeight()/TanksGame.PPM);


        final Actor flameActor = new Actor(){
            public void draw(Batch batch, float alpha){
                batch.draw(flameTexture, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(),
                        getScaleX(), getScaleY(), getRotation());
            }
        };
        flameActor.setBounds(0, 0, flameTexture.getRegionWidth()/TanksGame.PPM, flameTexture.getRegionHeight()/TanksGame.PPM);
        flameActor.setPosition(0,0);

        final Actor tankTowerActor = new Actor(){
            public void draw(Batch batch, float alpha){
                batch.draw(tankTowerTexture, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(),
                        getScaleX(), getScaleY(), getRotation());
            }
        };
        flameActor.setBounds(0, 0, tankTowerTexture.getRegionWidth()/TanksGame.PPM, tankTowerTexture.getRegionHeight()/TanksGame.PPM);
        flameActor.setPosition(0,0);


        group = new Group();
        group.addActor(tankHullActor);
        group.addActor(tankTowerActor);
        group.addActor(flameActor);
    }


}
