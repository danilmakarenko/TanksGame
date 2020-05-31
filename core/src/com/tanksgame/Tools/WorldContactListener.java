package com.tanksgame.Tools;

import com.badlogic.gdx.physics.box2d.*;
import com.tanksgame.Screens.ScreenManager;
import com.tanksgame.Sprites.Other.Bullet;
import com.tanksgame.Sprites.Player;
import com.tanksgame.TanksGame;

public class WorldContactListener implements ContactListener {

    private Player player;
    private ScreenManager screenManager;

    public WorldContactListener(Player player, ScreenManager screenManager) {
        this.player = player;
        this.screenManager = screenManager;
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        switch (cDef) {
            case TanksGame.BULLET_BIT | TanksGame.TREE_BIT:
            case TanksGame.BULLET_BIT | TanksGame.BUILDING_BIT:
            case TanksGame.BULLET_BIT | TanksGame.EDGE_BIT:
                if (fixA.getFilterData().categoryBits == TanksGame.BULLET_BIT) {
                    ((Bullet) fixA.getUserData()).setToDestroyMethod();
                } else {
                    ((Bullet) fixB.getUserData()).setToDestroyMethod();
                }
                break;
            case TanksGame.TOWER_BULLET_BIT | TanksGame.TREE_BIT:
            case TanksGame.TOWER_BULLET_BIT | TanksGame.BUILDING_BIT:
            case TanksGame.TOWER_BULLET_BIT | TanksGame.EDGE_BIT:
            case TanksGame.TOWER_BULLET_BIT | TanksGame.LAKE_BIT:
                if (fixA.getFilterData().categoryBits == TanksGame.TOWER_BULLET_BIT) {
                    ((Bullet) fixA.getUserData()).setToDestroyMethod();
                } else {
                    ((Bullet) fixB.getUserData()).setToDestroyMethod();
                }
                break;
            case TanksGame.TOWER_BULLET_BIT | TanksGame.PLAYER_BIT:
                if (fixA.getFilterData().categoryBits == TanksGame.TOWER_BULLET_BIT) {
                    ((Bullet) fixA.getUserData()).setToDestroyMethod();
                } else {
                    ((Bullet) fixB.getUserData()).setToDestroyMethod();
                }
                if (player.health > 10)
                    player.health -= 10;
                else
                    screenManager.setOnGameOverScreen();
                System.out.println("Health = " + player.health);
        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
