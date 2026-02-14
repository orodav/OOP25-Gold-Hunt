package it.unibo.goldhunt.items.impl;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.goldhunt.board.api.Board;
import it.unibo.goldhunt.board.api.Cell;
import it.unibo.goldhunt.engine.api.Position;
import it.unibo.goldhunt.items.api.ItemTypes;
import it.unibo.goldhunt.player.api.Inventory;
import it.unibo.goldhunt.player.api.PlayerOperations;

/**
 * Test suite for ItemFactoryImpl.
 * Ensures that items are created correctly based on string keys and that 
 * their context is properly bound.
 */
class ItemFactoryImplTest {
    private static final String CHART_ID = "C";
    private static final String PICKAXE_ID = "P";
    private static final String MAP_ID = "M";
    private static final String DYNAMITE_ID = "D";
    private static final String GOLD_ID = "G";
    private static final String LIVES_ID = "L";
    private static final String SHIELD_ID = "S";
    private static final String GOLDX3_ID = "X";
    private ItemFactoryImpl itemFactoryImpl;

    @BeforeEach
    void init() {
        itemFactoryImpl = new ItemFactoryImpl();
    }

    @Test
    void testCorrectItem() {
        assertTrue(itemFactoryImpl.generateItem(MAP_ID) instanceof Chart);
        assertTrue(itemFactoryImpl.generateItem(DYNAMITE_ID) instanceof Dynamite);
        assertTrue(itemFactoryImpl.generateItem(GOLD_ID) instanceof Gold);
        assertTrue(itemFactoryImpl.generateItem(LIVES_ID) instanceof Lifes);
        assertTrue(itemFactoryImpl.generateItem(CHART_ID) instanceof LuckyClover);
        assertTrue(itemFactoryImpl.generateItem(PICKAXE_ID) instanceof Pickaxe);
        assertTrue(itemFactoryImpl.generateItem(SHIELD_ID) instanceof Shield);
        assertTrue(itemFactoryImpl.generateItem(GOLDX3_ID) instanceof GoldX3);
    }

    @Test
    void generateItems() {
        // Usa le costanti anche nell'array
        final String[] keys = {
            MAP_ID, DYNAMITE_ID, GOLD_ID, LIVES_ID, 
            CHART_ID, PICKAXE_ID, SHIELD_ID, GOLDX3_ID,
        };
        for (final String str : keys) {
            final AbstractItem item = itemFactoryImpl.generateItem(str);
            assertNotNull(item, "should return an item given the key");
        }
    }

    @Test
    void testObjectsAreDiff() {
        final AbstractItem firsItem = itemFactoryImpl.generateItem(PICKAXE_ID);
        final AbstractItem secondItem = itemFactoryImpl.generateItem(PICKAXE_ID);

        assertNotSame(firsItem, secondItem, "every call should return a different object");
    }

    @Test
    void testItemContextBound() {
        final Board board = new FakeBoard();
        final PlayerOperations player = new FakePlayer();
        final Inventory inventory = new FakeInventory();
        // Usa CHART_ID qui
        final AbstractItem item = itemFactoryImpl.generateItem(CHART_ID, board, player, inventory);

        assertNotNull(item);
        // Nota: Assicurati che 'context' sia accessibile o usa un getter
        assertNotNull(item.getContext(), "item should have a bound context");
    }

    /**
     * Empty mock of Board for context binding tests.
     */
    private static final class FakeBoard implements Board {

        @Override
        public List<Cell> getBoardCells() {
            throw new UnsupportedOperationException("Unimplemented method 'getBoardCells'");
        }

        @Override
        public Cell getCell(final Position p) {
            throw new UnsupportedOperationException("Unimplemented method 'getCell'");
        }

        @Override
        public Position getCellPosition(final Cell cell) {
            throw new UnsupportedOperationException("Unimplemented method 'getCellPosition'");
        }

        @Override
        public List<Cell> getAdjacentCells(final Position p) {
            throw new UnsupportedOperationException("Unimplemented method 'getAdjacentCells'");
        }

        @Override
        public int getBoardSize() {
            throw new UnsupportedOperationException("Unimplemented method 'getBoardSize'");
        }

        @Override
        public List<Cell> getRow(final int index) {
            throw new UnsupportedOperationException("Unimplemented method 'getRow'");
        }

        @Override
        public List<Cell> getColumn(final int index) {
            throw new UnsupportedOperationException("Unimplemented method 'getColumn'");
        }

        @Override
        public boolean isPositionValid(final Position p) {
            throw new UnsupportedOperationException("Unimplemented method 'isPositionValid'");
        }

        @Override
        public boolean isAdjacent(final Position p1, final Position p2) {
            throw new UnsupportedOperationException("Unimplemented method 'isAdjacent'");
        }
    }

    /**
     * Empty mock of PlayerOperations for context binding tests.
     */
    private static final class FakePlayer implements PlayerOperations {

        @Override
        public Position position() {
            throw new UnsupportedOperationException("Unimplemented method 'position'");
        }

        @Override
        public int livesCount() {
            throw new UnsupportedOperationException("Unimplemented method 'livesCount'");
        }

        @Override
        public int goldCount() {
            throw new UnsupportedOperationException("Unimplemented method 'goldCount'");
        }

        @Override
        public Inventory inventory() {
            throw new UnsupportedOperationException("Unimplemented method 'inventory'");
        }

        @Override
        public PlayerOperations withInventory(final Inventory inventory) {
            throw new UnsupportedOperationException("Unimplemented method 'withInventory'");
        }

        @Override
        public PlayerOperations moveTo(final Position p) {
            throw new UnsupportedOperationException("Unimplemented method 'moveTo'");
        }

        @Override
        public PlayerOperations addGold(final int num) {
            throw new UnsupportedOperationException("Unimplemented method 'addGold'");
        }

        @Override
        public PlayerOperations addLives(final int num) {
            throw new UnsupportedOperationException("Unimplemented method 'addLives'");
        }

        @Override
        public PlayerOperations addItem(final ItemTypes item, final int quantity) {
            throw new UnsupportedOperationException("Unimplemented method 'addItem'");
        }

        @Override
        public PlayerOperations useItem(final ItemTypes item, final int quantity) {
            throw new UnsupportedOperationException("Unimplemented method 'useItem'");
        }

        @Override
        public PlayerOperations setLives(final int num) {
            throw new UnsupportedOperationException("Unimplemented method 'setLives'");
        }
    }

    /**
     * Empty mock of Inventory for context binding tests.
     */
    private static final class FakeInventory implements Inventory {

        @Override
        public Inventory add(final ItemTypes item, final int quantity) {
            throw new UnsupportedOperationException("Unimplemented method 'add'");
        }

        @Override
        public Inventory remove(final ItemTypes item, final int quantity) {
            throw new UnsupportedOperationException("Unimplemented method 'remove'");
        }

        @Override
        public int quantity(final ItemTypes item) {
            throw new UnsupportedOperationException("Unimplemented method 'quantity'");
        }
    }
}
