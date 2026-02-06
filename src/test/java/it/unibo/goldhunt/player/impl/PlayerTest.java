package it.unibo.goldhunt.player.impl;
//davv
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import it.unibo.goldhunt.engine.api.Position;
//import it.unibo.goldhunt.items.api.ItemTypes;
import it.unibo.goldhunt.player.api.Inventory;

public class PlayerTest {

    // helpers

    private Position pos(final int x, final int y) {
        return new Position(x,y);
    }

    private Inventory emptyInventory() {
        return new InventoryImpl();
    }

    private PlayerImpl basicPlayer() {
        return new PlayerImpl(pos(0, 0), 3, 0, emptyInventory());
    }

    // Testing Constructor Invariants

    @Test
    void shouldCreateValidPlayer() {
        final var player = new PlayerImpl(pos(0, 0), 3, 0, emptyInventory());
        assertEquals(pos(0, 0), player.position());
        assertEquals(3, player.livesCount());
        assertEquals(0, player.goldCount());
        assertNotNull(player.inventory());
    }

    @Test
    void shouldThrowIfLivesNegative() {
        assertThrows(IllegalArgumentException.class, () ->
                new PlayerImpl(pos(0, 0), -1, 0, emptyInventory()));
    }

    @Test
    void shouldThrowIfGoldNegative() {
        assertThrows(IllegalArgumentException.class, () ->
                new PlayerImpl(pos(0, 0), 3, -1, emptyInventory()));
    }

    @Test
    void shouldThrowIfPositionNull() {
        assertThrows(IllegalArgumentException.class, () ->
                new PlayerImpl(null, 3, 0, emptyInventory()));
    }

    @Test
    void shouldThrowIfInventoryNull() {
        assertThrows(IllegalArgumentException.class, () -> 
                new PlayerImpl(pos(0, 0), 3, 0, null));
    }


    // Testing Immutability and field preservation

    // _______________moveTo
    @Test
    void moveToShouldReturnNewPlayer() {
        final var player = basicPlayer();
        final var newPos = pos(4, 7);
        final var updated = player.moveTo(newPos);
        assertNotSame(player, updated);
        assertEquals(newPos, updated.position());
        assertEquals(player.livesCount(), updated.livesCount());
        assertEquals(player.goldCount(), updated.goldCount());
        assertEquals(player.inventory(), updated.inventory());
        assertEquals(pos(0, 0), player.position());     //originale rimane invariato
    }

    @Test
    void moveToChangePositionOnly() {
        var player = new PlayerImpl(pos(0, 0), 3, 0, emptyInventory());
        var newPos = pos(2, 4);
        var updated = player.moveTo(newPos);
        assertNotSame(player, updated);
        assertEquals(newPos, updated.position());
        assertEquals(3, updated.livesCount());
        assertEquals(0, updated.goldCount());
        assertEquals(player.inventory(), updated.inventory());
    }

    @Test
    void moveToShouldThrowIfNullPosition() {
        var player = new PlayerImpl(pos(0, 0), 3, 0, emptyInventory());
        assertThrows(IllegalArgumentException.class, () ->
                player.moveTo(null));
    }

    
    @Test
    void moveToSamePositionStillReturnsNewInstance() {
        var player = new PlayerImpl(pos(0,0), 3, 0, emptyInventory());
        var updated = player.moveTo(pos(0,0));
        assertNotSame(player, updated);
        assertEquals(player, updated);
    }

    //_________________gold

    @Test
    void addGoldShouldIncreaseGoldWhenNumPositive() {
        var player = basicPlayer();
        var updated = player.addGold(3);
        assertNotSame(player, updated);
        assertEquals(3, updated.goldCount());
        assertEquals(0, player.goldCount());
    }

