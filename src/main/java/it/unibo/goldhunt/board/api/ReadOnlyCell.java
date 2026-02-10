// AZZU

package it.unibo.goldhunt.board.api;

import java.util.Optional;

/**
 * This interface models a read-only view of a cell.
 * It exposes only the information needed to observe
 * the state of a cell, without allowing any modification.
 */
public interface ReadOnlyCell {

    /**
     * Checks if the cell is revealed. 
     * 
     * @return true if the cell is revealed, false otherwise
     */
    boolean isRevealed();

    /**
     * Checks if the cell is flagged.
     * 
     * @return true if the cell is flagged, false otherwise
     */
    boolean isFlagged();

    /**
     * Returns the cell's adjacent traps.
     * 
     * @return an {@code int} that represents the
     *     number of traps in the surrounding cells
     */
    int getAdjacentTraps();

    /**
     * Returns the identifier of the content in the cell, if there is any.
     * 
     * @return an {@link Optional} containing the content identifier,
     *     or an empty {@code Optional} if the cell has no content
     */
    Optional<String> contentID();

}
