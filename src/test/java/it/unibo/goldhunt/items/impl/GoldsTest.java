package it.unibo.goldhunt.items.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Objects;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.goldhunt.items.api.ItemTypes;
import it.unibo.goldhunt.player.api.Inventory;

public class GoldsTest {

    private Gold gold;
    private GoldX3 goldX3;
    private FakeInventory inventory;

    @BeforeEach
    void init(){
        goldX3 = new GoldX3();
        Objects.requireNonNull(goldX3);
        inventory = new FakeInventory();
        Objects.requireNonNull(inventory);
        gold = new Gold();
        Objects.requireNonNull(gold);

        goldX3.inventory = inventory;
        gold.inventory = inventory;
    }
    @Test
    void effectAppliedGoldX3(){
        boolean applied = goldX3.applyEffect();

        assertTrue(applied, "gold x3 effect should return true");
        assertEquals(GoldX3.ADDED_GOLDX3, inventory.added, "should return the correct amount");
    }

    @Test
    void effectAppliedGold(){
        boolean applied = gold.applyEffect();

        assertTrue(applied, "gold effect should return true");
        assertEquals(Gold.ADDED_GOLD, inventory.added, "should return the correct amount");
    }
    private static final class FakeInventory implements Inventory{

        int added = 0;

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
            return added;
        }
        
    }

}
