package com.tanksgame.Tools;

import com.badlogic.gdx.physics.box2d.*;
import com.tanksgame.Screens.PlayScreen;
import com.tanksgame.Screens.ScreenManager;
import com.tanksgame.Sprites.Other.Bullet;
import com.tanksgame.Sprites.Player;
import com.tanksgame.Sprites.TileObjects.Bot;
import com.tanksgame.Sprites.TileObjects.Tower;
import com.tanksgame.TanksGame;

public class WorldContactListener implements ContactListener {

    private Player player;
    private ScreenManager screenManager;
    private PlayScreen playScreen;

    public WorldContactListener(Player player, ScreenManager screenManager, PlayScreen playScreen) {
        this.player = player;
        this.screenManager = screenManager;
        this.playScreen = playScreen;
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        switch (cDef) {
            case TanksGame.BULLET_BIT | TanksGame.TREE_BIT:
            case TanksGame.BULLET_BIT | TanksGame.BUILDING_BIT:
                if (fixA.getFilterData().categoryBits == TanksGame.BULLET_BIT) {
                    ((Bullet) fixA.getUserData()).setToDestroyMethod();

                } else {
                    ((Bullet) fixB.getUserData()).setToDestroyMethod();
                }
                break;
            case TanksGame.BULLET_BIT | TanksGame.EDGE_BIT:
                if (fixA.getFilterData().categoryBits == TanksGame.BULLET_BIT) {
                    Bullet temp = ((Bullet) fixA.getUserData());
                    temp.setToDestroyMethod();
                } else {
                    Bullet temp = ((Bullet) fixB.getUserData());
                    temp.setToDestroyMethod();
                }
                break;
            case TanksGame.BULLET_BIT | TanksGame.TOWER_BIT:
                if (fixA.getFilterData().categoryBits == TanksGame.TOWER_BIT) {
                    Tower temp = ((Tower) fixA.getUserData());
                    temp.hits++;
                    ((Bullet) fixB.getUserData()).setToDestroyMethod();
                } else {
                    Tower temp = ((Tower) fixB.getUserData());
                    temp.hits++;
                    ((Bullet) fixA.getUserData()).setToDestroyMethod();
                }
                break;
            case TanksGame.TOWER_BULLET_BIT | TanksGame.TREE_BIT:
            case TanksGame.TOWER_BULLET_BIT | TanksGame.BUILDING_BIT:
            case TanksGame.TOWER_BULLET_BIT | TanksGame.EDGE_BIT:
            case TanksGame.TOWER_BULLET_BIT | TanksGame.LAKE_BIT:

            case TanksGame.BOT_BULLET_BIT | TanksGame.TREE_BIT:
            case TanksGame.BOT_BULLET_BIT | TanksGame.BUILDING_BIT:
            case TanksGame.BOT_BULLET_BIT | TanksGame.EDGE_BIT:
            case TanksGame.BOT_BULLET_BIT | TanksGame.LAKE_BIT:
                if (fixA.getFilterData().categoryBits == TanksGame.TOWER_BULLET_BIT) {
                    ((Bullet) fixA.getUserData()).setToDestroyMethod();
                } else {
                    ((Bullet) fixB.getUserData()).setToDestroyMethod();
                }
                break;
            case TanksGame.TOWER_BULLET_BIT | TanksGame.PLAYER_BIT:
            case TanksGame.BOT_BULLET_BIT | TanksGame.PLAYER_BIT:
                if (fixA.getFilterData().categoryBits == TanksGame.TOWER_BULLET_BIT) {
                    ((Bullet) fixA.getUserData()).setToDestroyMethod();
                } else {
                    ((Bullet) fixB.getUserData()).setToDestroyMethod();
                }
                if (player.health > 10)
                    player.health -= 10;
                else
                    screenManager.setOnGameOverScreen(playScreen.level);
//                System.out.println("Health = " + player.health);
                break;
            case TanksGame.BASE_BIT | TanksGame.PLAYER_BIT:
                player.isOnBase = true;
                break;
            case TanksGame.PLAYER_BIT | TanksGame.HEART_BIT:
                if (fixA.getFilterData().categoryBits == TanksGame.HEART_BIT) {
                    ((Tower) fixA.getUserData()).setToDestroyHeartBody();
                } else {
                    ((Tower) fixB.getUserData()).setToDestroyHeartBody();
                }
                player.health += 10;
                break;
            case TanksGame.BOT_BIT | TanksGame.TREE_BIT:
            case TanksGame.BOT_BIT | TanksGame.TOWER_BIT:
            case TanksGame.BOT_BIT | TanksGame.EDGE_BIT:
            case TanksGame.BOT_BIT | TanksGame.TOWER_GROUND_BIT:
            case TanksGame.BOT_BIT | TanksGame.BUILDING_BIT:
            case TanksGame.BOT_BIT | TanksGame.LAKE_BIT:
                if (fixA.getFilterData().categoryBits == TanksGame.BOT_BIT) {
                    ((Bot) fixA.getUserData()).reverseVelocity(true,false);
                } else {
                    ((Bot) fixB.getUserData()).reverseVelocity(true,false);
                }
                break;

            //                System.out.println("Health = " + player.health);
        }
    }

    @Override
    public void endContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;
        switch (cDef) {
            case TanksGame.BASE_BIT | TanksGame.PLAYER_BIT:
                player.isOnBase = false;
                break;
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
