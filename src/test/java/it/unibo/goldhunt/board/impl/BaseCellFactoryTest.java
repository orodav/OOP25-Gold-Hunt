// AZZU

package it.unibo.goldhunt.board.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.goldhunt.board.api.Cell;

public class BaseCellFactoryTest {

    private BaseCellFactory factory;

    @BeforeEach
    void init() {
        this.factory = new BaseCellFactory();
    }

    /**
     * Tests the creation of an empty cell
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


}
