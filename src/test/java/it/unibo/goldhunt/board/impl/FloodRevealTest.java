// AZZU

package it.unibo.goldhunt.board.impl;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.goldhunt.board.api.Board;
import it.unibo.goldhunt.board.api.Cell;
import it.unibo.goldhunt.board.api.CellFactory;
import it.unibo.goldhunt.board.api.RevealStrategy;
import it.unibo.goldhunt.engine.api.Position;

/**
 * This class tests {@link FloodReveal}.
 */
final class FloodRevealTest {

    private Board board;
    private CellFactory factory;
    private Cell[][] cells;
    private RevealStrategy strategy;

    @BeforeEach
    void init() {
        this.factory = new BaseCellFactory();
        this.board = new SquareBoardFactory(factory).createEmptyBoard(3);
        this.cells = new Cell[3][3];
        cellsSnapshot();
        cells[0][0].setAdjacentTraps(1);
        cells[0][1].setAdjacentTraps(1);
        cells[0][2].setAdjacentTraps(0);
        cells[1][0].setAdjacentTraps(0);
        cells[1][1].setAdjacentTraps(1);
        cells[1][2].setAdjacentTraps(0);
        cells[2][0].setAdjacentTraps(1);
        cells[2][1].setAdjacentTraps(1);
        cells[2][2].setAdjacentTraps(0);
        this.strategy = new FloodReveal();
    }

    /**
     * Tests that reveal() does not reveal anything if used on a revealed cell.
     */
    @Test
    void testRevealOnRevealedCell() {
        cells[0][2].reveal();
        strategy.reveal(board, new Position(0, 2));
        assertTrue(cells[0][2].isRevealed());
        assertFalse(cells[0][1].isRevealed());
        assertFalse(cells[1][1].isRevealed());
        assertFalse(cells[1][2].isRevealed());
    }

    /**
     * Tests that reveal() does not reveal anything if used on a flagged cell.
     */
    @Test
    void testRevealOnFlaggedCell() {
        cells[0][2].toggleFlag();
        strategy.reveal(board, new Position(0, 2));
        assertTrue(cells[0][2].isFlagged());
        assertFalse(cells[0][2].isRevealed());
        assertFalse(cells[0][1].isRevealed());
        assertFalse(cells[1][1].isRevealed());
        assertFalse(cells[1][2].isRevealed());
    }

    /**
     * Test that reveal() does not reveal all adjacent cells 
     * if the revealed cell's adjacent traps is not zero.
     */
    @Test
    void testRevealDoesNotFloodWhenAdjacentTrapsIsNotZero() {
        strategy.reveal(board, new Position(0, 1));
        assertTrue(cells[0][1].isRevealed());
        assertFalse(cells[0][0].isRevealed());
        assertFalse(cells[0][2].isRevealed());
        assertFalse(cells[1][0].isRevealed());
        assertFalse(cells[1][1].isRevealed());
        assertFalse(cells[1][2].isRevealed());
    }

    /**
     * Tests that reveal() reveals all adjacent cells
     * with no adjacent traps if the revealed cell has 
     * no adjacent traps.
     */
    @Test
    void testRevealFloodsWhenAdjacentTrapsIsZero() {
        strategy.reveal(board, new Position(0, 2));
        for (int i = 0; i <= 2; i++) {
            for (int j = 1; j <= 2; j++) {
                assertTrue(cells[i][j].isRevealed());
            }
        }
    }

    /**
     * Tests that a flagged cell is not revealed
     * when it is checked by the flood reveal strategy.
     */
    @Test
    void testRevealDoesNotRevealFlaggedCellInFlood() {
        cells[1][2].toggleFlag();
        strategy.reveal(board, new Position(0, 2));
        assertTrue(cells[0][1].isRevealed());
        assertTrue(cells[0][2].isRevealed());
        assertTrue(cells[1][1].isRevealed());
        assertTrue(cells[1][2].isFlagged());
        assertFalse(cells[1][2].isRevealed());
        assertFalse(cells[2][1].isRevealed());
        assertFalse(cells[2][2].isRevealed());
    }

    /**
     * Tests that reveal() throws NullPointerException correctly.
     */
    @Test
    void testRevealThrowsNullPointerException() {
        final Position p = new Position(0, 0);
        assertThrows(NullPointerException.class, () -> strategy.reveal(null, p));
        assertThrows(NullPointerException.class, () -> strategy.reveal(board, null));
    }

    /**
     * Tests that reveal() throws IndexOutOfBoundsException correctly. 
     */
    @Test
    void testRevealThrowsIndexOutOfBoundsException() {
        assertThrows(IndexOutOfBoundsException.class, () -> strategy.reveal(board, new Position(-1, 0)));
        assertThrows(IndexOutOfBoundsException.class, () -> strategy.reveal(board, new Position(0, 3)));
    }

    private void cellsSnapshot() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                cells[i][j] = board.getCell(new Position(i, j));
            }
        }
    }

}
