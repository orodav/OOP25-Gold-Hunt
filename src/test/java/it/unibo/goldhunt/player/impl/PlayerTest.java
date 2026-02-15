package it.unibo.goldhunt.player.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import it.unibo.goldhunt.engine.api.Position;
import it.unibo.goldhunt.items.api.ItemTypes;
import it.unibo.goldhunt.items.api.KindOfItem;
import it.unibo.goldhunt.player.api.Inventory;
import it.unibo.goldhunt.player.api.PlayerOperations;

/**
 * Testing class for Player implementation.
 */
class PlayerTest {

    private static final int ONE_NEGATIVE = -1;

    private Position pos(final int x, final int y) {
        return new Position(x, y);
    }

    private Inventory emptyInventory() {
        return new InventoryImpl();
    }

    private PlayerImpl basicPlayer() {
        return new PlayerImpl(pos(0, 0), 3, 0, emptyInventory());
    }

    /**
     * Nested Enum created for Player Testing class.
     */
    private enum StubItem implements ItemTypes {
        SHIELD;

        @Override
        public PlayerOperations applyEffect(final PlayerOperations player) {
            if (player == null) {
                throw new IllegalArgumentException("player");
            }
            return player;
        }

        @Override
        public String shortString() {
            return "sh";
        }

        @Override
        public String getName() {
            return "shield";
        }

        @Override
        public KindOfItem getItem() {
            throw new UnsupportedOperationException("Unimplemented method 'getItem'");
        }
    }

    @Test
    void shouldCreateValidPlayer() {
        final var inventory = emptyInventory();
        final var player = new PlayerImpl(pos(0, 0), 3, 0, inventory);
        assertEquals(pos(0, 0), player.position());
        assertEquals(3, player.livesCount());
        assertEquals(0, player.goldCount());
        assertSame(inventory, player.inventory());
    }

    @Test
    void shouldThrowIfLivesNegative() {
        assertThrows(IllegalArgumentException.class, 
            () -> new PlayerImpl(pos(0, 0), -1, 0, emptyInventory()));
    }

    @Test
    void shouldThrowIfGoldNegative() {
        assertThrows(IllegalArgumentException.class, 
            () -> new PlayerImpl(pos(0, 0), 3, -1, emptyInventory()));
    }

    @Test
    void shouldThrowIfPositionNull() {
        assertThrows(IllegalArgumentException.class, 
            () -> new PlayerImpl(null, 3, 0, emptyInventory()));
    }

    @Test
    void shouldThrowIfInventoryNull() {
        assertThrows(IllegalArgumentException.class, 
            () -> new PlayerImpl(pos(0, 0), 3, 0, null));
    }

    @Test
    void moveToShouldChangeOnlyPosition() {
        final var player = basicPlayer();
        final var newPos = pos(4, 7);
        final var updated = player.moveTo(newPos);
        assertNotSame(player, updated);
        assertEquals(newPos, updated.position());
        assertEquals(player.livesCount(), updated.livesCount());
        assertEquals(player.goldCount(), updated.goldCount());
        assertEquals(player.inventory(), updated.inventory());
        assertEquals(pos(0, 0), player.position());
    }

    @Test
    void moveToShouldThrowIfNullPosition() {
        final var player = basicPlayer();
        assertThrows(
            IllegalArgumentException.class,
            () -> player.moveTo(null).hashCode()
        );
    }

    @Test
    void moveToSamePositionStillReturnsNewInstance() {
        final var player = basicPlayer();
        final var updated = player.moveTo(pos(0, 0));
        assertNotSame(player, updated);
        assertEquals(player, updated);
    }

    @Test
    void addGoldShouldIncreaseGoldWhenNumPositive() {
        final var player = basicPlayer();
        final var updated = player.addGold(3);
        assertNotSame(player, updated);
        assertEquals(3, updated.goldCount());
        assertEquals(player.position(), updated.position());
        assertEquals(player.livesCount(), updated.livesCount());
        assertSame(player.inventory(), updated.inventory());
        assertEquals(0, player.goldCount());
    }

    @Test
    void addGoldShouldDecreaseGoldWhenNumNegative() {
        final var player = new PlayerImpl(pos(0, 0), 3, 4, emptyInventory());
        final var updated = player.addGold(-3);
        assertNotSame(player, updated);
        assertEquals(1, updated.goldCount());
        assertEquals(player.position(), updated.position());
        assertEquals(player.livesCount(), updated.livesCount());
        assertSame(player.inventory(), updated.inventory());
        assertEquals(4, player.goldCount());
    }

    @Test
    void addGoldShouldThrowWhenResultNegative() {
        final var player = basicPlayer();
        assertThrows(
            IllegalArgumentException.class,
            () -> player.addGold(ONE_NEGATIVE).hashCode()
        );
    }

    @Test
    void addGoldShouldAllowValueZero() {
        final var player = new PlayerImpl(pos(0, 0), 3, 2, emptyInventory());
        final var updated = player.addGold(-2);
        assertEquals(0, updated.goldCount());
    }

