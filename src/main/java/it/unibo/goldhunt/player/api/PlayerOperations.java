package it.unibo.goldhunt.player.api;

import it.unibo.goldhunt.engine.api.Position;
import it.unibo.goldhunt.items.api.ItemTypes;

/**
 * Represents the operational view of a player.
 */
public interface PlayerOperations extends Player{

    /**
     * Returns a new player instance moved to the specified position.
     * 
     * @param p the return position
     * @return a new {@code PlayerOperations} instance with updated position
     * @throws IllegalArgumentException if {@code p} is {@code null}
     */
    PlayerOperations moveTo(Position p);

    /**
     * Returns a new player instance with the given amount of gold added.
     * 
     * @param num the gold amount to add
     * @return a new {@code PlayerOperations} instance with updated gold count
     * @throws IllegalArgumentException if {@code num} is negative
     */
    PlayerOperations addGold(int num);

    /**
     * Returns a new player instance with the given amount of lives added.
     * 
     * @param num the lives amount to add
     * @return a new {@code PlayerOperations} instance with updated lives count
     * @throws IllegalArgumentException if {@code num} is negative
     */
    PlayerOperations addLives(int num);

    /**
     * Returns a new player instance with the specified quantity of an item added.
     * 
     * @param item the item type to add
     * @param quantity the amount to add
     * @return a new {@code PlayerOperations} instance with updated inventory
     * @throws IllegalArgumentException if {@code item} is {@code null}
     *                                  or {@code quantity} is negative
     */
    PlayerOperations addItem(ItemTypes item, int quantity);

    /**
     * Returns a new player instance with the specified quantity of an item used/consumed.
     * 
     * @param item the item type to use
     * @param quantity the amount to use
     * @return a new {@code PlayerOperations} instance with updated inventory
     * @throws IllegalArgumentException if {@code item} is {@code null},
     *                                  {@code quantity} is negative,
     *                                  or insufficient quantity is available
     */
    PlayerOperations useItem(ItemTypes item, int quantity);
}
