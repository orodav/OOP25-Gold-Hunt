package it.unibo.goldhunt.items.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.goldhunt.engine.api.Position;
import it.unibo.goldhunt.items.api.ItemContext;
import it.unibo.goldhunt.items.api.ItemTypes;
import it.unibo.goldhunt.player.api.Inventory;
import it.unibo.goldhunt.player.api.PlayerOperations;

/**
 * Test class for {@link LuckyClover} item.
 * Verifies that the lucky clover correctly adds itself to the inventory.
 */
class LuckyCloverTest {

    private LuckyClover clover;
    private InventoryFake inventory;


    /**
     * Sets up the test environment with fake player, inventory, and gold items.
     */
    @BeforeEach
    void init() {
        final Pyrite gold = new Pyrite();
        final PyriteX3 goldX3 = new PyriteX3();
        inventory = new InventoryFake();
        final var playerop = new FakePlayer(inventory);
        clover = new LuckyClover();
        gold.bind(new ItemContext(null, playerop, inventory));
        goldX3.bind(new ItemContext(null, playerop, inventory));
        clover.bind(new ItemContext(null, playerop, inventory));
    }

    /**
     * Tests that applying the clover effect correctly updates the inventory.
     */
    @Test
    void applyEffect() {
        final PlayerOperations playerop = new FakePlayer(inventory);
        inventory.hasEnough = true;
        final PlayerOperations used = clover.applyEffect(playerop);

        assertNotNull(used);
        assertEquals(LuckyClover.MAX_QUANTITY_CLOVER, inventory.added, "should be in inventory");
    }

    /**
     * Fake Inventory implementation to track added items.
     */
    private static final class InventoryFake implements Inventory {
        private int added;
        private boolean hasEnough;

        @Override
        public Inventory add(final ItemTypes item, final int quantity) {
            added += quantity;
            return this;
        }

        @Override
        public Inventory remove(final ItemTypes item, final int quantity) {
            throw new UnsupportedOperationException("Unimplemented method 'remove'");
        }

        @Override
        public int quantity(final ItemTypes item) {
            if (hasEnough) {
                return LuckyClover.MAX_QUANTITY_CLOVER;
            } else {
                return 0;
            }
        }
    } 

    /**
     * Mock version of gold for testing clover interactions.
     */
    private static final class Pyrite extends Gold {

        @Override
        public PlayerOperations applyEffect(final PlayerOperations playerop) {
            return playerop;
        }

        @Override public String getName() {
            return "Gold"; 
        }

        @Override public String shortString() { 
            return "G"; 
        }
    }

    /**
     * Mock version of triple gold for testing clover interactions.
     */
    private static final class PyriteX3 extends GoldX3 {

        @Override
        public PlayerOperations applyEffect(final PlayerOperations playerop) {
            return playerop;
        }

        @Override public String getName() {
            return "GoldX3"; 
        }

        @Override public String shortString() { 
            return "G3"; 
        }
    }

    /**
     * Fake PlayerOperations implementation to simulate item collection.
     */
    static final class FakePlayer implements PlayerOperations {
        private final Inventory inventory;

        FakePlayer(final Inventory inventory) {
            this.inventory = inventory;
        }

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
            return inventory;
        }

        @Override
        public PlayerOperations withInventory(final Inventory pInventory) {
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
            if (inventory != null) {
                inventory.add(item, quantity);
            }
            return this;
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
}
