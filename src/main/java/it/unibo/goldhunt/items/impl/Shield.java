package it.unibo.goldhunt.items.impl;

import it.unibo.goldhunt.items.api.Revealable;

//luca
public class Shield extends Item{

    private final static String ITEM_NAME = "Shield";

    public Revealable trap;

    @Override
    public String getName() {
        return ITEM_NAME;
    }

    @Override
    public boolean applyEffect() {
        if(context == null){
            throw new IllegalStateException("cannot bound item");
        }

        var playerop = context.playerop();

        if(trap != null){
            int lives = playerop.livesCount();
            trap.applyEffect();
            playerop.addLives(lives-playerop.livesCount());
            return true;
        }
        return false;
    }

    @Override
    public String shortString() {
        return "S";
    }

    public void bindTrap(Revealable trap){
        this.trap = trap;
    }
}
