/*
package it.unibo.goldhunt.player.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import it.unibo.goldhunt.items.api.ItemTypes;
import it.unibo.goldhunt.player.api.Inventory;

public class InventoryTest {
    // Helpers
    private Inventory empty() {
        return new InventoryImpl();
    }

    private enum StubItem implements ItemTypes {
        SHIELD;

        @Override
        public boolean applyEffect() {
            return false;
        }

        @Override
        public String shortString() {
            return "sh";
        }

        @Override
        public String getName() {
            return "shield";
        }
    }

    // quantity

    @Test
    void quantityShouldThrowIfItemNull() {
        final var inventory = empty();
        assertThrows(IllegalArgumentException.class, 
            () -> inventory.quantity(null)
        );
    }

    @Test
    void quantityShouldBeZeroForMissingItem() {
        final var inventory = empty();
        assertEquals(0, inventory.quantity(StubItem.SHIELD));
    }

    //__________add

    @Test
    void shouldAddMultipleTimesSameItem() {  //o limitiamo a 1?
        final var inventory = empty().add(StubItem.SHIELD, 2);
        final var updated = inventory.add(StubItem.SHIELD, 3);
        assertEquals(5, updated.quantity(StubItem.SHIELD));
        assertEquals(2, inventory.quantity(StubItem.SHIELD));
    }

    @Test
    void addShouldReturnNewInstanceAndNotModifyOriginal() {
        final var inventory = empty();
        final var updated = inventory.add(StubItem.SHIELD, 2);
        assertNotSame(inventory, updated);
        assertEquals(0, inventory.quantity(StubItem.SHIELD));
        assertEquals(2, updated.quantity(StubItem.SHIELD));
    }

    @Test
    void addShouldThrowIfQuantityNegative() {
        final var inventory = empty();
        assertThrows(IllegalArgumentException.class, () ->
                inventory.add(StubItem.SHIELD, -1));
    }

    @Test
    void addShouldThrowIfItemNull() {
        final var inventory = empty();
        assertThrows(IllegalArgumentException.class, 
            () -> inventory.add(null, 1));
    }

    @Test
    void addShouldAllowZeroQuantity() {
        final var inventory = empty().add(StubItem.SHIELD, 2);
        final var updated = inventory.add(StubItem.SHIELD, 0);
        assertNotSame(inventory, updated);
        assertEquals(2, updated.quantity(StubItem.SHIELD));
        assertEquals(2, inventory.quantity(StubItem.SHIELD));
    }

    //___________remove

    @Test
    void removeShouldReturnNewInstanceAndNotModifyOriginal() {
        final var inventory = empty().add(StubItem.SHIELD, 4);
        final var updated = inventory.remove(StubItem.SHIELD, 3);
        assertNotSame(inventory, updated);
        assertEquals(4, inventory.quantity(StubItem.SHIELD));
        assertEquals(1, updated.quantity(StubItem.SHIELD));
    }

    @Test
    void removeShouldRemoveKeyWhenQuantityZero() {
        final var inventory = empty().add(StubItem.SHIELD, 2);
        final var updated = inventory.remove(StubItem.SHIELD, 2);
        assertEquals(2, inventory.quantity(StubItem.SHIELD));
        assertEquals(0, updated.quantity(StubItem.SHIELD));
    }

    @Test
    void removeShouldThrowIfNotEnough() {
        final var inventory = empty().add(StubItem.SHIELD, 1);
        assertThrows(IllegalArgumentException.class, 
            () -> inventory.remove(StubItem.SHIELD, 2));
    }

    @Test
    void removeShouldThrowIfItemNull() {
        final var inventory = empty().add(StubItem.SHIELD, 1);
        assertThrows(IllegalArgumentException.class, 
            () -> inventory.remove(null, 1));
    }

    @Test
    void removeShouldThrowIfQuantityNotPositive() {
        final var inventory = empty().add(StubItem.SHIELD, 1);
        assertThrows(IllegalArgumentException.class, () ->
                inventory.remove(StubItem.SHIELD, -1));
        assertThrows(IllegalArgumentException.class, () ->
                inventory.remove(StubItem.SHIELD, 0));
    }


    //__________hasAtLeast

    @Test
    void hasAtLeastShouldReturnTrueWhenEnough() {
        final var inventory = empty().add(StubItem.SHIELD, 3);
        assertTrue(inventory.hasAtLeast(StubItem.SHIELD, 2));
        assertTrue(inventory.hasAtLeast(StubItem.SHIELD, 3));
    }

    @Test
    void hasAtLeastShouldReturnFalseWhenNotEnough() {
        final var inventory = empty().add(StubItem.SHIELD, 2);
        assertFalse(inventory.hasAtLeast(StubItem.SHIELD, 3));
    }

    @Test
    void hasAtLeastShouldReturnTrueWhenQuantityZero() {
        final var inventory = empty();
        assertTrue(inventory.hasAtLeast(StubItem.SHIELD, 0));
    }

    @Test
    void hasAtLeastShouldThrowIfItemNull() {
        final var inventory = empty();
        assertThrows(IllegalArgumentException.class, 
            () -> inventory.hasAtLeast(null, -1)
        );
    }
    @Test
    void hasAtLeastShouldThrowIfQuantityNegative() {
        final var inventory = empty();
        assertThrows(IllegalArgumentException.class,
            () -> inventory.hasAtLeast(StubItem.SHIELD, -1));
    }
}
 */