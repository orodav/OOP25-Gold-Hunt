// AZZU

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

public final class BaseCellTest {
    
    private CellFactory factory;
    private Cell cell;

    @BeforeEach
    void init() {
        this.factory = new BaseCellFactory();
        this.cell = this.factory.createCell();
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
    void testSetAdjacentTraps() {
        cell.setAdjacentTraps(0);
        assertEquals(0, cell.getAdjacentTraps());
        cell.setAdjacentTraps(1);
        assertEquals(1, cell.getAdjacentTraps());
        cell.setAdjacentTraps(8);
        assertEquals(8, cell.getAdjacentTraps());
        assertThrows(IllegalArgumentException.class, () -> cell.setAdjacentTraps(-1));
        assertThrows(IllegalArgumentException.class, () -> cell.setAdjacentTraps(9));
    }

    /**
     * Tests that setContent() does not change a cell's existing content.
     */
    @Test
    void testSetContent() {
        final CellContent content1 = new TempCellContent();
        cell.setContent(content1);
        assertTrue(cell.hasContent());
        assertTrue(cell.getContent().isPresent());
        assertEquals(content1, cell.getContent().get());
        final CellContent content2 = new TempCellContent();
        assertThrows(IllegalStateException.class, () -> cell.setContent(content2));
    }

    /**
     * Tests the removal of a cell's content.
     */
    @Test
    void testRemoveContent() {
        final CellContent content = new TempCellContent();
        cell.setContent(content);
        cell.removeContent();
        assertFalse(cell.hasContent());
        assertEquals(Optional.empty(), cell.getContent());
        cell.removeContent();
        assertFalse(cell.hasContent());
        assertEquals(Optional.empty(), cell.getContent());
    }

    private static final class TempCellContent implements CellContent {
        
        @Override
        public boolean applyEffect() { return true; }

        @Override
        public String shortString() { return "For testing only"; }
    }

}
