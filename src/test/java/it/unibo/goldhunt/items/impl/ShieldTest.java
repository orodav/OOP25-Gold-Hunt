package it.unibo.goldhunt.items.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Objects;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.goldhunt.engine.api.Position;
import it.unibo.goldhunt.items.api.ItemContext;
import it.unibo.goldhunt.items.api.ItemTypes;
import it.unibo.goldhunt.items.api.Revealable;
import it.unibo.goldhunt.player.api.Inventory;
import it.unibo.goldhunt.player.api.PlayerOperations;

/**
 * Test class for {@link Shield} item.
 * Verifies that the shield protects the player from traps.
 */
class ShieldTest {

    private Shield shield;
    private PlayerOpFake playerOp;

    /**
     * Sets up the test environment with a fake player and a bound shield.
     */
    @BeforeEach
    void init() {
        playerOp = new PlayerOpFake(2);
        Objects.requireNonNull(playerOp);
        shield = new Shield();
        Objects.requireNonNull(shield);
        final ItemContext itemContext = new ItemContext(null, playerOp, null);
        shield.bind(itemContext);
        shield.trap = new TrapFake();
    }

    /**
     * Tests that the shield is used and lives are preserved when a trap is triggered.
     */
    @Test
    void applyWhenTrapActivated() {
        final int livesBefore = playerOp.lives;
        final PlayerOperations used = shield.applyEffect(playerOp);

        assertTrue(used != null, "shield should be used");
        assertEquals(livesBefore, playerOp.lives);
    }

    /**
     * Tests that the shield is not consumed if no trap is present.
     */
    @Test
    void noTrapEffect() {
        shield.trap = null;
        final int livesBef = playerOp.livesCount();
        final PlayerOperations used = shield.applyEffect(playerOp);

        assertEquals(null, used, "the shield should not be used when no trap is activated");
        assertEquals(livesBef, playerOp.livesCount(), "lives shoul remain the same");
    }

    /**
     * Fake trap implementation used to simulate a threat for the shield.
     */
    private static final class TrapFake implements Revealable {

        TrapFake() {
            //empty constructor
        }

        @Override
        public String shortString() {
            return "T";
        }

        @Override
        public PlayerOperations applyEffect(final PlayerOperations playerop) {
            return playerop;
        }
    }

    /**
     * Fake PlayerOperations implementation to track life changes during trap events.
     */
    private static final class PlayerOpFake implements PlayerOperations {

        private int lives;

        PlayerOpFake(final int lives) {
            this.lives = lives;
        }

        @Override
        public Position position() {
            throw new UnsupportedOperationException("Unimplemented method 'position'");
        }

        @Override
        public int livesCount() {
            return lives;
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
            lives += num;
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
    }
}
