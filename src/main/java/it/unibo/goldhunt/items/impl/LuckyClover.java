package it.unibo.goldhunt.items.impl;

import it.unibo.goldhunt.player.api.Inventory;

public class LuckyClover extends Item{

    private final static String ITEM_NAME = "Lucky clover";

    @Override
    public String getName() {
        return ITEM_NAME;
    }

    @Override
    public boolean applyEffect() {
        Inventory inventory = context.inventory();
        inventory.add(this, MAX_QUANTITY_CLOVER);
        return true;
    }

    @Override
    public String shortString() {
        return "C";
    }

}