package it.unibo.goldhunt.items.impl;

import it.unibo.goldhunt.items.api.Item;
//luca
public class Dynamite extends Item{

    private final static boolean consumable = true;
    private final static String ITEM_NAME = "Dynamite";


    @Override
    public boolean isConsumable() {
        return consumable;
    }

    @Override
    public String getName() {
        return ITEM_NAME;
    }

    @Override
    public void applyEffect() {
        getAdjacentPosition(player.getCell());
        
    }

    @Override
    public boolean canUse() {
        return inGame;
    }

    @Override
    public String ShortString() {
        return "D";
    }

}
