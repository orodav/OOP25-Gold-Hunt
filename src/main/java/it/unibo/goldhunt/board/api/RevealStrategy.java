package it.unibo.goldhunt.board.api;

import it.unibo.goldhunt.engine.api.Position;

/**
 * This interface models the strategy for revealing cells.
 * This interface can be implemented to create different reveal strategies.
 */
@FunctionalInterface
public interface RevealStrategy {

    /**
     * Reveals the cell in the specified position of the specified board.
     * 
     * @param b the board
     * @param p the cell's position
     * @throws NullPointerException if {@code b} or {@code p} are {@code null}
     * @throws IndexOutOfBoundsException if {@code p} is out of {@code b}'s bounds
     */
    void reveal(Board b, Position p);

}
