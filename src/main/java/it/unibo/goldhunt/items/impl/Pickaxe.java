package it.unibo.goldhunt.items.impl;
//luca
import java.util.Random;

import it.unibo.goldhunt.items.api.Item;

public class Pickaxe extends Item{

    private final static boolean consumable = true;
    private final static String ITEM_NAME = "Pickaxe";

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
        Random random = new Random();
        random.
    }

    @Override
    public boolean canUse() {
        return inGame;
}

    @Override
    public String shortString() {
        return "P";
    }
}