package it.unibo.goldhunt.items.impl;

import it.unibo.goldhunt.items.api.KindOfItem;
import it.unibo.goldhunt.player.api.PlayerOperations;

/**
 * Implementation of the Lucky Clover item.
 * 
 * <p>
 * This item can be collected by the player and added to their inventory
 * to provide specific bonuses.
 */
public class LuckyClover extends AbstractItem {

    private static final String ITEM_NAME = "Lucky clover";

    /**
     * Returns the name of the item.
     * 
     * @return "Lucky Clover"
     */
    @Override
    public String getName() {
        return ITEM_NAME;
    }

    /**
     * Adds the clover to the player's inventory.
     * 
     * @param playerop the player using the item.
     * @return the updated player.
     * @throws IllegalArgumentException if player or context is null.
     */
    @Override
    public PlayerOperations applyEffect(final PlayerOperations playerop) {
        if (playerop == null) {
            throw new IllegalArgumentException();
        }

        if (getContext() == null) {
            throw new IllegalArgumentException();
        }
        playerop.addItem(getItem(), MAX_QUANTITY_CLOVER);
        return playerop;
    }

    /**
     * Returns a short string representing the item.
     * 
     * @return "C"
     */
    @Override
    public String shortString() {
        return "C";
    }

    /**
     * Returns the type of this item.
     * 
     * @return {@link KindOfItem#LUCKYCLOVER}
     */
    @Override
    public KindOfItem getItem() {
        return KindOfItem.LUCKYCLOVER;
    }

    @Override
    public PlayerOperations toInventory(final PlayerOperations playerop){
        return playerop.addItem(this.getItem(), 1);
    }
}
