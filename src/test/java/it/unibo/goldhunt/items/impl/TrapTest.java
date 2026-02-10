package it.unibo.goldhunt.items.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.goldhunt.engine.api.Position;
import it.unibo.goldhunt.items.api.ItemTypes;
import it.unibo.goldhunt.player.api.Inventory;
import it.unibo.goldhunt.player.api.PlayerOperations;

/**
 * Test class for {@link Trap}.
 * Verifies the damage effect and the string representation of traps.
 */
class TrapTest {

    private Trap trap;
    private PlayerOpFake playerop;

    /**
     * Initializes a trap and a fake player before each test.
     */
    @BeforeEach
    void init() {
        playerop = new PlayerOpFake();
        trap = new Trap();
        trap.bind(playerop);
    }

    /**
     * Tests that applying the trap effect reduces the player's lives by the defined damage.
     */
    @Test
    void testEffect() {
        final int defaultLives = playerop.livesCount();
        trap.applyEffect(playerop);

        assertEquals(defaultLives + Trap.DAMAGE, playerop.livesCount(), "trap should reduce life by DAMAGE");
    }

    /**
     * Verifies that the short string representation of the trap is correctly returned.
     */
    @Test
    void testID() {
        final String str = trap.shortString();
        assertEquals("T", str, "the method shortstring should return T");
    }

    /**
     * Fake PlayerOperations implementation used to track life changes caused by traps.
     */
    private static final class PlayerOpFake implements PlayerOperations {
        private int lives = 3;

        @Override
        public Position position() {
            throw new UnsupportedOperationException("Unimplemented method 'position'");
        }

        @Override
        public int livesCount() {
            return this.lives;
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
        public PlayerOperations moveTo(final Position p) {
            throw new UnsupportedOperationException("Unimplemented method 'moveTo'");
        }

        @Override
        public PlayerOperations addGold(final int num) {
            throw new UnsupportedOperationException("Unimplemented method 'addGold'");
        }

        @Override
        public PlayerOperations addLives(final int num) {
            this.lives += num;
            return this;
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
        public PlayerOperations withInventory(final Inventory inventory) {
            throw new UnsupportedOperationException("Unimplemented method 'withInventory'");
        }
    }
}
