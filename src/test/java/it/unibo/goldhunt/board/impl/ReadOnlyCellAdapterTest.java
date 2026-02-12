package it.unibo.goldhunt.board.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.goldhunt.board.api.Cell;
import it.unibo.goldhunt.board.api.ReadOnlyCell;

/**
 * This class tests {@link ReadOnlyCellAdapter}.
 */
final class ReadOnlyCellAdapterTest {

    private Cell cell;
    private ReadOnlyCell adapter;

    @BeforeEach
    void init() {
        this.cell = new TempCell();
        this.adapter = new ReadOnlyCellAdapter(cell);
    }

    /**
     * Tests that {@link ReadOnlyCellAdapter#ReadOnlyCellAdapter(Cell)}'s constructor
     * throws {@link NullPointerException} if {@code cell} is {@code null}.
     */
    @Test
    void testConstructorThrowsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ReadOnlyCellAdapter(null));
    }

    /**
     * Tests that {@link ReadOnlyCellAdapter#isRevealed()}
     * delegates to the wrapped cell.
     */
    @Test
    void testIsRevealedDelegatesRevealed() {
        assertFalse(adapter.isRevealed());
        cell.reveal();
        assertTrue(adapter.isRevealed());
    }

    /**
     * Tests that {@link ReadOnlyCellAdapter#isFlagged()}
     * delegates to the wrapped cell.
     */
    @Test
    void testIsFlaggedDelegatesFlagged() {
        assertFalse(adapter.isFlagged());
        cell.toggleFlag();
        assertTrue(adapter.isFlagged());
    }

    /**
     * Tests that {@link ReadOnlyCellAdapter#getAdjacentTraps()}
     * delegates to the wrapped cell.
     */
    @Test
    void testGetAdjacentTrapsDelegatesAdjacentTraps() {
        assertEquals(0, adapter.getAdjacentTraps());
        cell.setAdjacentTraps(8);
        assertEquals(8, adapter.getAdjacentTraps());
    }

    /**
     * Tests that {@link ReadOnlyCellAdapter#contentID()}
     * returns an empty {@link Optional} when the cell is
     * not revealed, no matter its content.
     */
    @Test
    void testContentIDIsHiddenIfCellIsNotRevealed() {
        cell.setContent(new TempCellContent());
        assertTrue(adapter.contentID().isEmpty());
    }

    /**
     * Tests that {@link ReadOnlyCellAdapter#contentID()}
     * returns an empty {@link Optional} when the cell is empty.
     */
    @Test
    void testContentIDIsEmptyIfCellIsRevealedAndEmpty() {
        cell.reveal();
        assertTrue(adapter.contentID().isEmpty());
    }

    /**
     * Tests that {@link ReadOnlyCellAdapter#contentID()}
     * exposes the content identifier when it is revealed
     * and it has content.
     */
    @Test
    void testContentIDIsExposedIfCellIsRevealedAndHasContent() {
        cell.setContent(new TempCellContent());
        cell.reveal();
        assertTrue(adapter.contentID().isPresent());
        assertEquals("For testing only", adapter.contentID().get());
    }

}
