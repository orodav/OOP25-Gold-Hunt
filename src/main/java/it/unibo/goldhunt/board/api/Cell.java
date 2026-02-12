package it.unibo.goldhunt.board.api;

import java.util.Optional;

import it.unibo.goldhunt.items.api.CellContent;

/**
 * This interface models a cell contained in a Minesweeper-like game's board.
 */
public interface Cell {

    /**
     * Reveals the cell if it is not flagged.
     */
    void reveal();

    /**
     * Checks if the cell is revealed.
     * 
     * @return true if the cell is revealed, false otherwise
     */
    boolean isRevealed();

    /**
     * Toggles the cell's flag status if it is not revealed.
     */
    void toggleFlag();

    /**
     * Checks if the cell is flagged.
     * 
     * @return true if the cell is flagged, false otherwise
     */
    boolean isFlagged();

    /**
     * Returns the number of adjacent traps.
     * 
     * @return an {@code int} that represents the number of
     *     traps in the eight cells surrounding the cell
     */
    int getAdjacentTraps();

    /**
     * Sets the number of adjacent traps to {@code n}.
     * This method is meant to be used only during the board generation.
     * 
     * @param n the number of adjacent traps
     * @throws IllegalArgumentException if {@code n} is negative or greater than 8
     */
    void setAdjacentTraps(int n);

    /**
     * Checks if the cell contains a permitted object.
     * 
     * @return true if the cell has the object, false otherwise
     */
    boolean hasContent();

    /**
     * Returns the cell's object.
     * 
     * @return an {@link Optional} of the object in the cell,
     *      or an empty {@code Optional} if the cell is empty.
     */
    Optional<CellContent> getContent();

    /**
     * Sets the cell's content if the cell is empty.
     * This method is meant to be used only during the board generation.
     * 
     * @param content the cell's content
     * @throws IllegalStateException if the cell is not empty.
     */
    void setContent(CellContent content);

    /**
     * Removes the content from the cell.
     */
    void removeContent();

}
