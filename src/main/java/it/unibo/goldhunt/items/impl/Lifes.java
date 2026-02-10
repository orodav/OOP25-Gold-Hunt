package it.unibo.goldhunt.items.impl;

import it.unibo.goldhunt.items.api.KindOfItem;
import it.unibo.goldhunt.player.api.PlayerOperations;

/**
 * Represents a life item.
 * <p>
 * Increases the player's lives by one if not at maximum.
 */
public class Lifes extends Item{

    public static final String ITEM_NAME = "life";

    /**
     * Returns the name of the item.
     * 
     * @return "life"
     */
    @Override
    public String getName() {
        return ITEM_NAME;
    }

    /**
     * Adds a life to the player if they aren't already at maximum health.
     * 
     * @param playerop the player to heal.
     * @return the updated player, or null if no life was added.
     * @throws IllegalStateException if the item context is missing.
     */
    @Override
    public PlayerOperations applyEffect(final PlayerOperations playerop) {
        if(context == null){
            throw new IllegalStateException("item cannot bound");
        }

        if(playerop.livesCount() < MAX_QUANTITY_LIVES){
            return playerop.addLives(PLUS_LIFE);
        
        }
        return null;
    }

    /**
     * Returns a short string representing the item.
     * 
     * @return "L"
     */
    @Override
    public String shortString() {
        return "L";
    }

    /**
     * Returns the type of this item.
     * 
     * @return {@link KindOfItem#LIVES}
     */
    @Override
    public KindOfItem getItem() {
        return KindOfItem.LIVES;
    }
}
