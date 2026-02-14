package it.unibo.goldhunt.items.impl;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Objects;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.goldhunt.engine.api.Position;
import it.unibo.goldhunt.items.api.ItemTypes;
import it.unibo.goldhunt.items.api.Revealable;
import it.unibo.goldhunt.player.api.Inventory;
import it.unibo.goldhunt.player.api.PlayerOperations;

/**
 * Test class for {@link TrapFactoryImpl}.
 * Verifies the correct creation and typing of trap items.
 */
class TrapFactoryImplTest {

    private TrapFactoryImpl trapFactory;
    private PlayerOpFake playerop;

    /**
     * Initializes the factory and the fake player before each test.
     */
    @BeforeEach
    void init() {
        playerop = new PlayerOpFake();
        trapFactory = new TrapFactoryImpl(playerop);
        Objects.requireNonNull(trapFactory);
    }

    /**
     * Verifies that the factory does not return null when creating a trap.
     */
    @Test
    void testTrapNonNull() {
        final Revealable trap = trapFactory.createTrap(playerop);
        assertNotNull(trap, "should not create an empty trap");
    }

    /**
     * Ensures that the created trap is a valid instance of {@link Revealable}.
     */
    @Test
    void trapIsTrap() {
        final Revealable trap = trapFactory.createTrap(playerop);
        assertNotNull(trap, "the trap should not be null");
    }

     /**
      * Fake PlayerOperations implementation to satisfy factory dependencies.
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

        @Override
        public PlayerOperations setLives(final int num) {
            throw new UnsupportedOperationException("Unimplemented method 'setLives'");
        }
    }
}
