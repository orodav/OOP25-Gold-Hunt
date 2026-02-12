package it.unibo.goldhunt.board.impl;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.goldhunt.board.api.Board;
import it.unibo.goldhunt.board.api.Cell;
import it.unibo.goldhunt.board.api.RevealStrategy;
import it.unibo.goldhunt.engine.api.Position;

/**
 * This class tests {@link SimpleReveal}.
 */
final class SimpleRevealTest {

    private Board board;
    private RevealStrategy strategy;

    @BeforeEach
    void init() {
        this.board = new SquareBoardFactory(new BaseCellFactory()).createEmptyBoard(3);
        this.strategy = new SimpleReveal();
    }

    /**
     * Tests that {@link SimpleReveal#reveal(Board, Position)} reveals the target cell correctly.
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
     * Tests that {@link RevealStrategy#reveal(Board, Position)}
     * throws {@link NullPointerException} correctly.
     */
    @Test
    void testRevealThrowsNullPointerException() {
        final Position p = new Position(0, 0);
        assertThrows(NullPointerException.class, () -> strategy.reveal(null, p));
        assertThrows(NullPointerException.class, () -> strategy.reveal(board, null));
    }

    /**
     * Tests that {@link RevealStrategy#reveal(Board, Position)}
     * throws {@link IndexOutOfBoundsException} correctly. 
     */
    @Test
    void testRevealThrowsIndexOutOfBoundsException() {
        assertThrows(IndexOutOfBoundsException.class, () -> strategy.reveal(board, new Position(-1, 0)));
        assertThrows(IndexOutOfBoundsException.class, () -> strategy.reveal(board, new Position(0, 3)));
    }

}
