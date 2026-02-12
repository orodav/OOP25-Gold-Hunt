package it.unibo.goldhunt.board.api;

import it.unibo.goldhunt.engine.api.Position;

/**
 * This interface models a read-only view of the game board.
 * It exposes access to the state of the board and its cells
 * without allowing any modification.
 */
public interface ReadOnlyBoard {

    /**
     * Returns the board's size.
     * 
     * @return the board's width and height
     */
    int boardSize();

    /**
     * Returns the read-only view of the cell at the given position.
     * 
     * @param p the cell's position
     * @return the cell in {@code p} position
     */
    ReadOnlyCell cellAt(Position p);

}
