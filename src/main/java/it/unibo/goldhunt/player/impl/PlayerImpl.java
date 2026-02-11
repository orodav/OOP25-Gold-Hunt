package it.unibo.goldhunt.player.impl;

import java.util.Arrays;

import it.unibo.goldhunt.engine.api.Position;
import it.unibo.goldhunt.items.api.ItemTypes;
import it.unibo.goldhunt.player.api.Inventory;
import it.unibo.goldhunt.player.api.PlayerOperations;

/**
 * Immutable implementation of {@link PlayerOperations}.
 * 
 * <p>
 * All state changes produce a new {@code PlayerImpl} instance.
 */
public final class PlayerImpl implements PlayerOperations {

    private final Position position;
    private final int lives;
    private final int gold;
    private final Inventory inventory;

    /**
     * Creates a player with the specified state.
     * 
     * @param position the player's position
     * @param lives the number of lives
     * @param gold the amount of gold
     * @param inventory the player's inventory
     * @throws IllegalArgumentException if any parameter is invalid
     */
    public PlayerImpl(
            final Position position, 
            final int lives, 
            final int gold, 
            final Inventory inventory) {
                if (position == null || inventory == null) {
                    throw new IllegalArgumentException();
                }
                if (lives < 0 || gold < 0) {
                    throw new IllegalArgumentException();
                }
                this.position = position;
                this.lives = lives;
                this.gold = gold;
                this.inventory = inventory;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Position position() {
        return this.position;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int livesCount() {
        return this.lives;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int goldCount() {
        return this.gold;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Inventory inventory() {
        return this.inventory;
    }

    /*
     * Two players are equal if all their state fields match.
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        final PlayerImpl checkObj = (PlayerImpl) obj;
        return this.position.equals(checkObj.position) 
                && this.lives == checkObj.lives 
                && this.gold == checkObj.gold 
                && this.inventory.equals(checkObj.inventory);
    }

    /*
     * Returns a hash code consistent with {@link #equals(Object)}.
     */
    @Override
    public int hashCode() {
        return Arrays.hashCode(new Object[] {
            this.position, this.lives, this.gold, this.inventory,
        });
    }

    /** 
     * Returns a readable representation of the player state.
     */
    @Override
    public String toString() {
        return "Player[position=" + this.position
                + ", lives=" + this.lives
                + ", gold=" + this.gold
                + ", inventory=" + this.inventory
                + "]";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PlayerImpl moveTo(final Position newPos) {
        return new PlayerImpl(newPos, this.lives, this.gold, this.inventory);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PlayerImpl addGold(final int num) {
        return new PlayerImpl(this.position, this.lives, this.gold + num, this.inventory);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PlayerImpl addLives(final int num) {
        return new PlayerImpl(this.position, this.lives + num, this.gold, this.inventory);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PlayerImpl addItem(final ItemTypes item, final int quantity) {
        return new PlayerImpl(this.position, this.lives, this.gold, this.inventory.add(item, quantity));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PlayerImpl useItem(final ItemTypes item, final int quantity) {
        return new PlayerImpl(this.position, this.lives, this.gold, this.inventory.remove(item, quantity));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PlayerOperations withInventory(final Inventory newInventory) {
        if (inventory == null) {
            throw new IllegalArgumentException("inventory can't be null");
        }
        return new PlayerImpl(this.position, this.lives, this.gold, newInventory);
    }
}
