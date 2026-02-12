package it.unibo.goldhunt.board.impl;

import java.util.Objects;

import it.unibo.goldhunt.board.api.Board;
import it.unibo.goldhunt.board.api.BoardFactory;
import it.unibo.goldhunt.board.api.CellFactory;

/**
 * This class is the implementantion of {@link BoardFactory}.
 */
public final class SquareBoardFactory implements BoardFactory {

    private final CellFactory cellFactory;

    /**
     * {@code SquareBoardFactory}'s constructor.
     * 
     * @param cellFactory the factory that creates the board's cells
     */
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
