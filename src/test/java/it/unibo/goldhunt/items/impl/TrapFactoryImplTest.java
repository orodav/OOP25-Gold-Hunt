package it.unibo.goldhunt.items.impl;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Objects;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.goldhunt.engine.api.Position;
import it.unibo.goldhunt.items.api.ItemTypes;
import it.unibo.goldhunt.items.api.Revealable;
import it.unibo.goldhunt.player.api.Inventory;
import it.unibo.goldhunt.player.api.PlayerOperations;

public class TrapFactoryImplTest {

    private TrapFactoryImpl trapFactory;
    private PlayerOpFake playerop;

    @BeforeEach
    void init(){
        playerop = new PlayerOpFake();
        Objects.requireNonNull(playerop);
        trapFactory = new TrapFactoryImpl(playerop);
        Objects.requireNonNull(trapFactory);
    }

    @Test
    void testTrapNonNull(){
        Revealable trap = trapFactory.createTrap();
        assertNotNull(trap, "should not create an empty trap");
    }

    @Test
    void trapIsTrap(){
        Revealable trap = trapFactory.createTrap();
        assertTrue(trap instanceof Revealable, "should create an instance of Trap");
    }

     private static class PlayerOpFake implements PlayerOperations{

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
        public PlayerOperations moveTo(Position p) {
            throw new UnsupportedOperationException("Unimplemented method 'moveTo'");
        }

        @Override
        public PlayerOperations addGold(int num) {
            throw new UnsupportedOperationException("Unimplemented method 'addGold'");
        }

        @Override
        public PlayerOperations addLives(int num) {
            this.lives += num;
            return this;
        }

        @Override
        public PlayerOperations addItem(ItemTypes item, int quantity) {
            throw new UnsupportedOperationException("Unimplemented method 'addItem'");
        }

        @Override
        public PlayerOperations useItem(ItemTypes item, int quantity) {
            throw new UnsupportedOperationException("Unimplemented method 'useItem'");
        }

        @Override
        public PlayerOperations withInventory(Inventory inventory) {
            throw new UnsupportedOperationException("Unimplemented method 'withInventory'");
        }

    }
}
