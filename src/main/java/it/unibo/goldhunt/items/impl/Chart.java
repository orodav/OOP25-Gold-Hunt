package it.unibo.goldhunt.items.impl;

import it.unibo.goldhunt.items.api.Item;
//luca
public class Chart extends Item{

    private final static boolean consumable = true;
    private final static String ITEM_NAME = "Map";

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
        getAdjacentPosition(player.getCell()).getAdjacentPosition();
        stream.pos.cell.contains.trap.reveal;
    }

    @Override
    public boolean canUse() {
        return inGame;
    }

    @Override
    public String ShortString() {
        return "M";
    }

}
