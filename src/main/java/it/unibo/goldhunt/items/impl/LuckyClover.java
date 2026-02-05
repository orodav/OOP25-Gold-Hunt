package it.unibo.goldhunt.items.impl;
import it.unibo.goldhunt.items.api.Item;

public class LuckyClover extends Item{

    private final static boolean consumable = false;
    private final static String ITEM_NAME = "Lucky clover";
    Gold gold;
    private boolean usage = false;

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
        player.setMultiplier(2);
    }

    @Override
    public boolean canUse() {
        return usage;
    }

    @Override
    public String shortString() {
        return "C";
    }

}
