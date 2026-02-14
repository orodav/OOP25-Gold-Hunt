package it.unibo.goldhunt.board.impl;

import java.util.List;
import java.util.Objects;

import it.unibo.goldhunt.board.api.Board;
import it.unibo.goldhunt.board.api.Cell;
import it.unibo.goldhunt.engine.api.Position;

/**
 * This class is a support class for {@link ReadOnlyBoardAdapterTest}.
 */
public class TempBoard implements Board {

    private static final String UNNECESSARY_METHOD_FOR_TESTING = "Not needed in TempBoard";

    private final Cell[][] board;

    /**
     * This constructor creates a {@code TempBoard} full of {@link TempCell}.
     * 
     * @param boardSize the board's width and height
     * @throws IllegalArgumentException if {@code boardSize} is less than or equal to 0
     */
    public TempBoard(final int boardSize) {
        if (boardSize <= 0) {
            throw new IllegalArgumentException("The board size must be greater than 0.");
        }
        this.board = new Cell[boardSize][boardSize];
        cellsSnapshot();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Cell getCell(final Position p) {
        Objects.requireNonNull(p);
        if (!isPositionValid(p)) {
            throw new IndexOutOfBoundsException("This position is not in the board");
        }
        return this.board[p.x()][p.y()];
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getBoardSize() {
        return this.board.length;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isPositionValid(final Position p) {
        Objects.requireNonNull(p);
        return p.x() >= 0 && p.x() < board.length
            && p.y() >= 0 && p.y() < board.length;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Cell> getBoardCells() {
        throw new UnsupportedOperationException(UNNECESSARY_METHOD_FOR_TESTING);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Position getCellPosition(final Cell cell) {
        throw new UnsupportedOperationException(UNNECESSARY_METHOD_FOR_TESTING);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Cell> getAdjacentCells(final Position p) {
        throw new UnsupportedOperationException(UNNECESSARY_METHOD_FOR_TESTING);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Cell> getRow(final int index) {
        throw new UnsupportedOperationException(UNNECESSARY_METHOD_FOR_TESTING);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Cell> getColumn(final int index) {
        throw new UnsupportedOperationException(UNNECESSARY_METHOD_FOR_TESTING);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isAdjacent(final Position p1, final Position p2) {
        throw new UnsupportedOperationException(UNNECESSARY_METHOD_FOR_TESTING);
    }

    @SuppressWarnings("PMD.ForLoopCanBeForeach")
    private void cellsSnapshot() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                board[i][j] = new TempCell();   
            }
        }
    }

}
