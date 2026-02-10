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
public class GoldsTest {

    private Gold gold;
    private GoldX3 goldX3;
    private FakeInventory inventory;
    private FakePlayer player;

    /**
     * Initializes the player, inventory, and gold items before each test.
     */
    @BeforeEach
    void init(){
        inventory = new FakeInventory();
        player = new FakePlayer(inventory);
        goldX3 = new GoldX3();
        gold = new Gold();
        Objects.requireNonNull(goldX3);
        Objects.requireNonNull(inventory);
        Objects.requireNonNull(gold);
        Objects.requireNonNull(player);
        gold.context = new ItemContext(null, player, inventory);
        goldX3.context = new ItemContext(null, player, inventory);
    }

    /**
     * Verifies that GoldX3 adds the correct tripled amount of gold.
     */
    @Test
    void effectAppliedGoldX3(){
        PlayerOperations applied = goldX3.applyEffect(player);

        assertTrue(applied != null, "gold x3 effect should return true");
        assertEquals(GoldX3.ADDED_GOLDX3, inventory.added, "should return the correct amount");
    }

    /**
     * Verifies that a standard Gold item adds the correct base amount of gold.
     */
    @Test
    void effectAppliedGold(){
        PlayerOperations applied = gold.applyEffect(player);

        assertTrue(applied != null, "gold effect should return true");
        assertEquals(Gold.ADDED_GOLD, inventory.added, "should return the correct amount");
    }

    /**
     * Tests if the Lucky Clover correctly doubles the gold received from a normal Gold item.
     */
    @Test
    void effectAppliedGoldBonus(){
        inventory.setClover(false);
        gold.applyEffect(player);
        assertEquals(Gold.ADDED_GOLD, inventory.added, "if the lucky clover is not in the inventory the gold should not be doubled");

        inventory.added = 0;

        inventory.hasClover = true;
        gold.applyEffect(player);

        int expected = Gold.ADDED_GOLD * 2;
        assertEquals(expected, inventory.added, "gold with lucky clover should be doubled");

    }

    /**
     * Tests if the Lucky Clover correctly doubles the gold received from a GoldX3 item.
     */
    @Test
    void effectAppliedGoldX3Bonus(){
        inventory.setClover(false);
        goldX3.applyEffect(player);
        assertEquals(GoldX3.ADDED_GOLDX3, inventory.added, "if the lucky clover is not in the inventory the gold should not be doubled");

        inventory.added = 0;

        inventory.hasClover = true;
        goldX3.applyEffect(player);

        int expected = GoldX3.ADDED_GOLDX3 * 2;
        assertEquals(expected, inventory.added, "gold with lucky clover should be doubled");
    }

    /**
     * Mock implementation of the Inventory to track added gold and clover presence.
     */
    private static final class FakeInventory implements Inventory{

        int added = 0;
        boolean hasClover = false;

        void setClover(boolean present){
            this.hasClover = present;
        }

        @Override
        public Inventory add(ItemTypes item, int quantity) {
            added+=quantity;
            return this;
        }

        @Override
        public Inventory remove(ItemTypes item, int quantity) {
            throw new UnsupportedOperationException("Unimplemented method 'remove'");
        }

        @Override
        public int quantity(ItemTypes item) {
            if(item == KindOfItem.LUCKYCLOVER){
               return hasClover ? 1 : 0; 
            }
            return 0;
        }

        public boolean hasAtLeast(ItemTypes item, int quantity){
            if(item instanceof LuckyClover){
                return hasClover;
            }
            return false;
        }
    }

    /**
     * Mock implementation of PlayerOperations to interact with the fake inventory.
     */
    private final static class FakePlayer implements PlayerOperations{

        private final Inventory inventory;

        FakePlayer(Inventory inventory){
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
        public PlayerOperations withInventory(Inventory inventory) {
            throw new UnsupportedOperationException("Unimplemented method 'withInventory'");
        }

        @Override
        public PlayerOperations moveTo(Position p) {
            throw new UnsupportedOperationException("Unimplemented method 'moveTo'");
        }

        @Override
        public PlayerOperations addGold(int num) {
            ((FakeInventory) inventory).added += num;
            return this;
        }

        @Override
        public PlayerOperations addLives(int num) {
            throw new UnsupportedOperationException("Unimplemented method 'addLives'");
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
