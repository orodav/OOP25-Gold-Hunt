// AZZU

package it.unibo.goldhunt.board.api;

/**
 * This interface models a board factory.
 * It creates a board with empty cells.
 */
public interface BoardFactory {

    /**
     * Creates a square board full of cells with no content.
     * 
     * @param boardSize the board's width and height
     * @return the created board
     * @throws IllegalArgumentException if {@code boardSize} is less than or equal to 0
     */
    Board createEmptyBoard(int boardSize);

}
