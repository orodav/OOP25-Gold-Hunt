// AZZU

package it.unibo.goldhunt.board.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.goldhunt.board.api.Board;
import it.unibo.goldhunt.board.api.Cell;
import it.unibo.goldhunt.board.api.CellFactory;
import it.unibo.goldhunt.engine.api.Position;

/**
 * This class tests {@link SquareBoard}.
 */
final class SquareBoardTest {

    private static final int ADJACENT_CELLS_ON_EDGE = 5;
    private static final int OUT_OF_BOUNDS = 9;

    private Board board;
    private CellFactory factory;
    private Cell[][] cells;

    @BeforeEach
    void init() {
        this.factory = new BaseCellFactory();
        this.board = new SquareBoardFactory(factory).createEmptyBoard(3);
        this.cells = new Cell[3][3];
        cellsSnapshot();
    }

    /**
     * Tests that SquareBoard.getBoardCells() returns 
     * a list with all of the board's cells.
     */
    @Test
    void testGetBoardCells() {
        final List<Cell> list = this.board.getBoardCells();
        assertEquals(OUT_OF_BOUNDS, list.size());
        assertTrue(list.containsAll(List.of(
            cells[0][0], cells[0][1], cells[0][2],
            cells[1][0], cells[1][1], cells[1][2],
            cells[2][0], cells[2][1], cells[2][2])));
    }

    /**
     * Tests that getCell() return the right cell.
     */
    @Test
    void testGetCellReturnsRightCells() {
        assertSame(cells[0][0], board.getCell(new Position(0, 0)));
        assertSame(cells[1][1], board.getCell(new Position(1, 1)));
        assertSame(cells[2][2], board.getCell(new Position(2, 2)));
    }

    /**
     * Tests that getCell() throws NullPointerException
     * and IndexOutOfBoundsException correctly.
     */
    @Test
    void testGetCellThrowsRightExceptions() {
        assertThrows(NullPointerException.class, () -> board.getCell(null));
        assertThrows(IndexOutOfBoundsException.class, () -> board.getCell(new Position(-1, 0)));
        assertThrows(IndexOutOfBoundsException.class, () -> board.getCell(new Position(0, 3)));
    }

    /**
     * Tests that getCellPosition() returns the right position.
     */
    @Test
    void testGetCellPositionReturnsRightPositions() {
        assertEquals(new Position(0, 0), board.getCellPosition(cells[0][0]));
        assertEquals(new Position(1, 1), board.getCellPosition(cells[1][1]));
        assertEquals(new Position(2, 2), board.getCellPosition(cells[2][2]));
    }

    /**
     * Tests that getCellPosition() throws NullPointerException
     * and IndexOutOfBoundsException correctly. 
     */
    @Test
    void testGetCellPositionThrowsRightExceptions() {
        assertThrows(NullPointerException.class, () -> board.getCellPosition(null));
        assertThrows(IllegalArgumentException.class, () -> board.getCellPosition(factory.createCell()));
    }

    /**
     * Tests that a cell in the corner of the board
     * has 3 adjacent cells.
     */
    @Test
    void testGetAdjacentCellsOnCornerIsThree() {
        final List<Cell> list = board.getAdjacentCells(new Position(0, 0));
        assertEquals(3, list.size());
        assertTrue(list.containsAll(List.of(cells[0][1], cells[1][0], cells[1][1])));
    }

    /**
     * Tests that a cell on the edge of the board
     * has 5 adjacent cells.
     */
    @Test
    void testGetAdjacentCellsOnEdgeIsFive() {
        final List<Cell> list = board.getAdjacentCells(new Position(1, 0));
        assertEquals(ADJACENT_CELLS_ON_EDGE, list.size());
        assertTrue(list.containsAll(List.of(
            cells[0][0], cells[0][1], cells[1][1],
            cells[2][0], cells[2][1])));
    }

    /**
     * Tests that a cell in the middle of the board
     * has 8 adjacent cells.
     */
    @Test 
    void testGetAdjacentCellsInCenterIsEight() {
        final List<Cell> list = board.getAdjacentCells(new Position(1, 1));
        assertEquals(8, list.size());
        assertTrue(list.containsAll(List.of(
            cells[0][0], cells[0][1], cells[0][2],
            cells[1][0], cells[1][2],
            cells[2][0], cells[2][1], cells[2][2])));
    }

