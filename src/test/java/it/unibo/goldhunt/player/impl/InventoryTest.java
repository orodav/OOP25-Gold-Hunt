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

    private Inventory notEmpty(ItemTypes item, int quantity) {
        return empty().add(item, quantity);
    }

    private ItemTypes item() {
        return ItemTypes.SHIELD;
    }

    private Inventory inventoryWith(int quantity) {
        return empty().add(item(), quantity);
    }

    //___________________

    
    @Test
    void shouldCreateEmptyInventory() {
        final var inv = empty();
        assertEquals(0, inv.quantity(item()));
    }

    // add, remove, quantity, hasAtLeast

    @Test
    void quantityShouldThrowIfQuantityNull() {
        final var inv = empty();
        assertThrows(IllegalArgumentException.class, () ->
                inv.quantity(null));
    }

    //__________add

    @Test
    void shouldAddMultipleTimesSameItem() {  //o limitiamo a 1?
        final var inv = empty().add(item(), 2);
        final var updated = inv.add(item(), 3);
        assertEquals(5, updated.quantity(item()));
        assertEquals(2, inv.quantity(item()));
    }

    @Test
    void addShouldReturnNewInstanceAndNotModifyOriginal() {
        var inv = empty();
        var updated = inv.add(item(), 2);
        assertNotSame(inv, updated);
        assertEquals(0, inv.quantity(item()));
        assertEquals(2, updated.quantity(item()));
    }

    @Test
    void addShouldThrowIfQuantityNegative() {
        final var inv = empty();
        assertThrows(IllegalArgumentException.class, () ->
                inv.add(item(), -1));
    }

    @Test
    void addShouldThrowIfItemNull() {
        final var inv = empty();
        assertThrows(IllegalArgumentException.class, () ->
                inv.add(null, 1));
    }

    @Test
    void addShouldAllowZeroQuantity() {
        final var inv = empty().add(item(), 2);
        final var updated = inv.add(item(), 0);
        assertEquals(2, updated.quantity(item()));
    }


    //___________remove

    @Test
    void removeShouldReturnNewInstanceAndNotModifyOriginal() {
        var inv = empty().add(item(), 4);
        var updated = inv.remove(item(), 3);
        assertNotSame(inv, updated);    // new instance
        assertEquals(4, inv.quantity(item()));  //original not modified
        assertEquals(1, updated.quantity(item()));  //correct new state
    }

    @Test
    void shouldAllowReachingZero() {    
        final var inv = inventoryWith(2);
        final var updated = inv.remove(item(), 2);
        assertEquals(0, updated.quantity(item()));
    } 

    @Test
    void removeShouldThrowIfNotEnough() {
        final var inv = inventoryWith(1);
        assertThrows(IllegalArgumentException.class, () ->
                inv.remove(item(), 2));
    }

    @Test
    void shouldThrowIfRemoveItemNull() {
        final var inv = empty().add(item(), 1);
        assertThrows(IllegalArgumentException.class, () ->
                inv.remove(null, 1));
    }

    @Test
    void shouldThrowIfRemoveQuantityNegativeOrZero() {
        final var inv = empty();
        assertThrows(IllegalArgumentException.class, () ->
                inv.remove(item(), -1));
        assertThrows(IllegalArgumentException.class, () ->
                inv.remove(item(), 0));
    }


    //__________hasAtLeast

    @Test
    void shouldHasAtLeastReturnTrueWhenEnough() {
        var inv = inventoryWith(3);
        assertTrue(inv.hasAtLeast(item(), 2));
        assertTrue(inv.hasAtLeast(item(), 3));
    }

    @Test
    void shouldHasAtLeastReturnFalseWhenNotEnough() {
        var inv = inventoryWith(2);
        assertFalse(inv.hasAtLeast(item(), 3));
    }

    @Test
    void shouldHasAtLeastThrowIfQuantityNegative() {
        var inv = inventoryWith(2);
        assertThrows(IllegalArgumentException.class, () ->
                inv.hasAtLeast(item(), 0));
        assertThrows(IllegalArgumentException.class, () ->
                inv.hasAtLeast(item(), -1));
    }
}
