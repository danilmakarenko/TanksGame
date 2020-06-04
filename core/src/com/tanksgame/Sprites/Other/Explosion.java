package com.tanksgame.Sprites.Other;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tanksgame.Sprites.TileObjects.Tank;

public class Explosion {

    private Animation animation;

    private int animationTimer = 0;

    private Texture explosionATexture;
    private Texture explosionBTexture;
    private Texture explosionCTexture;
    private Texture explosionDTexture;
    private Texture explosionETexture;
    private Texture explosionFTexture;
    private Texture explosionGTexture;
    private Texture explosionHTexture;

    private Sprite explosionASprite;
    private Sprite explosionBSprite;
    private Sprite explosionCSprite;
    private Sprite explosionDSprite;
    private Sprite explosionESprite;
    private Sprite explosionFSprite;
    private Sprite explosionGSprite;
    private Sprite explosionHSprite;

    private float x;
    private float y;

    public boolean remove = false;

    private Tank tank;

    public Explosion(float x, float y, Tank tank) {
        this.x = x;
        this.y = y;
        this.tank = tank;

        explosionATexture = new Texture(Gdx.files.internal("explosion/Explosion_A.png"));
        explosionBTexture = new Texture(Gdx.files.internal("explosion/Explosion_B.png"));
        explosionCTexture = new Texture(Gdx.files.internal("explosion/Explosion_C.png"));
        explosionDTexture = new Texture(Gdx.files.internal("explosion/Explosion_D.png"));
        explosionETexture = new Texture(Gdx.files.internal("explosion/Explosion_E.png"));
        explosionFTexture = new Texture(Gdx.files.internal("explosion/Explosion_F.png"));
        explosionGTexture = new Texture(Gdx.files.internal("explosion/Explosion_G.png"));
        explosionHTexture = new Texture(Gdx.files.internal("explosion/Explosion_H.png"));

        explosionASprite = new Sprite(explosionATexture);
        explosionBSprite = new Sprite(explosionBTexture);
        explosionCSprite = new Sprite(explosionCTexture);
        explosionDSprite = new Sprite(explosionDTexture);
        explosionESprite = new Sprite(explosionETexture);
        explosionFSprite = new Sprite(explosionFTexture);
        explosionGSprite = new Sprite(explosionGTexture);
        explosionHSprite = new Sprite(explosionHTexture);

        if (animation == null)
            animation = new Animation(1/16f, explosionASprite, explosionBSprite, explosionCSprite, explosionDSprite, explosionESprite,
                    explosionFSprite, explosionGSprite, explosionHSprite);
        animation.setPlayMode(Animation.PlayMode.NORMAL);
    }

    public void update(float delta) {
        animationTimer += delta;
        if (animation.isAnimationFinished(animationTimer))
            remove = true;
    }

    public void render(SpriteBatch batch) {
//        batch.draw( animation.getKeyFrame(animationTimer), x, y);
        Sprite ani = (Sprite) animation.getKeyFrame(animationTimer);
        System.out.println(ani.getTexture().toString());
        ani.setPosition(tank.hull.getPosition().x, tank.hull.getPosition().y);
        System.out.println("Coords = " + tank.hull.getPosition().x + "; " + tank.hull.getPosition().y);
        ani.setSize(100, 100);
        ani.draw(batch);
    }


}
