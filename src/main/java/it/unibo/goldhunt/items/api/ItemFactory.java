package it.unibo.goldhunt.items.api;

import it.unibo.goldhunt.board.api.Board;
import it.unibo.goldhunt.items.impl.Item;
import it.unibo.goldhunt.player.api.Inventory;
import it.unibo.goldhunt.player.api.PlayerOperations;

/**
 * Factory interface responsible for the creation of game items.
 * <p>
 * Implementations of this interface encapsulate the logic used
 * to generate different types of {@link Item} based on the identifier
 * of the class.
 */
public interface ItemFactory {

    /**
     * Generates an item using full context 
     * 
     * @param item the item id
     * @param board the game board
     * @param playerop the player using the item
     * @param inventory player's inventory
     * @return the generated item 
     */
    Item generateItem(String item, Board board, PlayerOperations playerop, Inventory inventory);
    
    /**
     * Generates an item using only the id
     * 
     * @param item the item id
     * @return the generated item
     */
    Item generateItem(String item);
    
}
