package it.unibo.goldhunt.items.impl;

import it.unibo.goldhunt.items.api.KindOfItem;
import it.unibo.goldhunt.player.api.PlayerOperations;

//luca
/**
 * Represents the "Gold" item.
 * 
 * <p>
 * When collected, it adds a fixed amount of gold to the player.
 * If the player has a Lucky Clover in the inventory the gold amount is double.
 */
public class Gold extends Item{

    public static final String ITEM_NAME = "Gold";

    /**
     * Returns the name of the item.
     * 
     * @return "Gold"
     */
    @Override
    public String getName() {
        return ITEM_NAME;
    }

    /**
     * Apllies the gold effect to the player.
     * 
     * <p>
     * Adds {@link #ADDED_GOLD} to the player's gold count.
     * if the player has a Lucky Clover, the gold is multiplied.
     * 
     * @param playerop the player receiving the gold.
     * @return the same PlayerOperations object after the effect.
     * @throws IllegalStateException if the item context is not bound.
     */
    @Override
    public PlayerOperations applyEffect(final PlayerOperations playerop) {
        if (context == null) {
            throw new IllegalStateException("item cannot bound");
        }
        final var inventory = context.inventory();
        int multiplier = 1;

        if (inventory.quantity(KindOfItem.LUCKYCLOVER) > 0) {
            multiplier = LUCKY_CLOVER_MULTIPLIER;

        }

        return playerop.addGold(ADDED_GOLD * multiplier);
    }

    /**
     * Returns a short string representing the item.
     * 
     * @return "G"
     */
    @Override
    public String shortString() {
        return "G";
    }

    /**
     * Returns the type of this item.
     * 
     * @return {@link KindOfItem#GOLD}
     */
    @Override
    public KindOfItem getItem() {
        return KindOfItem.GOLD;
    }

}
