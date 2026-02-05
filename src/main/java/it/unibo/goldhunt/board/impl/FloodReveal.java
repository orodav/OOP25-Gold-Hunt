// AZZU

package it.unibo.goldhunt.board.impl;

import java.util.Objects;

import it.unibo.goldhunt.board.api.Board;
import it.unibo.goldhunt.board.api.Cell;
import it.unibo.goldhunt.board.api.RevealStrategy;
import it.unibo.goldhunt.engine.api.Position;

public class FloodReveal implements RevealStrategy {

    private static final int NO_ADJACENT_TRAPS = 0;

    /**
     * {@inheritDoc}
     */
    @Override
    public void reveal(Board b, Position p) {
        Objects.requireNonNull(b);

        final Cell cell = b.getCell(p);

        if (cell.isRevealed() || cell.isFlagged()) { 
            return;
        }

        cell.reveal();
        if (cell.getAdjacentTraps() == NO_ADJACENT_TRAPS) {
            b.getAdjacentCells(p).forEach(adjacent -> {
                Position adjacentPosition = b.getCellPosition(adjacent);
                reveal(b, adjacentPosition);});
            }
        }

}
