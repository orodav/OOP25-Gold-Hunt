package it.unibo.goldhunt.board.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.goldhunt.board.api.Cell;
import it.unibo.goldhunt.board.api.CellFactory;
import it.unibo.goldhunt.items.api.CellContent;

/**
 * This class tests {@link BaseCell}.
 */
final class BaseCellTest {

    private static final int OUT_OF_BOUNDS = 9;

    private Cell cell;

    @BeforeEach
    void init() {
        final CellFactory factory = new BaseCellFactory();
        this.cell = factory.createCell();
    }

    /**
     * Tests that a cell is revealed when it is not flagged.
     */
    @Test
    void testRevealWhenNotFlagged() {
        cell.reveal();
        assertTrue(cell.isRevealed());
        assertFalse(cell.isFlagged());
    }

    /**
     * Tests that a revealed cell remains revealed if revealed more than once.
     */
    @Test
    void testRevealWhenAlreadyRevealed() {
        cell.reveal();
        cell.reveal();
        assertTrue(cell.isRevealed());
    }

    /**
     * Tests that a flagged cell cannot be revealed.
     */
    @Test
    void testRevealWhenFlagged() {
        cell.toggleFlag();
        cell.reveal();
        assertFalse(cell.isRevealed());
        assertTrue(cell.isFlagged());
    }

    /**
     * Tests that a non-revealed and unflagged cell can be flagged.
     */
    @Test
    void testToggleFlagWhenNotFlagged() {
        cell.toggleFlag();
        assertFalse(cell.isRevealed());
        assertTrue(cell.isFlagged());
    }

    /**
     * Tests that a flagged cell can be unflagged.
     */
    @Test
    void testToggleFlagWhenFlagged() {
        cell.toggleFlag();
        cell.toggleFlag();
        assertFalse(cell.isRevealed());
        assertFalse(cell.isFlagged());
    }

    /**
     * Tests that a revealed cell cannot be flagged.
     */
    @Test
    void testToggleFlagWhenRevealed() {
        cell.reveal();
        cell.toggleFlag();
        assertFalse(cell.isFlagged());
    }

    /**
     * Tests that the number of adjacent cells is between 0 and 8 inclusive.
     */
    @Test
    void testSetAdjacentTrapsSetsTrapsCorrectly() {
        cell.setAdjacentTraps(0);
        assertEquals(0, cell.getAdjacentTraps());
        cell.setAdjacentTraps(1);
        assertEquals(1, cell.getAdjacentTraps());
        cell.setAdjacentTraps(8);
        assertEquals(8, cell.getAdjacentTraps());
    }

    /**
     * Tests that {@link BaseCell#setAdjacentTraps(int)} throws {@link IllegalArgumentException}
     * when a negative number or a number greater than 8 is passed.
     */
    @Test
    void testSetAdjacentTrapsThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> cell.setAdjacentTraps(-1));
        assertThrows(IllegalArgumentException.class, () -> cell.setAdjacentTraps(OUT_OF_BOUNDS));
    }


    /**
     * Tests that {@link BaseCell#setContent(CellContent)} adds content to a cell correctly.
     */
    @Test
    void testSetContentSetsContent() {
        final CellContent content = new TempCellContent();
        cell.setContent(content);
        assertTrue(cell.hasContent());
        assertTrue(cell.getContent().isPresent());
        assertEquals(content, cell.getContent().get());
    }

    /**
     * Test that {@link BaseCell#setContent(CellContent)} adds content to a cell
     * after its initial content has been removed.
     */
    @Test
    void testSetContentAfterRemoveContent() {
        final CellContent content = new TempCellContent();
        cell.setContent(content);
        cell.removeContent();
        cell.setContent(content);
        assertTrue(cell.hasContent());
        assertTrue(cell.getContent().isPresent());
        assertEquals(content, cell.getContent().get());
    }

    /**
     * Tests that {@link BaseCell#setContent(CellContent)} does not change a cell's existing content.
     */
    @Test
    void testSetContentThrowsIllegalStateExceptionIfAlreadyHasContent() {
        final CellContent content1 = new TempCellContent();
        cell.setContent(content1);
        final CellContent content2 = new TempCellContent();
        assertThrows(IllegalStateException.class, () -> cell.setContent(content2));
    }

    /**
     * Tests that {@link BaseCell#removeContent()} removes a cell's content correctly.
     */
    @Test
    void testRemoveContent() {
        final CellContent content = new TempCellContent();
        cell.setContent(content);
        cell.removeContent();
        assertFalse(cell.hasContent());
        assertEquals(Optional.empty(), cell.getContent());
    }

    /**
     * Tests that {@link BaseCell#removeContent()} works correctly when used
     * on cells with no content.
     */
    @Test
    void testRemoveContentWithNoContent() {
        cell.removeContent();
        assertFalse(cell.hasContent());
        assertEquals(Optional.empty(), cell.getContent());
    }

}
