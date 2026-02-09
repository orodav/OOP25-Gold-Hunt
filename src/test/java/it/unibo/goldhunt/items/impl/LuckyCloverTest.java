package it.unibo.goldhunt.items.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.goldhunt.engine.api.Position;
import it.unibo.goldhunt.items.api.ItemContext;
import it.unibo.goldhunt.items.api.ItemTypes;
import it.unibo.goldhunt.player.api.Inventory;
import it.unibo.goldhunt.player.api.PlayerOperations;

public class LuckyCloverTest {

    private LuckyClover clover;
    private InventoryFake inventory;
    private Pyrite gold;
    private PyriteX3 goldX3;

    @BeforeEach
    void init(){
        inventory = new InventoryFake();
        var playerop = new FakePlayer(inventory);
        clover = new LuckyClover();
        gold = new Pyrite();
        goldX3 = new PyriteX3();

        gold.bind(new ItemContext(null, playerop, inventory));
        goldX3.bind(new ItemContext(null, playerop, inventory));
        clover.bind(new ItemContext(null, playerop, inventory));
    }

    @Test
    void applyEffect(){
        inventory.hasEnough = true;
        boolean used = clover.applyEffect();

        assertTrue(used);
        assertEquals(LuckyClover.MAX_QUANTITY_CLOVER, inventory.added, "should be in inventory");
    }

    private final static class InventoryFake implements Inventory{
        int added = 0;
        boolean hasEnough = true;

        @Override
        public Inventory add(ItemTypes item, int quantity) {
            added += quantity;
            return this;
        }

        @Override
        public Inventory remove(ItemTypes item, int quantity) {
            throw new UnsupportedOperationException("Unimplemented method 'remove'");
        }

        @Override
        public int quantity(ItemTypes item) {
            if(hasEnough){
                return LuckyClover.MAX_QUANTITY_CLOVER;
                
            }else{
                return 0;
            }
        }
        
    }
    
    private static class Pyrite extends Gold {
        @Override
        public boolean applyEffect() {
        return true;
        }
        @Override public String getName() {
            return "Gold"; 
        }
        @Override public String shortString() { 
            return "G"; 
        }
    }

    private static class PyriteX3 extends GoldX3 {
    @Override
    public boolean applyEffect() {
        return true;
    }
    @Override public String getName() {
        return "GoldX3"; 
    }
    @Override public String shortString() { 
        return "G3"; 
    }
}

private static final class FakePlayer implements PlayerOperations{
    final Inventory inventory;

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
        throw new UnsupportedOperationException("Unimplemented method 'addGold'");
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
