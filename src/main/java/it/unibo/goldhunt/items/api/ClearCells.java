package it.unibo.goldhunt.items.api;

import java.util.List;

import it.unibo.goldhunt.board.api.Cell;
import it.unibo.goldhunt.items.impl.Trap;

/**
 * Provides an interface for clearing and revealing cells.
 * <p>
 * The default method removes any {@link Trap} instance contained
 * in the given cells and then reveals them.
 */
public interface ClearCells {

    /**
     * Removes traps from the given cells and reveals them.
     * <p>
     * If a cell contains a {@link Trap}, the trap is removed.
     * After that, all cells are revealed.
     *
     * @param remove the list of cells to clear
     */
        default void disarm(List<Cell> remove){
        remove.stream()
        .filter(Cell::hasContent)
        .forEach(cell -> cell.getContent()
        .filter(Trap.class::isInstance)
        .ifPresent(c -> cell.removeContent()));

        remove.forEach(Cell::reveal);
    }
}