    /**
     * Tests that getAdjacentCells() throws
     * NullPointerException and IndexOutOfBoundsException correctly.
     */
    @Test
    void testGetAdjacentCellsThrowsRightExceptions() {
        assertThrows(NullPointerException.class, () -> board.getAdjacentCells(null));
        assertThrows(IndexOutOfBoundsException.class, () -> board.getAdjacentCells(new Position(-1, 0)));
        assertThrows(IndexOutOfBoundsException.class, () -> board.getAdjacentCells(new Position(0, 3)));
    }

    /**
     * Tests that getBoardSize() return the right board size.
     */
    @Test
    void testGetBoardSize() {
        assertEquals(3, board.getBoardSize());
    }

    /**
     * Tests that getRow() returns the right board row.
     */
    @Test
    void testGetRowReturnsRightRow() {
        final List<Cell> row = board.getRow(1);
        assertEquals(3, row.size());
        assertTrue(row.containsAll(List.of(cells[1][0], cells[1][1], cells[1][2])));
    }

    /**
     * Tests that getRow() throws IndexOutOfBoundsException correctly.
     */
    @Test
    void testGetRowThrowsIndexOutOfBoundsException() {
        assertThrows(IndexOutOfBoundsException.class, () -> board.getRow(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> board.getRow(3));
    }

    /**
     * Tests that getColumn() returns the right board column.
     */
    @Test
    void testGetColumnReturnsRightColumn() {
        final List<Cell> col = board.getColumn(1);
        assertEquals(3, col.size());
        assertTrue(col.containsAll(List.of(cells[0][1], cells[1][1], cells[2][1])));
    }

    /**
     * Tests that getColumn() throws IndexOutOfBoundsException correctly.
     */
    @Test
    void testGetColumnThrowsIndexOutOfBoundsException() {
        assertThrows(IndexOutOfBoundsException.class, () -> board.getColumn(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> board.getColumn(3));
    }

    /**
     * Tests that isPositionValid() returns the right results.
     */
    @Test
    void testIsPositionValidReturnsRightResults() {
        assertTrue(board.isPositionValid(new Position(0, 0)));
        assertTrue(board.isPositionValid(new Position(1, 1)));
        assertTrue(board.isPositionValid(new Position(2, 2)));
        assertFalse(board.isPositionValid(new Position(-1, 0)));
        assertFalse(board.isPositionValid(new Position(0, 3)));
    }

    /**
     * Tests that isPositionValid() throws NullPointerException correctly.
     */
    @Test
    void testIsPositionValidThrowsNullPointerException() {
        assertThrows(NullPointerException.class, () -> board.isPositionValid(null));
    }

    /**
     * Tests that isAdjacent() returns the right results.
     */
    @Test
    void testIsAdjacentReturnsRightResults() {
        assertTrue(board.isAdjacent(new Position(0, 0), new Position(0, 1)));
        assertTrue(board.isAdjacent(new Position(0, 0), new Position(1, 0)));
        assertTrue(board.isAdjacent(new Position(0, 0), new Position(1, 1)));
        assertFalse(board.isAdjacent(new Position(2, 0), new Position(2, 2)));
        assertFalse(board.isAdjacent(new Position(0, 1), new Position(2, 1)));
        assertFalse(board.isAdjacent(new Position(0, 2), new Position(2, 0)));
    }

    /**
     * Tests that isAdjacent() throws NullPointerException correctly.
     */
    @Test
    void testIsAdjacentThrowsNullPointerException() {
        final Position p1 = new Position(0, 0);
        final Position p2 = new Position(1, 1);
        assertThrows(NullPointerException.class, () -> board.isAdjacent(null, p2));
        assertThrows(NullPointerException.class, () -> board.isAdjacent(p1, null));
    }

    /**
     * Tests that isAdjacent() throws IndexOutOfBoundsException correctly.
     */
    @Test
    void testIsAdjacentThrowsIndexOutOfBoundsException() {
        final Position negativeP = new Position(-1, 0);
        final Position greaterP = new Position(0, 3);
        final Position correctP = new Position(0, 0);
        assertThrows(IndexOutOfBoundsException.class, () -> board.isAdjacent(negativeP, correctP));
        assertThrows(IndexOutOfBoundsException.class, () -> board.isAdjacent(greaterP, correctP));
        assertThrows(IndexOutOfBoundsException.class, () -> board.isAdjacent(correctP, negativeP));
        assertThrows(IndexOutOfBoundsException.class, () -> board.isAdjacent(correctP, greaterP));
    }

    private void cellsSnapshot() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                cells[i][j] = board.getCell(new Position(i, j));
            }
        }
    }

}
