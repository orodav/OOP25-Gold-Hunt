package it.unibo.goldhunt.items.impl;

import it.unibo.goldhunt.items.api.KindOfItem;
import it.unibo.goldhunt.player.api.PlayerOperations;

//luca
public class Gold extends Item{

    public static final String ITEM_NAME = "Gold";

    @Override
    public String getName() {
        return ITEM_NAME;
    }

    @Override
    public PlayerOperations applyEffect(PlayerOperations playerop) {
        if(context == null){
            throw new IllegalStateException("item cannot bound");
        }
        var inventory = context.inventory();
        int multiplier = 1;

        if(inventory.quantity(KindOfItem.LUCKYCLOVER)>0){
            multiplier = LUCKY_CLOVER_MULTIPLIER;

        }

        return playerop.addGold(ADDED_GOLD * multiplier);
    }

    @Override
    public String shortString() {
        return "G";
    }

    @Override
    public KindOfItem getItem() {
        return KindOfItem.GOLD;
    }

}
