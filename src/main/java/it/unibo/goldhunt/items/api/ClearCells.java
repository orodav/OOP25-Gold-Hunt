package it.unibo.goldhunt.items.api;

import java.util.List;

import it.unibo.goldhunt.board.api.Cell;
import it.unibo.goldhunt.items.impl.Trap;

public interface ClearCells {
        default void disarm(List<Cell> remove){
        remove.stream()
        .filter(Cell::hasContent)
        .forEach(cell -> cell.getContent()
        .filter(Trap.class::isInstance)
        .ifPresent(c -> cell.removeContent()));

        remove.forEach(Cell::reveal);
    }
}
