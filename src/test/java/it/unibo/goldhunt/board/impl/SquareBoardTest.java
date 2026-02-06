// AZZU

package it.unibo.goldhunt.board.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

public final class SquareBoardTest {

    private Board board;
    private CellFactory factory;
    private Cell[][] cells;

    @BeforeEach
    void init() {
        this.board = SquareBoard.create(3);
        this.factory = new BaseCellFactory();
        this.cells = new Cell[3][3];
        fillBoard();
    }

    /**
     * Tests that SquareBoard.getBoardCells() returns 
     * a list with all of the board's cells.
     */
    @Test
    void testGetBoardCells() {
        List<Cell> list = this.board.getBoardCells();
        assertEquals(9, list.size());
        assertTrue(list.containsAll(List.of(
            cells[0][0], cells[0][1], cells[0][2],
            cells[1][0], cells[1][1], cells[1][2],
            cells[2][0], cells[2][1], cells[2][2] )));
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

    private void fillBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                final Cell c = factory.createCell();
                this.board.setCell(c, new Position(i, j));
                cells[i][j] = c;
            }
        }
    }

}
