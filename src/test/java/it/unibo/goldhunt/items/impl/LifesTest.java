package it.unibo.goldhunt.items.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Objects;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.goldhunt.engine.api.Position;
import it.unibo.goldhunt.items.api.ItemTypes;
import it.unibo.goldhunt.player.api.Inventory;
import it.unibo.goldhunt.player.api.Player;
import it.unibo.goldhunt.player.api.PlayerOperations;

public class LifesTest {
    private Lifes lifes;

    @BeforeEach
    void init(){
        lifes = new Lifes();
    }

    @Test
    void testBelowMax(){
        PlayerFake player = new PlayerFake(2);
        Objects.requireNonNull(player);
        PlayerOpFake playerop = new PlayerOpFake(2);
        Objects.requireNonNull(playerop);

        lifes.player = player;
        lifes.playerop = playerop;

        boolean res = lifes.applyEffect();
        assertTrue(res);
        assertEquals(3, playerop.livesCount());
    }

    @Test
    void testMaxLives(){
        PlayerFake player = new PlayerFake(Item.MAX_QUANTITY_LIVES);
        Objects.requireNonNull(player);
        PlayerOpFake playerop = new PlayerOpFake(Item.MAX_QUANTITY_LIVES);
        Objects.requireNonNull(playerop);

        lifes.player = player;
        lifes.playerop = playerop;

        boolean res = lifes.applyEffect();

        assertFalse(res);
        assertEquals(Item.MAX_QUANTITY_LIVES, playerop.livesCount());
    }

    private static final class PlayerFake implements Player{

        private int lives;

        PlayerFake(int lives){
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
        public PlayerOperations withInventory(Inventory inventory) {
            throw new UnsupportedOperationException("Unimplemented method 'withInventory'");
        }
        
    }

    private static final class PlayerOpFake implements PlayerOperations{

        private int lives;

        PlayerOpFake(int lives){
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
        public PlayerOperations withInventory(Inventory inventory) {
            throw new UnsupportedOperationException("Unimplemented method 'withInventory'");
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
            lives += num;
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
        
    }
}
