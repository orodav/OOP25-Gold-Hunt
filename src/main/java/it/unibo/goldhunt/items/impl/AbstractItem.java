package it.unibo.goldhunt.items.impl;

import it.unibo.goldhunt.items.api.ItemContext;
import it.unibo.goldhunt.items.api.ItemTypes;

/**
 * Base class for all game items
 * 
 * <p>
 * Provides shared constants, context binding, and a position field.
 */
public abstract class AbstractItem implements ItemTypes {

    /**
     * Maximum quantities for various items. 
     */
    public static final int MAX_QUANTITY_CLOVER = 1;
    public static final int MAX_QUANTITY_SHIELD = 1;
    public static final int MAX_QUANTITY_ITEMS = 3;
    public static final int MAX_QUANTITY_LIVES = 3;
    public static final int ADDED_GOLD = 1;
    public static final int ADDED_GOLDX3 = 3;
    public static final int PLUS_LIFE = 1;
    public static final int RADIUS = 2;
    public static final int LUCKY_CLOVER_MULTIPLIER = 2;

    /**
     * Context containing player, inventory, and board.
     */
    private ItemContext context;

    /**
     * Binds the item to its context.
     * 
     * @param contextItem the context containing board, player, and the inventory.
     */
    public void bind(final ItemContext contextItem) {
        this.context = contextItem;
    }

    /**
     * Getter for the context.
     * 
     * @return an item context type.
     */
    protected ItemContext getContext() {
        return this.context;
    }
}
