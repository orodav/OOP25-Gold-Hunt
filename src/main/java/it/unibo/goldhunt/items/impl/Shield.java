package it.unibo.goldhunt.items.impl;
//luca
import java.util.HashMap;

import it.unibo.goldhunt.items.api.Item;
import it.unibo.goldhunt.items.api.Trap;

public class Shield extends Item{

    private final static boolean consumable = true;
    private final static String ITEM_NAME = "Sheild";
    private Trap trap;


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
        if(trap.applyEffect()){
            player.lifeCounter++;

        }
    }

    @Override
    public boolean canUse() {
        return inGame;
    }

    @Override
    public String shortString() {
        return "S";
    }

}
