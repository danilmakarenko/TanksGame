package com.tanksgame.Sprites.TileObjects;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.tanksgame.Screens.PlayScreen;
import com.tanksgame.Sprites.Enemies.Enemy;

public class Tower extends Sprite {
    private float stateTime;
    private boolean setToDestroy;
    private boolean destroyed;
    public World world;
    public PlayScreen screen;
    float angle;
    public Body b2body;
    private float x;
    private float y;

    public Tower(PlayScreen screen, float x, float y) {
        this.world = screen.getWorld();
        this.screen = screen;
        this.x = x;
        this.y = y;
        defineEnemy();
        b2body.setActive(true);
        stateTime = 0;
//??
//        setBounds(x, y, 32, 32);
        setToDestroy = false;
        destroyed = false;
    }

    public void update(float dt){
        stateTime += dt;
        if(setToDestroy && !destroyed){
            world.destroyBody(b2body);
            destroyed = true;
            //добавить текстуру уничтоженной башни
//            setRegion(new TextureRegion(screen.getAtlas().findRegion("goomba"), 32, 0, 16, 16));
            stateTime = 0;
        }
        else if(!destroyed) {
            setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2+getHeight()/10);
        }
    }

    private void defineEnemy() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(x-getWidth() / 2, y-getHeight()/2);
        bdef.type = BodyDef.BodyType.StaticBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(25, 35);
        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);
        Sprite tower = new Sprite(new Texture("stoneTower.png"));
        tower.setPosition(b2body.getPosition().x,b2body.getPosition().y);
        tower.setSize(100,100);
//        ???
        setBounds(x,y,100,100);
        setRegion(tower);
//        CircleShape shape = new CircleShape();
//        shape.setRadius(10);
//        fdef.shape = shape;
//        b2body.createFixture(fdef).setUserData(this);
//        Sprite tower = new Sprite(new Texture("stoneTower.png"));
////        tower.setOrigin(b2body.getPosition().x / 2, b2body.getPosition().y / 2);
//        tower.setPosition(b2body.getPosition().x - getWidth() / 2,b2body.getPosition().y-getHeight()/2);
//        tower.setSize(100,100);
////        ???
//        setBounds(x,y,100,100);
//        setRegion(tower);

    }

    public void draw(Batch batch){
        super.draw(batch);

    }

}
