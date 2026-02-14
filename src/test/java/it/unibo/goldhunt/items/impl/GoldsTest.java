package it.unibo.goldhunt.items.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Objects;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.goldhunt.engine.api.Position;
import it.unibo.goldhunt.items.api.ItemContext;
import it.unibo.goldhunt.items.api.ItemTypes;
import it.unibo.goldhunt.items.api.KindOfItem;
import it.unibo.goldhunt.player.api.Inventory;
import it.unibo.goldhunt.player.api.PlayerOperations;

/**
 * Test suite for Gold items (Gold and GoldX3).
 * Checks if gold is added correctly and if the Lucky Clover bonus works.
 */
class GoldsTest {

    private Gold gold;
    private GoldX3 goldX3;
    private FakeInventory inventory;
    private FakePlayer player;

    /**
     * Initializes the player, inventory, and gold items before each test.
     */
    @BeforeEach
    void init() {
        inventory = new FakeInventory();
        player = new FakePlayer(inventory);
        goldX3 = new GoldX3();
        gold = new Gold();
        final ItemContext context = new ItemContext(null, player, inventory);
        Objects.requireNonNull(goldX3);
        Objects.requireNonNull(inventory);
        Objects.requireNonNull(gold);
        Objects.requireNonNull(player);
        gold.bind(context);
        goldX3.bind(context);
    }

    /**
     * Verifies that GoldX3 adds the correct tripled amount of gold.
     */
    @Test
    void effectAppliedGoldX3() {
        final PlayerOperations applied = goldX3.applyEffect(player);

        assertTrue(applied != null, "gold x3 effect should return true");
        assertEquals(GoldX3.ADDED_GOLDX3, inventory.added, "should return the correct amount");
    }

    /**
     * Verifies that a standard Gold item adds the correct base amount of gold.
     */
    @Test
    void effectAppliedGold() {
        final PlayerOperations applied = gold.applyEffect(player);

        assertTrue(applied != null, "gold effect should return true");
        assertEquals(Gold.ADDED_GOLD, inventory.added, "should return the correct amount");
    }

    /**
     * Tests if the Lucky Clover correctly doubles the gold received from a normal Gold item.
     */
    @Test
    void effectAppliedGoldBonus() {
        inventory.setClover(false);
        gold.applyEffect(player);
        assertEquals(Gold.ADDED_GOLD, inventory.added,
            "if the lucky clover is not in the inventory the gold should not be doubled");

        inventory.added = 0;

        inventory.hasClover = true;
        gold.applyEffect(player);

        final int expected = Gold.ADDED_GOLD * 2;
        assertEquals(expected, inventory.added,
             "gold with lucky clover should be doubled");
    }

    /**
     * Tests if the Lucky Clover correctly doubles the gold received from a GoldX3 item.
     */
    @Test
    void effectAppliedGoldX3Bonus() {
        inventory.setClover(false);
        goldX3.applyEffect(player);
        assertEquals(GoldX3.ADDED_GOLDX3, inventory.added,
             "if the lucky clover is not in the inventory the gold should not be doubled");

        inventory.added = 0;

        inventory.hasClover = true;
        goldX3.applyEffect(player);

        final int expected = GoldX3.ADDED_GOLDX3 * 2;
        assertEquals(expected, inventory.added, "gold with lucky clover should be doubled");
    }

    /**
     * Mock implementation of the Inventory to track added gold and clover presence.
     */
    private static final class FakeInventory implements Inventory {

        private int added;
        private boolean hasClover;

        void setClover(final boolean present) {
            this.hasClover = present;
        }

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
            if (item == KindOfItem.LUCKYCLOVER) {
               return hasClover ? 1 : 0; 
            }
            return 0;
        }

        @Override
        public boolean hasAtLeast(final ItemTypes item, final int quantity) {
            return item instanceof LuckyClover && hasClover;
        }
    }

    /**
     * Mock implementation of PlayerOperations to interact with the fake inventory.
     */
    private static final class FakePlayer implements PlayerOperations {

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
            ((FakeInventory) inventory).added += num;
            return this;
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
}
