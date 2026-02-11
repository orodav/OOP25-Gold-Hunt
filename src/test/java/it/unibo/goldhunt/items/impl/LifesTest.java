package it.unibo.goldhunt.items.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Objects;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.goldhunt.engine.api.Position;
import it.unibo.goldhunt.items.api.ItemContext;
import it.unibo.goldhunt.items.api.ItemTypes;
import it.unibo.goldhunt.player.api.Inventory;
import it.unibo.goldhunt.player.api.Player;
import it.unibo.goldhunt.player.api.PlayerOperations;

/**
 * Test class for {@link Lifes} item.
 * Verifies that the life-restoring effect works correctly.
 */
class LifesTest {
    private Lifes lifes;

    /**
     * Setup a new Lifes instance before each test.
     */
    @BeforeEach
    void init() {
        lifes = new Lifes();
    }

    /**
     * Tests that a life is added when the player is below the maximum.
     */
    @Test
    void testBelowMax() {
        final PlayerOpFake playerop = new PlayerOpFake(2);
        Objects.requireNonNull(playerop);
        lifes.bind(new ItemContext(null, playerop, null));
        final PlayerOperations res = lifes.applyEffect(playerop);
        assertNotNull(res);
        assertEquals(3, playerop.livesCount());
    }

    /**
     * Tests that no life is added if the player is already at the maximum limit.
     */
    @Test
    void testMaxLives() {
        final PlayerFake player = new PlayerFake(AbstractItem.MAX_QUANTITY_LIVES);
        Objects.requireNonNull(player);
        final PlayerOpFake playerop = new PlayerOpFake(AbstractItem.MAX_QUANTITY_LIVES);
        Objects.requireNonNull(playerop);
        lifes.bind(new ItemContext(null, playerop, null));
        final PlayerOperations res = lifes.applyEffect(playerop);
        assertNull(res);
        assertEquals(AbstractItem.MAX_QUANTITY_LIVES, playerop.livesCount());
    }

    /**
     * Fake Player implementation for testing life-related state.
     */
    private static final class PlayerFake implements Player {

        private final int lives;

        PlayerFake(final int lives) {
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
    }

    /**
     * Fake PlayerOperations implementation to simulate adding lives.
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