    @Test
    void addGoldShouldDecreaseGoldWhenNumNegative() {
        var player = new PlayerImpl(pos(0, 0), 3, 4, emptyInventory());
        var updated = player.addGold(-3);
        assertNotSame(player, updated);
        assertEquals(1, updated.goldCount());
        assertEquals(4, player.goldCount());
    }

    @Test
    void addGoldShouldThrowWhenResultNegative() {
        var player = new PlayerImpl(pos(0, 0), 3, 0, emptyInventory());
        assertThrows(IllegalArgumentException.class, () ->
                player.addGold(-1));
    }

    @Test
    void addGoldShouldAllowValueZero() {
        var player = new PlayerImpl(pos(0, 0), 3, 2, emptyInventory());
        var updated = player.addGold(-2);
        assertEquals(0, updated.goldCount());
    }

    //____________lives

    @Test
    void addLivesShouldIncreaseByOne() {
        var player = new PlayerImpl(pos(0, 0), 3, 0, emptyInventory());
        var updated = player.addLives(1);
        assertNotSame(player, updated);
        assertEquals(4, updated.livesCount());
        assertEquals(3, player.livesCount());
    }

    @Test
    void addLivesShouldDecreaseByOne() {
        var player = new PlayerImpl(pos(0, 0), 3, 0, emptyInventory());
        var updated = player.addLives(-1);
        assertNotSame(player, updated);
        assertEquals(2, updated.livesCount());
        assertEquals(3, player.livesCount());
    }

    @Test
    void addLivesShouldAllowReachingZero() {
        var player = new PlayerImpl(pos(0, 0), 1, 0, emptyInventory());
        var updated = player.addLives(-1);
        assertEquals(0, updated.livesCount());
    }

    @Test
    void addLivesShouldThrowWhenNegative() {
        var player = new PlayerImpl(pos(0, 0), 0, 0, emptyInventory());
        assertThrows(IllegalArgumentException.class, () ->
                player.addLives(-1));
    }

    // ___________Inventory: delegation

    /*
    @Test
    void addItemShouldUpdateInventory() {
        final var player = basicPlayer();
        final var updated = player.addItem(ItemTypes.SHIELD, 1);
        assertNotSame(player, updated);
        assertEquals(player.position(), updated.position());
        assertEquals(player.livesCount(), updated.livesCount());
        assertEquals(player.goldCount(), updated.goldCount());
        assertNotEquals(player.inventory(), updated.inventory());
    }
    */

    /*
    @Test
    void useItemShouldUpdateInventory() {
        final var player = basicPlayer().addItem(ItemTypes.SHIELD, 1);
        final var updated = player.useItem(ItemTypes.SHIELD, 1);
        assertNotSame(player, updated);
        assertNotEquals(player.inventory(), updated.inventory());
    }
    */

    // ________equals e hashCode

    @Test
    void equalsShouldBeTrueForSameState() {
        final var inventory = emptyInventory();
        final var player = new PlayerImpl(pos(1, 1), 3, 0, inventory);
        final var updated = new PlayerImpl(pos(1, 1), 3, 0, inventory);
        assertEquals(player, updated);
        assertEquals(player.hashCode(), updated.hashCode());
    }

    @Test
    void equalsShouldBeFalseIfFieldsDiffers() {
        final var inventory = emptyInventory();
        final var base = new PlayerImpl(pos(1, 1), 3, 0, inventory);
        assertNotEquals(base, new PlayerImpl(pos(2, 1), 3, 0, inventory));
        assertNotEquals(base, new PlayerImpl(pos(1, 1), 4, 0, inventory));
        assertNotEquals(base, new PlayerImpl(pos(1, 1), 3, 4, inventory));
        //assertNotEquals(base, new PlayerImpl(pos(1, 1), 3, 0, inventory.add(ItemTypes.SHIELD, 1)));
    }

    @Test
    void EqualsShouldHandleNull() {
        final var player = basicPlayer();
        assertNotEquals(player, null);
        assertNotEquals(player, "not a player");
    }
}
