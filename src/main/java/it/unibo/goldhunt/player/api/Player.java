package it.unibo.goldhunt.player.api;

import it.unibo.goldhunt.engine.api.Position;

/**
 * Represents the immutable view of a player in the game.
 * 
 * A {@code Player} provides access to the player's current state.
 * 
 * Implementations are expected tobe immutable.
 */
public interface Player {

    /**
     * Returns the current position of the player on the board.
     * 
     * @return the player's position
     */
    Position position();

    /**
     * Returns the current number of lives available to the player.
     * 
     * @return the remaining lives
     */
    int livesCount();

    /**
     * Returns the amount of gold owned by the player.
     * 
     * @return the gold count
     */
    int goldCount();

    /**
     * Returns the player's inventory.
     * 
     * @return the inventory associated with this player
     */
    Inventory inventory();

    /**
     * Returns a new Player instance equal to this one, 
     * but with the given inventory.
     * 
     * @param inventory the new inventory
     * @return a new {@code Player} instance with updated inventory
     * @throws IllegalArgumentException if {@code inventory} is {@code null}
     */
    PlayerOperations withInventory(Inventory inventory);

}
