package it.unibo.goldhunt.player.impl;

import org.junit.jupiter.api.Test;

public class InventoryTest {

    @Test
    void shouldCreateEmptyInventory() {}

    // add, remove, quantity, hasAtLeast

    @Test
    void shouldReturnZeroQuantityForMissingItem() {}

    @Test
    void shouldThrowIfQuantityNull() {}

    @Test
    void shouldAddMultipleTimesSameItem() {} //o limitiamo a 1?

    @Test
    void shouldReturnNewInstanceWhenAdding() {}

    @Test
    void shouldThrowIfQuantityNegative() {}

    @Test
    void shouldNotModifyOriginalWhenAdding() {}

    @Test
    void shouldThrowIfAddItemNull() {}

    @Test
    void shouldAllowAddingZeroQuantity() {}



    @Test
    void shouldRemoveItemQuantity() {}

    @Test
    void shouldReturnNewInstanceWhenRemoving() {}

    @Test
    void shouldNotModifyOriginalWhenRemoving() {}

    @Test
    void shouldAllowReachingZero() {} //andRemoveEntry ?

    @Test
    void shouldThrowIfRemoveMoreThanOwned() {}

    @Test
    void shouldThrowIfRemoveItemNull() {}

    @Test
    void shouldThrowIfRemoveQuantityZeroOrNegative() {}



    @Test
    void shouldHasAtLeastReturnTrueWhenEnough() {}

    @Test
    void shouldHasAtLeastReturnFalseWhenNotEnough() {}

    @Test
    void shouldHasAtLeastThrowIfQuantityNegative() {}
}
