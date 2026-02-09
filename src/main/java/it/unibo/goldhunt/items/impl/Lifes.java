package it.unibo.goldhunt.items.impl;

public class Lifes extends Item{

    public static final String ITEM_NAME = "life";

    @Override
    public String getName() {
        return ITEM_NAME;
    }


    @Override
    public boolean applyEffect() {
        if(context == null){
            throw new IllegalStateException("item cannot bound");
        }

        var playerop = context.playerop();

        if(playerop.livesCount() < MAX_QUANTITY_LIVES){
            playerop.addLives(PLUS_LIFE);
            return true;
        }
        return false;
    }

    @Override
    public String shortString() {
        return "L";
    }
}
