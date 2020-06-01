package com.tanksgame.Tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.CircleMapObject;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.tanksgame.Screens.PlayScreen;
import com.tanksgame.Sprites.Enemies.Enemy;
import com.tanksgame.Sprites.TileObjects.Tank;
import com.tanksgame.Sprites.TileObjects.Tower;
import com.tanksgame.TanksGame;

import java.util.ArrayList;
import java.util.List;

public class Box2DWorldCreator {
    private List<Tower> towers;


    public Box2DWorldCreator(PlayScreen screen) {
        World world = screen.getWorld();
        TiledMap map = screen.getMap();
        //create body and fixture variables
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;


        //create edges bodies/fixtures
        for (MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX()/TanksGame.PPM + rect.getWidth()/TanksGame.PPM / 2), (rect.getY()/TanksGame.PPM + rect.getHeight()/TanksGame.PPM / 2));



            body = world.createBody(bdef);
            shape.setAsBox(rect.getWidth()/TanksGame.PPM / 2, rect.getHeight()/TanksGame.PPM / 2);
            fdef.shape = shape;
            fdef.filter.categoryBits = TanksGame.EDGE_BIT;
            body.createFixture(fdef);

            fdef.filter.categoryBits = TanksGame.EDGE_BIT;
            body.setUserData(this);
        }

        //create buildings bodies/fixtures
        for (MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX()/TanksGame.PPM + rect.getWidth()/TanksGame.PPM / 2), (rect.getY()/TanksGame.PPM + rect.getHeight()/TanksGame.PPM / 2));

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth()/TanksGame.PPM / 2, rect.getHeight()/TanksGame.PPM / 2);
            fdef.filter.categoryBits = TanksGame.BUILDING_BIT;
            body.createFixture(fdef);

            fdef.filter.categoryBits = TanksGame.BUILDING_BIT;
            body.setUserData(this);
        }

        //create trees bodies/fixtures
        for (MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX()/TanksGame.PPM + rect.getWidth()/TanksGame.PPM / 2), (rect.getY()/TanksGame.PPM + rect.getHeight()/TanksGame.PPM / 2));

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth()/TanksGame.PPM / 2, rect.getHeight()/TanksGame.PPM / 2);
            fdef.filter.categoryBits = TanksGame.TREE_BIT;
            fdef.shape = shape;
            body.createFixture(fdef);

            fdef.filter.categoryBits = TanksGame.TREE_BIT;
            body.setUserData(this);
        }

        //create all towers
        towers = new ArrayList<>();
        for (MapObject object : map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            towers.add(new Tower(screen, rect.getX()/TanksGame.PPM + rect.getWidth()/TanksGame.PPM / 2, rect.getY()/TanksGame.PPM + rect.getHeight()/TanksGame.PPM));
        }


        //create lakes bodies/fixtures
        for (MapObject object : map.getLayers().get(7).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX()/TanksGame.PPM + rect.getWidth()/TanksGame.PPM / 2), (rect.getY()/TanksGame.PPM + rect.getHeight()/TanksGame.PPM / 2));


            body = world.createBody(bdef);
            shape.setAsBox(rect.getWidth()/TanksGame.PPM / 2, rect.getHeight()/TanksGame.PPM / 2);
            fdef.shape = shape;
            fdef.filter.categoryBits = TanksGame.LAKE_BIT;
            body.createFixture(fdef);

            fdef.filter.categoryBits = TanksGame.LAKE_BIT;
            body.setUserData(this);
        }
    }


    public List<Tower> getTowers() {
        return towers;
    }
}
