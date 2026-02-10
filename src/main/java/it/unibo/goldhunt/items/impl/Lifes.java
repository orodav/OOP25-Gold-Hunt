package it.unibo.goldhunt.items.impl;

import it.unibo.goldhunt.items.api.KindOfItem;
import it.unibo.goldhunt.player.api.PlayerOperations;

public class Lifes extends Item{

    public static final String ITEM_NAME = "life";

    @Override
    public String getName() {
        return ITEM_NAME;
    }


    @Override
    public PlayerOperations applyEffect(PlayerOperations playerop) {
        if(context == null){
            throw new IllegalStateException("item cannot bound");
        }

        if(playerop.livesCount() < MAX_QUANTITY_LIVES){
            return playerop.addLives(PLUS_LIFE);
        
        }
        return null;
    }

    @Override
    public String shortString() {
        return "L";
    }


    @Override
    public KindOfItem getItem() {
        return KindOfItem.LIVES;
    }
}
