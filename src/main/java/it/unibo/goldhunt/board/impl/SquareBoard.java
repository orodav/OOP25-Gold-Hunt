// AZZU

package it.unibo.goldhunt.board.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import it.unibo.goldhunt.board.api.Board;
import it.unibo.goldhunt.board.api.Cell;
import it.unibo.goldhunt.engine.api.Position;
import it.unibo.goldhunt.items.api.CellContent;

/**
 * This class implements Board and it models a square board.
 */
public class SquareBoard implements Board {

    private static final String CELL_NOT_IN_BOARD_EXCEPTION = "This cell is not in the board: ";
    private static final String INDEX_EXCEPTION_MESSAGE = "This index is not in the board: ";
    private static final String NULL_CELL_EXCEPTION = "The cell cannot be null";
    private static final String NULL_POSITION_EXCEPTION = "The position cannot be null";
    private static final String POSITION_EXCEPTION_MESSAGE = "This position is not in the board: ";

    private final Cell[][] board;
    private final Map<Cell, Position> cellPositions;

    /**
     * BaseBoard's constructor.
     * This constructor sets the board's size.
     * 
     * @param boardSize
     */
    protected SquareBoard(final int boardSize) {
        this.board = new Cell[boardSize][boardSize];
        this.cellPositions = new HashMap<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Cell> getBoardCells() {
        return Arrays.stream(board)
            .flatMap(Arrays::stream)
            .toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Cell getCell(final Position p) {
        checkValidPosition(p);

        return this.board[p.x()][p.y()];
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Position getCellPosition(final Cell cell) {
        Objects.requireNonNull(cell, NULL_CELL_EXCEPTION);
        checkValidCell(cell);

        return cellPositions.get(cell);
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
    public List<Cell> getRow(final int index) {
        checkValidIndex(index);

        return Arrays.stream(this.board[index]).toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Cell> getColumn(final int index) {
        checkValidIndex(index);

        return Arrays.stream(board).map(row -> row[index]).toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Cell> getAdjacentCells(final Position p) {
        checkValidPosition(p);
        
        return getBoardCells().stream()
            .filter(cell -> isAdjacent(p, getCellPosition(cell)))
            .toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setCell(final Cell cell, final Position p) {
        Objects.requireNonNull(cell, NULL_CELL_EXCEPTION);
        checkValidPosition(p);

        this.board[p.x()][p.y()] = cell;
        this.cellPositions.put(cell, p);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isPositionValid(final Position p) {
        Objects.requireNonNull(p, NULL_POSITION_EXCEPTION);
        
        final int x = p.x();
        final int y = p.y();
        return x >= 0 && x < board.length 
                && y >= 0 && y < board.length;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isAdjacent(final Position p1, final Position p2) {
        checkValidPosition(p1);
        checkValidPosition(p2);

        int dx = Math.abs(p1.x() - p2.x());
        int dy = Math.abs(p1.y() - p2.y());
        return (dx <= 1) && (dy <= 1) && !(dx == 0 && dy == 0);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < getBoardSize(); i++) {
            for (int j = 0; j < getBoardSize(); j++) {
                Cell c = board[i][j];

                if (c.isFlagged()) {
                    sb.append("F "); 
                } else if (!c.isRevealed()) {
                    sb.append(". "); 
                } else if (c.hasContent()) {
                    sb.append(c.getContent()
                           .map(CellContent::shortString)
                           .orElse("?")).append(" ");
                } else {
                    sb.append(c.getAdjacentTraps()).append(" ");
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }


    private void checkValidCell(final Cell cell) {
        if (!cellPositions.containsKey(cell)) {
            throw new IllegalArgumentException(CELL_NOT_IN_BOARD_EXCEPTION + cell);
        }
    }

    private void checkValidIndex(final int index) {
        if (index < 0 || index >= board.length) {
            throw new IndexOutOfBoundsException(INDEX_EXCEPTION_MESSAGE + index);
        }
    }

    private void checkValidPosition(final Position p) {
        if (!isPositionValid(p)) {
            throw new IndexOutOfBoundsException(POSITION_EXCEPTION_MESSAGE + p);
        }
    }

}
