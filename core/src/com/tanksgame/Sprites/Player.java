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

public class Player extends Sprite {

    public World world;
    public Body b2body;
    private PlayScreen screen;
    private Tank tank;
    public Group group;
    public Stage stage;
    private TextureRegion tankHullTexture;
    private TextureRegion tankTowerTexture;
    private TextureRegion flameTexture;
    private Actor tankHullActor;
    private Actor tankTowerActor;


    public Player(PlayScreen playScreen) {
        //initialize default values
        this.screen = playScreen;
        this.world = playScreen.getWorld();
//        tank = new Tank(new Texture("hull.png"), new Texture("tower.png"), new Texture("flame.png"));

        //define mario in Box2d
        definePlayer();

        //set initial values for marios location, width and height. And initial frame as marioStand.
        setBounds(0, 0, 32 / TanksGame.PPM, 32 / TanksGame.PPM);
        createTank(new Texture("hull.png"), new Texture("tower.png"), new Texture("flame.png"));
//        setRegion(new Texture("flame.png"));

//        setRegion(marioStand);
    }


    public void definePlayer() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(32 / TanksGame.PPM, 32 / TanksGame.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);


        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(10/ TanksGame.PPM,20/ TanksGame.PPM);
//        CircleShape shape = new CircleShape();
//        shape.setRadius(12 / TanksGame.PPM);
//        fdef.filter.categoryBits = MarioBros.MARIO_BIT;
//        fdef.filter.maskBits = MarioBros.GROUND_BIT |
//                MarioBros.COIN_BIT |
//                MarioBros.BRICK_BIT |
//                MarioBros.ENEMY_BIT |
//                MarioBros.OBJECT_BIT |
//                MarioBros.ENEMY_HEAD_BIT |
//                MarioBros.ITEM_BIT;

        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);
    }

    public void draw(Batch batch) {
        group.draw(batch,1);
    }

    public void update(float dt) {

        group.setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
    }


    public void createTank(Texture tankHull, Texture tankTower, Texture flame) {


        tankHullTexture = new TextureRegion(tankHull);
        tankTowerTexture = new TextureRegion(tankTower);
        flameTexture = new TextureRegion(flame);

        tankHullActor = new Actor() {
            public void draw(Batch batch, float alpha) {
                batch.draw(tankHullTexture, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(),
                        getScaleX(), getScaleY(), getRotation());
            }
        };
        tankHullActor.setBounds(tankHullActor.getX(), tankHullActor.getY(), tankHullTexture.getRegionWidth()/TanksGame.PPM, tankHullTexture.getRegionHeight()/TanksGame.PPM);




//        final Actor flameActor = new Actor() {
//            public void draw(Batch batch, float alpha) {
//                batch.draw(flameTexture, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(),
//                        getScaleX(), getScaleY(), getRotation());
//            }
//        };
//        flameActor.setBounds(0, 0, flameTexture.getRegionWidth()/TanksGame.PPM, flameTexture.getRegionHeight()/TanksGame.PPM);
//        flameActor.setPosition(tankHullActor.getWidth() / 2, tankHullActor.getHeight() / 2);
//        setRegion(flameTexture);

        tankTowerActor = new Actor() {
            public void draw(Batch batch, float alpha) {
                batch.draw(tankTowerTexture, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(),
                        getScaleX(), getScaleY(), getRotation());
            }
        };
        tankTowerActor.setBounds(0, 0, tankTowerTexture.getRegionWidth()/TanksGame.PPM, tankTowerTexture.getRegionHeight()/TanksGame.PPM);
        tankTowerActor.setPosition(tankHullActor.getWidth()/2-tankTowerActor.getWidth()/2, tankHullActor.getY() / 2);

//        setRegion(tankTowerTexture);

        group = new Group();
        group.addActor(tankHullActor);
        group.addActor(tankTowerActor);
//        group.addActor(flameActor);
    }


    public Texture getOverlayTexture(Texture overLay, Texture textureInput){
        overLay.getTextureData().prepare();
        textureInput.getTextureData().prepare();
        Pixmap inputPix = textureInput.getTextureData().consumePixmap();
        Pixmap overlayPix = overLay.getTextureData().consumePixmap();

        Pixmap outputPix = new Pixmap(inputPix.getWidth(), inputPix.getHeight(), Pixmap.Format.RGBA8888);

        // go over the inputPix and add each byte to the outputPix
        // but only where the same byte is not alpha in the overlayPix

        Texture outputTexture =  new Texture(outputPix, Pixmap.Format.RGBA8888, false);

        inputPix.dispose();
        outputPix.dispose();
        overlayPix.dispose();
        return outputTexture;
    }


}
