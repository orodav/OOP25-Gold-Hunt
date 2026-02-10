//SARA
package it.unibo.goldhunt.configuration.api;

import java.util.Map;

/**
 * This interface models different level configurations for the game.
 */
public interface LevelConfig {
    
    /**
     * Returns the configuration of items for the current difficulty level.
     * Each entry maps an item symbol (used by {@link ItemFactory}) to the number of instances 
     * to be placed on the board.
     * 
     * @return a {@link Map} containing item identifiers and their quantities.
     */
    Map<String, Integer> getItemConfig();

    /**
     * Returns the number of traps for the current difficulty level.
     * 
     * @return the number of traps to be placed on the board.
     */
    int getTrapCount();

    /**
     * Return the size of the board for the current difficulty level.
     * 
     * @return the size of the board (N X N grid).
     */
    int getBoardSize();
}


