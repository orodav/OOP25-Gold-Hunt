package it.unibo.goldhunt.items.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.goldhunt.items.api.ItemTypes;
import it.unibo.goldhunt.player.api.Inventory;

public class LuckyCloverTest {

    private LuckyClover clover;
    private InventoryFake inventory;
    private Pyrite gold;
    private PyriteX3 goldX3;

    @BeforeEach
    void init(){
        clover = new LuckyClover();

        gold = new Pyrite();
        goldX3 = new PyriteX3();

        clover.gold = gold;
        clover.goldX3 = goldX3;

        inventory = new InventoryFake();
        clover.inventory = inventory;
        clover.luckyclover = clover;
    }

    @Test
    void applySingleGold(){
        inventory.hasEnough = true;
        boolean used = clover.applyEffect();

        assertTrue(used, "the lucky clover should apply the effects");
        assertTrue(gold.effectApplied, "gold effects should be applied");
        assertTrue(goldX3.effectApplied, "gold x3 effects should be applied");
        assertEquals(2, inventory.added, "should recieve bonus for both gold items");
    }

    @Test
    void effectWithoutClover(){
        inventory.hasEnough = false;

        boolean used = clover.applyEffect();

        assertTrue(used, "effect should be applied whatsoever");

        assertTrue(gold.effectApplied, "effect should be applied whatsoever");
        assertTrue(goldX3.effectApplied, "effect should be applied whatsoever");
        assertEquals(0, inventory.added, "inventory should not revieve bonus without clover");
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
        boolean effectApplied = false;
        @Override
        public boolean applyEffect() {
            effectApplied = true;
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
    boolean effectApplied = false;
    @Override
    public boolean applyEffect() {
        effectApplied = true;
        return true;
    }
    @Override public String getName() {
        return "GoldX3"; 
    }
    @Override public String shortString() { 
        return "G3"; 
    }
}

}
