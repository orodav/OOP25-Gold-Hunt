// AZZU

package it.unibo.goldhunt.board.impl;

import java.util.Objects;

import it.unibo.goldhunt.board.api.Board;
import it.unibo.goldhunt.board.api.Cell;
import it.unibo.goldhunt.board.api.RevealStrategy;
import it.unibo.goldhunt.engine.api.Position;

/**
 * This class implements RevealStrategy.
 * With this reveal strategy, when a cell with no adjacent traps is revealed,
 * all adjacent cells with no traps are revealed as well.
 */
public final class FloodReveal implements RevealStrategy {

    private static final int NO_ADJACENT_TRAPS = 0;

    /**
     * {@inheritDoc}
     */
    @Override
    public void reveal(final Board b, final Position p) {
        Objects.requireNonNull(b);
        Objects.requireNonNull(p);

        final Cell cell = b.getCell(p);

        if (cell.isRevealed() || cell.isFlagged()) { 
            return;
        }

        cell.reveal();
        if (cell.getAdjacentTraps() == NO_ADJACENT_TRAPS) {
            b.getAdjacentCells(p).forEach(adjacent -> {
                final Position adjacentPosition = b.getCellPosition(adjacent);
                reveal(b, adjacentPosition);
                });
            }
        }

}
