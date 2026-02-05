package it.unibo.goldhunt.items.impl;

import it.unibo.goldhunt.items.api.Item;
//luca
public class Gold extends Item{

    private boolean consumable = true;
    public static final String ITEM_NAME = "Gold";
    private boolean usage = false;

    Gold gold = new Gold();

    @Override
    public boolean isConsumable() {
        return consumable;
    }

    @Override
    public String getName() {
        return ITEM_NAME;
    }

    @Override
    public boolean canUse() {
        return usage;
    }

    @Override
    public void applyEffect() {
        inventory.add(gold, 1);
    }

    @Override
    public String shortString() {
        return "G";
    }

}
