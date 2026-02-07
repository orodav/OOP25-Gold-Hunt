//SARA
package it.unibo.goldhunt.configuration.api;

import it.unibo.goldhunt.board.api.Board;
import it.unibo.goldhunt.engine.api.Position;

/**
 * This interface models a board generator, responsible for creating and initializing
 * the game board according to the selected level configuration.
 */
public interface BoardGenerator {

    /**
     * Generates a new game board according to the level configuration.
     * The board is created with guaranteed safe path between the start and exit positions,
     * and populated with traps and items.
     * 
     * @param config the level configuration used to define board size and contents
     * @param start the starting position of the player
     * @param exit the exit position of the level
     * @return the generated and initialized board
     * @throws 
     */
    Board generate(LevelConfig config, Position start, Position exit);
}

