package it.unibo.goldhunt.items.impl;

import it.unibo.goldhunt.items.api.KindOfItem;
import it.unibo.goldhunt.items.api.Revealable;
import it.unibo.goldhunt.player.api.PlayerOperations;

//luca
public class Shield extends Item{

    private final static String ITEM_NAME = "Shield";

    public Revealable trap;

    @Override
    public String getName() {
        return ITEM_NAME;
    }

    @Override
    public PlayerOperations applyEffect(PlayerOperations playerop) {
        if(context == null){
            throw new IllegalStateException("cannot bound item");
        }

        if(trap == null){
            return null;
        }

        final int before = playerop.livesCount();
        final PlayerOperations afterTrap = trap.applyEffect(playerop);
        final int damageTaken = before - afterTrap.livesCount();

        if(damageTaken > 0){
            return afterTrap.addLives(damageTaken);
        }
        return afterTrap;
    }

    @Override
    public String shortString() {
        return "S";
    }

    public void bindTrap(Revealable trap){
        this.trap = trap;
    }

    @Override
    public KindOfItem getItem() {
        return KindOfItem.SHIELD;
    }
}
