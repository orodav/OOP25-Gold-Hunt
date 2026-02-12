package it.unibo.goldhunt.items.api;

import it.unibo.goldhunt.board.api.Board;
import it.unibo.goldhunt.player.api.Inventory;
import it.unibo.goldhunt.player.api.PlayerOperations;

/**
 * Holds the context needed for an item to operate.
 * 
 * <p>
 * Contains the {@link Board}, the {@link PlayerOperation}, and the player's {@link Inventory}
 * 
 * @param board the game board
 * @param playerop the player using the item
 * @param inventory player's inventory
 */
public record ItemContext(Board board, PlayerOperations playerop, Inventory inventory) {

}
