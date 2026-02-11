package it.unibo.goldhunt.player.api;

import it.unibo.goldhunt.items.api.ItemTypes;

/**
 * Represents the player's inventory.
 * 
 * An {@code Inventory} manages item quantities owned by the player.
 * 
 * Implementations are expected to be immutable.
 * 
 */
public interface Inventory {

    /**
     * Adds a given quantity of the specified item to the inventory.
     * 
     * @param item the item type to add
     * @param quantity the amount to add
     * @return a new {@code Inventory} reflecting the updated state
     * @throws IllegalArgumentException if {@code item} is {@code null}
     *                                  or {@code quantity} is negative
     */
    Inventory add(ItemTypes item, int quantity);

    /**
     * Removes a given quantity of the specified item from the inventory.
     * 
     * @param item the item type to remove
     * @param quantity the amount to remove
     * @return a new {@code Inventory} reflecting the updated state
     * @throws IllegalArgumentException if {@code item} is {@code null}
     *                                  or {@code quantity} is negative
     *                                  or sufficient quantity is not available
     */
    Inventory remove(ItemTypes item, int quantity);

    /** 
     * Checks whether the inventory contains at least the specified quantity
     * of the given item.
     * 
     * @param item the item type to check
     * @param quantity the required quantity
     * @return {@code true} if the inventory contains at least
     *         the specified quantity
     * @throws IllegalArgumentException if {@code item} is {@code null}
     *                                  or {@code quantity} is negative
     */
    default boolean hasAtLeast(ItemTypes item, int quantity) {
        if (item == null) {
            throw new IllegalArgumentException("item can't be null");
        }
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity must be >= 0");
        }
        return this.quantity(item) >= quantity;
    }

    /**
     * Returns the corrent quantity of the specified item.
     * 
     * @param item the item type
     * @return the amount currently stored
     * @throws IllegalArgumentException if {@code item} is {@code null}
     */
    int quantity(ItemTypes item);
}