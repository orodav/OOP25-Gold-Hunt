package it.unibo.goldhunt.items.impl;

import it.unibo.goldhunt.items.api.KindOfItem;
import it.unibo.goldhunt.player.api.PlayerOperations;

//luca
/**
 * Represents the "Gold x3" item.
 * <p>
 * When collected, it adds three times the normal gold amount to the player.
 * If the player has a Lucky Clover, the amount doubles.
 */
public class GoldX3 extends Item {

    public static final String ITEM_NAME = "Gold x3";

    /**
     * Applies the gold x3 effect to the player.
     * <p>
     * Adds {@link #ADDED_GOLDX3} to the player's gold count.
     * If the player has a lucky Clover, the gold is multiplied by {@link #LUCKY_CLOVER_MULTIPLIER}
     * 
     * @param playerop the player receiving the gold
     * @return the same PlayerOperations object after effect
     * @throws IllegalStateException if the item context is not bound
     */
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

    /**
     * Returns a short string representing the item.
     * 
     * @return "X"
     */
    @Override
    public String shortString(){
        return "X";
    }

    /**
     * Returns the name of the item.
     * 
     * @return "Gold x3"
     */
    @Override
    public String getName() {
        return ITEM_NAME;
    }

    /**
     * Returns the type of this item.
     * 
     * @return {@link KindOfItem#GOLDX3}
     */
    @Override
    public KindOfItem getItem() {
        return KindOfItem.GOLDX3;
    }

}
