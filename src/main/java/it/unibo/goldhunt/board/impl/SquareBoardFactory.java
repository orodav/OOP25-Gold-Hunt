// AZZU

package it.unibo.goldhunt.board.impl;

import java.util.Objects;

import it.unibo.goldhunt.board.api.Board;
import it.unibo.goldhunt.board.api.BoardFactory;
import it.unibo.goldhunt.board.api.CellFactory;

/**
 * This class is the implementantion of {@code BoardFactory}.
 */
public class SquareBoardFactory implements BoardFactory {

    private CellFactory cellFactory;

    public SquareBoardFactory(final CellFactory cellFactory) {
        this.cellFactory = Objects.requireNonNull(cellFactory);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Board createEmptyBoard(final int boardSize) {
        return new SquareBoard(boardSize, cellFactory);
    }

}
