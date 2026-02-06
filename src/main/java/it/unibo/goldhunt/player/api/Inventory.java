package it.unibo.goldhunt.player.api;

import it.unibo.goldhunt.items.api.ItemTypes;

//davv
public interface Inventory {

    Inventory add(ItemTypes item, int quantity);

    Inventory remove(ItemTypes item, int quantity);

    /** Default Method */
    default boolean hasAtLeast(ItemTypes item, int quantity) {
        if (item == null) {
            throw new IllegalArgumentException("item can't be null");
        }
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity must be > 0");
        }
        return this.quantity(item) >= quantity;
    }

    int quantity(ItemTypes item);
}