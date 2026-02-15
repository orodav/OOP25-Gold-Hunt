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
    CHART("Map", "M"),
    DYNAMITE("Dynamite", "D"),
    PICKAXE("Pickaxe", "P"),
    LUCKYCLOVER("Lucky Clover", "C"),
    LIVES("Life", "L"),
    GOLDX3("Gold x3", "X"),
    GOLD("Gold", "G"),
    SHIELD("Shield", "S");

    private final String itemName;
    private final String symbol;

    KindOfItem(final String itemName, final String symbol) {
        this.itemName = itemName;
        this.symbol = symbol;
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
        return this.symbol;
    }

    @Override
    public KindOfItem getItem() {
        return this;
    }
}
