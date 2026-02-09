package it.unibo.goldhunt.items.impl;

//luca
public class Gold extends Item{

    public static final String ITEM_NAME = "Gold";

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
        var inventory = context.inventory();
        int multiplier = 1;

        if(inventory.hasAtLeast(new LuckyClover(), MAX_QUANTITY_CLOVER)){
            multiplier = LUCKY_CLOVER_MULTIPLIER;

        }

        playerop.addGold(ADDED_GOLD * multiplier);

        return true;
    }

    @Override
    public String shortString() {
        return "G";
    }

}
