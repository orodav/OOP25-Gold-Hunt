package it.unibo.goldhunt.items.impl;
//luca
public class GoldX3 extends Item {

    public static final String ITEM_NAME = "Gold x3";

    @Override
    public boolean applyEffect(){
        if(context == null){
            throw new IllegalStateException("item cannot bound");
        }
        var playerop = context.playerop();

        int multiplier = 1;
        if(playerop.inventory().hasAtLeast(new LuckyClover(), MAX_QUANTITY_CLOVER)){
            multiplier = LUCKY_CLOVER_MULTIPLIER;
        }
        
        playerop.addGold(ADDED_GOLDX3 * multiplier);
        
        return true;
    }

    @Override
    public String shortString(){
        return "X";
    }

    @Override
    public String getName() {
        return ITEM_NAME;
    }

}
