// AZZU

package it.unibo.goldhunt.board.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.goldhunt.board.api.Board;
import it.unibo.goldhunt.board.api.CellFactory;

/**
 * This class tests {@link SquareBoardFactory}.
 */
public class SquareBoardFactoryTest {

    private CellFactory cellFactory;
    private SquareBoardFactory boardFactory;

    @BeforeEach
    void init() {
        this.cellFactory = new BaseCellFactory();
        this.boardFactory = new SquareBoardFactory(this.cellFactory);
    }

    /**
     * Tests that {@link SquareBoardFactory#SquareBoardFactory(CellFactory)}
     * throws {@link NullPointerException} correctly.
     */
    @Test
    void testConstructorThrowsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new SquareBoardFactory(null));
    }

    /**
     * Tests that {@link SquareBoardFactory#createEmptyBoard(int)}
     * creates a board with the correct size.
     */
    @Test
    void testCreateEmptyBoardCreatesBoardOfCorrectSize() {
        final Board board = boardFactory.createEmptyBoard(3);
        assertEquals(3, board.getBoardSize());
        assertEquals(9, board.getBoardCells().size());
    }

    /**
     * Tests that {@link SquareBoardFactory#createEmptyBoard(int)}
     * does not create a board size equal or less than 0.
     */
    @Test
    void testCreateEmptyBoardThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> boardFactory.createEmptyBoard(-1));
        assertThrows(IllegalArgumentException.class, () -> boardFactory.createEmptyBoard(0));
    }

}
