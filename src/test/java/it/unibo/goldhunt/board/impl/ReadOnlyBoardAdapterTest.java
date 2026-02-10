package it.unibo.goldhunt.board.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.goldhunt.board.api.Board;
import it.unibo.goldhunt.board.api.ReadOnlyBoard;
import it.unibo.goldhunt.engine.api.Position;

/**
 * This class tests {@link ReadOnlyBoardAdapter}.
 */
final class ReadOnlyBoardAdapterTest {

    private Board board;
    private ReadOnlyBoard adapter;

    @BeforeEach
    void init() {
        this.board = new TempBoard(3);
        this.adapter = new ReadOnlyBoardAdapter(board);
    }

    /**
     * Tests that {@link ReadOnlyBoardAdapter#ReadOnlyBoardAdapter(Board)}
     * throws {@link NullPointerException} if {@code board} is {@code null}.
     */
    @Test
    void testConstructorThrowsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ReadOnlyBoardAdapter(null));
    }

    /**
     * Tests that {@link ReadOnlyBoardAdapter#boardSize()}
     * delegates to the wrapped board.
     */
    @Test
    void testBoardSizeDelegatesBoardSize() {
        assertEquals(3, adapter.boardSize());
    }

    /**
     * Tests that {@link ReadOnlyBoardAdapter#cellAt(Position)}
     * returns a read-only view reflecting the underlying cell state.
     */
    @Test
    void testCellAtReturnsReadOnlyCell() {
        final Position p = new Position(1, 1);
        board.getCell(p).toggleFlag();
        assertTrue(adapter.cellAt(p).isFlagged());
        board.getCell(p).toggleFlag();
        board.getCell(p).reveal();
        assertTrue(adapter.cellAt(p).isRevealed());
    }
    
}
