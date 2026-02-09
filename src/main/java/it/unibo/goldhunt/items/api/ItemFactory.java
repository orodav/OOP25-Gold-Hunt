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


    Item generateItem(String item, Board board, PlayerOperations playerop, Inventory inventory);
    Item generateItem(String item);
    
}
