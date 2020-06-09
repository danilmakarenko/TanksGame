package com.tanksgame.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.tanksgame.Sprites.TileObjects.Tank;

import java.util.ArrayList;
import java.util.List;


public class OrthogonalTiledMapRendererWithSprites extends OrthogonalTiledMapRenderer {
    private Sprite sprite;
    private ArrayList<Sprite> sprites;
    private int drawSpritesAfterLayer = 1;
    private Tank tank;

    public OrthogonalTiledMapRendererWithSprites(TiledMap map, float unitScale, Tank tank) {
        super(map, unitScale);
        sprites = new ArrayList<Sprite>();
        this.tank = tank;
    }

    public void addSprite(Sprite sprite) {
        sprites.add(sprite);
    }

    @Override
    public void render() {
        beginRender();
        int currentLayer = 0;
        for (MapLayer layer : map.getLayers()) {
            if (layer.isVisible()) {
                if (layer instanceof TiledMapTileLayer) {
                    renderTileLayer((TiledMapTileLayer) layer);
                    currentLayer++;
                    if (currentLayer == drawSpritesAfterLayer) {
                        for (Sprite sprite : sprites)
                            sprite.draw(this.getBatch());
                    }
                } else {
                    for (MapObject object : layer.getObjects()) {
                        renderObject(object);
                    }
                }
            }
        }
        for (Sprite spriteTmp : sprites) {
            spriteTmp.getTexture().dispose();
        }
        endRender();

        sprites.clear();
        sprites.trimToSize();
    }

}