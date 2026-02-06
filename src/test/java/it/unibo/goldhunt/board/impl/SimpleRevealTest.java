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
 * This class tests SimpleReveal.
 */
public final class SimpleRevealTest {

    private Board board;
    private CellFactory factory;
    private RevealStrategy strategy;

    @BeforeEach
    void init() {
        this.board = SquareBoard.create(3);
        this.factory = new BaseCellFactory();
        this.strategy = new SimpleReveal();
        fillBoard();
    }

    /**
     * Tests that reveal() reveals the target cell correctly.
     */
    @Test
    void testRevealRevealsRightCell() {
        final Position p = new Position(1, 1);
        final Cell cell = board.getCell(p);
        assertFalse(cell.isRevealed());
        strategy.reveal(board, p);
        assertTrue(cell.isRevealed());
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

    private void fillBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                final Cell c = factory.createCell();
                this.board.setCell(c, new Position(i, j));
            }
        }
    }

}
