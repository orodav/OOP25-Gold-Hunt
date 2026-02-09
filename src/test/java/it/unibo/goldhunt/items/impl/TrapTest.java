package it.unibo.goldhunt.items.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.goldhunt.engine.api.Position;
import it.unibo.goldhunt.items.api.ItemTypes;
import it.unibo.goldhunt.player.api.Inventory;
import it.unibo.goldhunt.player.api.PlayerOperations;

public class TrapTest {

    private Trap trap;
    private PlayerOpFake playerop;

    @BeforeEach
    void init(){
        playerop = new PlayerOpFake();
        trap = new Trap(playerop);
    }

    @Test
    void testEffect(){
        int defaultLives = playerop.livesCount();
        trap.applyEffect();
        assertEquals(defaultLives - Trap.DAMAGE * -1, playerop.livesCount(),  "trap should reduce life by DAMAGE");
    }

    @Test
    void testID(){
        String str = trap.shortString();
        assertEquals("T", str, "the method shortstring should return T");
    }


    private static final class PlayerOpFake implements PlayerOperations{
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
