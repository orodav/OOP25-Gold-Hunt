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

public class ShieldTest {

    private Shield shield;
    private PlayerOpFake playerOp;

    @BeforeEach
    void init(){
        playerOp = new PlayerOpFake(2);
        Objects.requireNonNull(playerOp);
        shield = new Shield();
        Objects.requireNonNull(shield);

        ItemContext itemContext = new ItemContext(null, playerOp, null);
        shield.bind(itemContext);
        shield.trap = new TrapFake(true);
    }

    @Test
    void applyWhenTrapActivated(){
        int livesBefore = playerOp.lives;

        PlayerOperations used = shield.applyEffect(playerOp);

        assertTrue(used != null, "shield should be used");
        assertEquals(livesBefore, playerOp.lives);
    }

    @Test
    void noTrapEffect(){
        shield.trap = null;

        int livesBef = playerOp.livesCount();
        PlayerOperations used = shield.applyEffect(playerOp);

        assertEquals(null, used, "the shield should not be used when no trap is activated");
        assertEquals(livesBef, playerOp.livesCount(), "lives shoul remain the same");
    }

    private static final class TrapFake implements Revealable{

        private final boolean effect;

        TrapFake(boolean effect){
            this.effect = effect;
            getEffect();
        }

        @Override
        public String shortString() {
            return "T";
        }

        @Override
        public PlayerOperations applyEffect(PlayerOperations playerop) {
            return playerop;
        }

        private boolean getEffect(){
            return effect;
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
