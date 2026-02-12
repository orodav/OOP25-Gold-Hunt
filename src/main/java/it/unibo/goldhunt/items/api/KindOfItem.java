package it.unibo.goldhunt.items.api;

import it.unibo.goldhunt.player.api.PlayerOperations;

/**
 * Represents the various types of items available in the game.
 * 
 * <p>
 * Each constant provides basic information like the item's name 
 * and its identification.
 */
public enum KindOfItem implements ItemTypes {
    CHART("Map"),
    DYNAMITE("Dynamite"),
    PICKAXE("Pickaxe"),
    LUCKYCLOVER("Lucky Clover"),
    LIVES("Life"),
    GOLDX3("Gold x3"),
    GOLD("Gold"),
    SHIELD("Shield");

    private final String itemName;

    KindOfItem(final String itemName) {
        this.itemName = itemName;
    }

    /**
     * Returns the display name of the item.
     * 
     * @return the item name as a string.
     */
    @Override
    public String getName() {
        return itemName;
    }

    /**
     * Applies the effect given the player.
     * 
     * @param playerop the player
     * @return player updated.
     */
    @Override
    public PlayerOperations applyEffect(final PlayerOperations playerop) {
        return playerop;
    }

    @Override
    public String shortString() {
        return this.name();
    }

    @Override
    public KindOfItem getItem() {
        return this;
    }
}
