package it.unibo.goldhunt.board.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.goldhunt.board.api.Cell;
import it.unibo.goldhunt.board.api.CellFactory;
import it.unibo.goldhunt.items.api.CellContent;

/**
 * This class tests {@link BaseCellFactory}.
 */
final class BaseCellFactoryTest {

    private CellFactory factory;

    @BeforeEach
    void init() {
        this.factory = new BaseCellFactory();
    }

    /**
     * Tests the creation of an empty cell.
     */
    @Test
    void testCreateCellWithNoParameters() {
        final Cell cell = this.factory.createCell();
        assertFalse(cell.isRevealed());
        assertFalse(cell.isFlagged());
        assertEquals(0, cell.getAdjacentTraps());
        assertFalse(cell.hasContent());
        assertEquals(Optional.empty(), cell.getContent());
    }

    /**
     * Test the creation of a cell with content.
     */
    @Test
    void testCreateCellWithParameters() {

        final Cell cell2 = this.factory.createCell(Optional.empty());
        assertFalse(cell2.isRevealed());
        assertFalse(cell2.isFlagged());
        assertEquals(0, cell2.getAdjacentTraps());
        assertFalse(cell2.hasContent());
        assertEquals(Optional.empty(), cell2.getContent());

        final CellContent content = new TempCellContent();
        final Cell cell3 = this.factory.createCell(Optional.of(content));
        assertFalse(cell3.isRevealed());
        assertFalse(cell3.isFlagged());
        assertEquals(0, cell3.getAdjacentTraps());
        assertTrue(cell3.hasContent());
        assertEquals(Optional.of(content), cell3.getContent());
    }
 
}
