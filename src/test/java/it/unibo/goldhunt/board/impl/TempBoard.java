package it.unibo.goldhunt.board.impl;

import java.util.List;
import java.util.Objects;

import it.unibo.goldhunt.board.api.Board;
import it.unibo.goldhunt.board.api.Cell;
import it.unibo.goldhunt.engine.api.Position;

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
    public Cell getCell(Position p) {
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
    public boolean isPositionValid(Position p) {
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
    public Position getCellPosition(Cell cell) {
        throw new UnsupportedOperationException(UNNECESSARY_METHOD_FOR_TESTING);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Cell> getAdjacentCells(Position p) {
        throw new UnsupportedOperationException(UNNECESSARY_METHOD_FOR_TESTING);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Cell> getRow(int index) {
        throw new UnsupportedOperationException(UNNECESSARY_METHOD_FOR_TESTING);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Cell> getColumn(int index) {
        throw new UnsupportedOperationException(UNNECESSARY_METHOD_FOR_TESTING);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isAdjacent(Position p1, Position p2) {
        throw new UnsupportedOperationException(UNNECESSARY_METHOD_FOR_TESTING);
    }

    private void cellsSnapshot() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                board[i][j] = new TempCell();
            }
        }
    }

}
