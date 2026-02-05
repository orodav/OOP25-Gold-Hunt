// AZZU

package it.unibo.goldhunt.board.api;

import java.util.Optional;

import it.unibo.goldhunt.items.api.CellContent;

/**
 * This interface models a cell factory.
 * This interface can be implemented to create cells with every permitted object.
 */
public interface CellFactory {

    /**
     * Creates an empty cell.
     * 
     * @return the created cell
     */
    Cell createCell();

    /**
     * Creates a cell with content.
     * 
     * @param content the cell's content
     * @return the created cell
     */
    Cell createCell(Optional<CellContent> content);
}