    @Test
    void addGoldZeroShouldReturnSameNewInstance() {
        final var player = new PlayerImpl(pos(3, 4), 2, 3, emptyInventory());
        final var updated = player.addGold(0);
        assertEquals(player, updated);
        assertNotSame(player, updated);
    }

    @Test
    void addLivesShouldIncreaseWhenPositive() {
        final var player = basicPlayer();
        final var updated = player.addLives(1);
        assertNotSame(player, updated);
        assertEquals(4, updated.livesCount());
        assertEquals(player.position(), updated.position());
        assertEquals(player.goldCount(), updated.goldCount());
        assertSame(player.inventory(), updated.inventory());
        assertEquals(3, player.livesCount());
    }

    @Test
    void addLivesShouldDecreaseWhenNegative() {
        final var player = basicPlayer();
        final var updated = player.addLives(-1);
        assertNotSame(player, updated);
        assertEquals(2, updated.livesCount());
        assertEquals(player.position(), updated.position());
        assertEquals(player.goldCount(), updated.goldCount());
        assertSame(player.inventory(), updated.inventory());
        assertEquals(3, player.livesCount());
    }

    @Test
    void addLivesShouldAllowReachingZero() {
        final var player = new PlayerImpl(pos(0, 0), 1, 0, emptyInventory());
        final var updated = player.addLives(ONE_NEGATIVE);
        assertEquals(0, updated.livesCount());
    }

    @Test
    void addLivesShouldThrowWhenNegativeOrZero() {
        final var player = new PlayerImpl(pos(0, 0), 0, 0, emptyInventory());
        assertThrows(
            IllegalArgumentException.class,
            () -> player.addLives(ONE_NEGATIVE).hashCode()
        );
    }

    @Test
    void addLivesWithZeroShouldReturnSameNewInstance() {
        final var player = new PlayerImpl(pos(2, 3), 3, 2, emptyInventory());
        final var updated = player.addLives(0);
        assertNotSame(player, updated);
        assertEquals(player, updated);
    }

    @Test
    void addItemShouldUpdateInventory() {
        final var player = basicPlayer();
        final var updated = player.addItem(StubItem.SHIELD, 1);
        assertEquals(0, player.inventory().quantity(StubItem.SHIELD));
        assertEquals(1, updated.inventory().quantity(StubItem.SHIELD));
    }

    @Test
    void useItemShouldUpdateInventory() {
        final var player = basicPlayer().addItem(StubItem.SHIELD, 1);
        final var updated = player.useItem(StubItem.SHIELD, 1);
        assertNotSame(player, updated);
        assertNotEquals(player.inventory(), updated.inventory());
    }

    @Test
    void equalsShouldBeTrueForSameState() {
        final var inventory = emptyInventory();
        final var player = new PlayerImpl(pos(1, 1), 3, 0, inventory);
        final var updated = new PlayerImpl(pos(1, 1), 3, 0, inventory);
        assertEquals(player, updated);
        assertEquals(player.hashCode(), updated.hashCode());
    }

    @Test
    void equalsShouldBeFalseIfAnyFieldDiffers() {
        final var inventory = emptyInventory();
        final var base = new PlayerImpl(pos(1, 1), 3, 0, inventory);
        assertNotEquals(base, new PlayerImpl(pos(2, 1), 3, 0, inventory));
        assertNotEquals(base, new PlayerImpl(pos(1, 1), 4, 0, inventory));
        assertNotEquals(base, new PlayerImpl(pos(1, 1), 3, 4, inventory));
    }

    @Test
    void equalsShouldHandleNull() {
        final var player = basicPlayer();
        assertNotEquals(player, null);
        assertNotEquals(player, "not a player");
    }

    @Test
    void playerToStringContainsKeyFields() {
        final var player = new PlayerImpl(pos(1, 2), 3, 4, emptyInventory());
        final var string = player.toString();
        assertNotNull(string);
        assertTrue(string.contains("lives="));
        assertTrue(string.contains("gold="));
        assertTrue(string.contains("position="));
        assertTrue(string.contains("inventory="));
    }

    @Test
    void withInventoryShouldThrowIfNull() {
        final var player = basicPlayer();
        assertThrows(IllegalArgumentException.class, 
            () -> player.withInventory(null)
        );
    }

    @Test
    void withInventoryShouldReplaceInventoryInNewInstance() {
        final var player = basicPlayer();
        final Inventory newInv = emptyInventory().add(StubItem.SHIELD, 1);
        final var updated = player.withInventory(newInv);
        assertNotSame(player, updated);
        assertSame(newInv, updated.inventory());
    }

    @Test
    void withInventoryShouldNotChangeOtherFields() {
        final var inventory = emptyInventory();
        final var player = new PlayerImpl(pos(4, 7), 2, 8, inventory);
        final Inventory newInv = emptyInventory().add(StubItem.SHIELD, 1);
        final var updated = player.withInventory(newInv);
        assertEquals(player.position(), updated.position());
        assertEquals(player.livesCount(), updated.livesCount());
        assertEquals(player.goldCount(), updated.goldCount());
        assertSame(newInv, updated.inventory());
    }
}
