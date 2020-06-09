package com.tanksgame.Sprites.TileObjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Timer;
import com.tanksgame.Screens.PlayScreen;
import com.tanksgame.Sprites.Other.Bullet;
import com.tanksgame.TanksGame;

import java.util.ArrayList;
import java.util.List;

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
    public List<Bullet> bullets;
    private double timeOfShooting;
    private double reloadProgress;
    private boolean isReloaded;
    private BodyDef bulletBodyDef;
    private FixtureDef bulletFixtureDef;
    public float bulletSpeed = 20;
    private boolean isShoot;
    private double shootingTime;
    private int reloadTime = 2;
    private float width = getWidth();
    private float height = getHeight();

    private Texture bulletTexture;

    private Texture stoneTower;
    private Texture heartTexture;

    private float angleOfShoot;

    private Sprite tower;
    private Sprite heartSprite;

    public int hits = 0;

    private int newHits;

    private float towerX;
    private float towerY;

    public boolean isDestroyed = false;

    private Animation animation;

    private int animationTimer = 0;

    private Texture explosion_ATexture;
    private Texture explosion_BTexture;
    private Texture explosion_CTexture;
    private Texture explosion_DTexture;
    private Texture explosion_ETexture;
    private Texture explosion_FTexture;
    private Texture explosion_GTexture;
    private Texture explosion_HTexture;

    private Sprite explosion_ASprite;
    private Sprite explosion_BSprite;
    private Sprite explosion_CSprite;
    private Sprite explosion_DSprite;
    private Sprite explosion_ESprite;
    private Sprite explosion_FSprite;
    private Sprite explosion_GSprite;
    private Sprite explosion_HSprite;

    private Body heart;
    private boolean drawHeart;
    private boolean isSetToDestroyHeart;
    private boolean heartIsDestroyed;



    public Tower(PlayScreen screen, float x, float y) {
        this.world = screen.getWorld();
        this.screen = screen;
        this.x = x;
        this.y = y;
        defineEnemy();
        b2body.setActive(false);
        stateTime = 0;
//??
//        setBounds(x, y, 32, 32);
        setToDestroy = false;
        destroyed = false;
        bullets = new ArrayList<>();

        explosion_ATexture = screen.getGame().assetManager.get("explosion/Explosion_A.png");
        explosion_BTexture = screen.getGame().assetManager.get("explosion/Explosion_B.png");
        explosion_CTexture = screen.getGame().assetManager.get("explosion/Explosion_C.png");
        explosion_DTexture = screen.getGame().assetManager.get("explosion/Explosion_D.png");
        explosion_ETexture = screen.getGame().assetManager.get("explosion/Explosion_E.png");
        explosion_FTexture = screen.getGame().assetManager.get("explosion/Explosion_F.png");
        explosion_GTexture = screen.getGame().assetManager.get("explosion/Explosion_G.png");
        explosion_HTexture = screen.getGame().assetManager.get("explosion/Explosion_H.png");

        explosion_ASprite = new Sprite(explosion_ATexture);
        explosion_BSprite = new Sprite(explosion_BTexture);
        explosion_CSprite = new Sprite(explosion_CTexture);
        explosion_DSprite = new Sprite(explosion_DTexture);
        explosion_ESprite = new Sprite(explosion_ETexture);
        explosion_FSprite = new Sprite(explosion_FTexture);
        explosion_GSprite = new Sprite(explosion_GTexture);
        explosion_HSprite = new Sprite(explosion_HTexture);

        animation = new Animation(1f, explosion_ASprite, explosion_BSprite, explosion_CSprite,
                explosion_DSprite, explosion_ESprite, explosion_FSprite, explosion_GSprite, explosion_HSprite);
        animation.setPlayMode(Animation.PlayMode.LOOP);
        heartTexture = screen.getGame().assetManager.get("heart.png");
        drawHeart = false;
        isSetToDestroyHeart = false;
        heartIsDestroyed = false;
    }

    public void update(float dt) {
        stateTime += dt;
        animationTimer += dt;
        for (Bullet bullet : bullets) {
            bullet.update(dt);
        }

        if (hits != newHits && hits <= 3)
            updateStoneTower();

        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2 + getHeight() / 10);


        if (!isReloaded) {
            double now = System.nanoTime();
            reloadProgress = (now - timeOfShooting) / 1000000000 / screen.getPlayer().getReloadTime();
        }
        if (isReloaded && b2body.isActive()) {
            shoot();
            isReloaded = false;
            shootingTime = System.nanoTime();
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                }
            }, 0.5f);
        }
        double now = System.nanoTime();
        if ((now - shootingTime) / 1000000000 >= reloadTime)
            isReloaded = true;

        newHits = hits;

        if (isSetToDestroyHeart&&!heartIsDestroyed) {
            world.destroyBody(heart);
//            heart.setActive(false);
            heartIsDestroyed = true;
        }
        if (b2body.isActive()) {
            float x = screen.getPlayer().tank.hull.getPosition().x;
            float y = screen.getPlayer().tank.hull.getPosition().y;
            Vector2 sp2 = new Vector2(x, y);
            Vector2 sss = new Vector2(this.x, this.y);
            Vector2 d = sp2.sub(sss);
            b2body.setTransform(b2body.getPosition(), (float) (d.angleRad() - Math.PI / 2));
        }


    }

    Vector2 tmp = new Vector2();
    Vector2 tmp2 = new Vector2();

    public void shoot() {
            float rotation = (float) ((float) b2body.getTransform().getRotation() + Math.PI / 2);
            float xTower = MathUtils.cos(rotation);
            float yTower = MathUtils.sin(rotation);

            angleOfShoot = b2body.getAngle();

//            float x = screen.getPlayer().tank.hull.getPosition().x;
//            float y = screen.getPlayer().tank.hull.getPosition().y;
            BodyDef bodyDef = new BodyDef();
            bodyDef.type = BodyDef.BodyType.DynamicBody;
            bodyDef.position.set(this.x, this.y);
            // making bullet body
            bulletBodyDef = bodyDef;

//            Vector2 sp2 = new Vector2(x, y);
//            Vector2 sss = new Vector2(this.x, this.y);
//            Vector2 d = sp2.sub(sss);
//            b2body.setTransform(b2body.getPosition(), (float) (d.angleRad() - Math.PI / 2));


            CircleShape bulletShape = new CircleShape();
            bulletShape.setRadius(10 / TanksGame.PPM);


            FixtureDef fixDef = new FixtureDef();
            fixDef.shape = bulletShape;
            fixDef.density = (float) Math.pow(2, 15);
            fixDef.restitution = .1f;
            fixDef.friction = .5f;

            bulletFixtureDef = fixDef;

            bulletFixtureDef.filter.categoryBits = TanksGame.TOWER_BULLET_BIT;
            bulletFixtureDef.filter.maskBits = TanksGame.EDGE_BIT |
                    TanksGame.PLAYER_BIT |
                    TanksGame.TREE_BIT |
                    TanksGame.BUILDING_BIT;

            bulletBodyDef.position.set(b2body.getWorldPoint(tmp.set(0, getHeight())));

            bullets.add(new Bullet(screen, angleOfShoot, b2body, bulletBodyDef, bulletFixtureDef, xTower, yTower, bulletSpeed));
            isReloaded = true;
            bullets.get(bullets.size() - 1).createBullet();

            timeOfShooting = System.nanoTime();
    }

    private void updateStoneTower() {
        System.out.println("Hits = " + hits);
        world.destroyBody(b2body);

        switch (screen.level) {
            case 1:
                if (hits == 1)
                    stoneTower = screen.getGame().assetManager.get("stoneTower/stoneTowerAfterOneHit.png");
                if (hits == 2)
                    stoneTower = screen.getGame().assetManager.get("stoneTower/stoneTowerAfterSecondHit.png");
                if (hits >= 3)
                    stoneTower = screen.getGame().assetManager.get("stoneTower/stoneTowerAfterThirdHit.png");
                break;
            case 2:
                if (hits == 1)
                    stoneTower = screen.getGame().assetManager.get("stoneTower/stoneWinterTowerAfterOneHit.png");
                if (hits == 2)
                    stoneTower = screen.getGame().assetManager.get("stoneTower/stoneWinterTowerAfterSecondHit.png");
                if (hits >= 3)
                    stoneTower = screen.getGame().assetManager.get("stoneTower/stoneWinterTowerAfterThirdHit.png");
                break;
            case 3:
                if (hits == 1)
                    stoneTower = screen.getGame().assetManager.get("stoneTower/stoneDesertTowerAfterOneHit.png");
                if (hits == 2)
                    stoneTower = screen.getGame().assetManager.get("stoneTower/stoneDesertTowerAfterSecondHit.png");
                if (hits >= 3)
                    stoneTower = screen.getGame().assetManager.get("stoneTower/stoneDesertTowerAfterThirdHit.png");
                break;
            case 4:
                if (hits == 1)
                    stoneTower = screen.getGame().assetManager.get("stoneTower/shipAfterOneHit.png");
                if (hits == 2)
                    stoneTower = screen.getGame().assetManager.get("stoneTower/shipAfterSecondHit.png");
                if (hits >= 3)
                    stoneTower = screen.getGame().assetManager.get("stoneTower/shipAfterThirdHit.png");
                break;
            case 5:
                if (hits == 1)
                    stoneTower = screen.getGame().assetManager.get("stoneTower/stoneCityTowerAfterOneHit.png");
                if (hits == 2)
                    stoneTower = screen.getGame().assetManager.get("stoneTower/stoneCityTowerAfterSecondHit.png");
                if (hits >= 3)
                    stoneTower = screen.getGame().assetManager.get("stoneTower/stoneCityTowerAfterThirdHit.png");
                break;
        }
        BodyDef bdef = new BodyDef();
        bdef.position.set(towerX, towerY);
        bdef.type = BodyDef.BodyType.StaticBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(25 / TanksGame.PPM, 35 / TanksGame.PPM);
        fdef.shape = shape;

        fdef.filter.categoryBits = TanksGame.TOWER_BIT;
        fdef.filter.maskBits = TanksGame.BULLET_BIT;

        b2body.createFixture(fdef).setUserData(this);
        tower = new Sprite(stoneTower);
        tower.setPosition(b2body.getPosition().x, b2body.getPosition().y);
        tower.setSize(100 / TanksGame.PPM, 100 / TanksGame.PPM);
        setBounds(x, y, 100 / TanksGame.PPM, 100 / TanksGame.PPM);
        setRegion(tower);
        if (hits >= 3) {
            isDestroyed = true;
            b2body.setActive(false);
            if (screen.level != 4) {
                createHeart();
            } else screen.player.health += 50;

        }
        shape.dispose();
    }

    private void defineEnemy() {

        bulletTexture = screen.getGame().assetManager.get("bullet.png");
        switch (screen.level) {
            case 1:
                stoneTower = screen.getGame().assetManager.get("stoneTower/stoneTower.png");
                break;
            case 2:
                stoneTower = screen.getGame().assetManager.get("stoneTower/stoneWinterTower.png");
                break;
            case 3:
                stoneTower = screen.getGame().assetManager.get("stoneTower/stoneDesertTower.png");
                break;
            case 4:
                stoneTower = screen.getGame().assetManager.get("stoneTower/ship.png");
                break;
            case 5:
                stoneTower = screen.getGame().assetManager.get("stoneTower/stoneCityTower.png");
                break;
        }
        BodyDef bdef = new BodyDef();
        towerX = x - getWidth() / 2;
        towerY = y - getHeight() / 2;
        bdef.position.set(towerX, towerY);
        bdef.type = BodyDef.BodyType.StaticBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(25 / TanksGame.PPM, 35 / TanksGame.PPM);
        fdef.shape = shape;

        fdef.filter.categoryBits = TanksGame.TOWER_BIT;
        fdef.filter.maskBits = TanksGame.BULLET_BIT;

        b2body.createFixture(fdef).setUserData(this);
        tower = new Sprite(stoneTower);
        tower.setPosition(b2body.getPosition().x, b2body.getPosition().y);
        tower.setSize(100 / TanksGame.PPM, 100 / TanksGame.PPM);
        setBounds(x, y, 100 / TanksGame.PPM, 100 / TanksGame.PPM);
        setRegion(tower);
        shape.dispose();
    }


    public void draw(Batch batch) {
        super.draw(batch);
        if (bullets != null && bullets.size() > 0) {
            for (Bullet bulletTmp : bullets)
                bulletTmp.draw(batch, 100 / TanksGame.PPM, 100 / TanksGame.PPM);
        }
        if (drawHeart&&!heartIsDestroyed) {
            heartSprite.draw(batch);
        }

    }

    public void dispose() {
        screen.dispose();
        world.dispose();
        heartSprite.getTexture().dispose();
        heartTexture.dispose();
    }

    private void createHeart() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(b2body.getPosition().x, b2body.getPosition().y+25/TanksGame.PPM);
        bdef.type = BodyDef.BodyType.StaticBody;
        heart = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(10 / TanksGame.PPM, 10 / TanksGame.PPM);
        fdef.shape = shape;

        fdef.filter.categoryBits = TanksGame.HEART_BIT;
        fdef.filter.maskBits = TanksGame.PLAYER_BIT;

        int heartScaleSize = 20;
        heart.createFixture(fdef).setUserData(this);
        heartSprite = new Sprite(heartTexture);
        heartSprite.setOrigin(heartSprite.getWidth()/heartScaleSize/2/TanksGame.PPM,heartSprite.getHeight()/heartScaleSize/2/TanksGame.PPM);
        heartSprite.setPosition(heart.getPosition().x - heartSprite.getWidth()/heartScaleSize / 2 / TanksGame.PPM, heart.getPosition().y - heartSprite.getHeight() /heartScaleSize / 2 / TanksGame.PPM);
        heartSprite.setSize(heartSprite.getWidth()/heartScaleSize/TanksGame.PPM, heartSprite.getHeight()/heartScaleSize/TanksGame.PPM);
        drawHeart = true;
        shape.dispose();
    }

    public void setToDestroyHeartBody() {
        isSetToDestroyHeart = true;
        drawHeart = false;
    }
}
