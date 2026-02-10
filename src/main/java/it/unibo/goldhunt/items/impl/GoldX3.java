package it.unibo.goldhunt.items.impl;

import it.unibo.goldhunt.items.api.KindOfItem;
import it.unibo.goldhunt.player.api.PlayerOperations;

//luca
public class GoldX3 extends Item {

    public static final String ITEM_NAME = "Gold x3";

    @Override
    public PlayerOperations applyEffect(PlayerOperations playerop){
        if(context == null){
            throw new IllegalStateException("item cannot bound");
        }

        int multiplier = 1;
        if(playerop.inventory().quantity(KindOfItem.LUCKYCLOVER)>0){
            multiplier = LUCKY_CLOVER_MULTIPLIER;
        }
        
        return playerop.addGold(ADDED_GOLDX3 * multiplier);
    }

    @Override
    public String shortString(){
        return "X";
    }

    @Override
    public String getName() {
        return ITEM_NAME;
    }

    @Override
    public KindOfItem getItem() {
        return KindOfItem.GOLDX3;
    }

}
