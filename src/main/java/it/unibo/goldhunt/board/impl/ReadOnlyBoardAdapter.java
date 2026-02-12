package it.unibo.goldhunt.board.impl;

import java.util.Objects;

import it.unibo.goldhunt.board.api.Board;
import it.unibo.goldhunt.board.api.ReadOnlyBoard;
import it.unibo.goldhunt.board.api.ReadOnlyCell;
import it.unibo.goldhunt.engine.api.Position;

/**
 * This class implements {@link ReadOnlyBoard}.
 */
public final class ReadOnlyBoardAdapter implements ReadOnlyBoard {

    private final Board board;

    /**
     * {@code ReadOnlyBoardAdapter}'s constructor.
     * It creates a read-only view of the given {@link Board}.
     * 
     * @param board the given board
     * @throws NullPointerException if {@code board} is {@code null}
     */
    public ReadOnlyBoardAdapter(final Board board) {
        Objects.requireNonNull(board);
        this.board = board;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int boardSize() {
        return this.board.getBoardSize();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ReadOnlyCell cellAt(final Position p) {
        return new ReadOnlyCellAdapter(board.getCell(p));
    }

}
