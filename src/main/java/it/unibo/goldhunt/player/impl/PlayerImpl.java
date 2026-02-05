package it.unibo.goldhunt.player.impl;
//davv
import java.util.Arrays;

import it.unibo.goldhunt.engine.api.Position;
import it.unibo.goldhunt.items.api.ItemTypes;
import it.unibo.goldhunt.player.api.Inventory;
import it.unibo.goldhunt.player.api.PlayerOperations;

public final class PlayerImpl implements PlayerOperations {

    private final Position position;
    private final int lives;
    private final int gold;
    private final Inventory inventory;

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


    @Override
    public Position position() {
        return this.position;
    }

    @Override
    public int livesCount() {
        return this.lives;
    }

    @Override
    public int goldCount() {
        return this.gold;
    }

    @Override
    public Inventory inventory() {
        return this.inventory;
    }

    /*
     * Check value-based equity.
     * Two players are equal if all their state fields are matching.
     * Needed to compare logical game states instead of object identity.
     * Useful for tests, state checks ad deterministic game logic.
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
        return this.position.equals(checkObj.position) &&
                this.lives == checkObj.lives &&
                this.gold == checkObj.gold &&
                this.inventory.equals(checkObj.inventory);
    }

    /*
     * Required for correct behavior in hash-based collections.
     * same player state -> same hash code.
     */
    @Override
    public int hashCode() {
        return Arrays.hashCode(new Object[] {
            this.position, this.lives, this.gold, this.inventory
        });
    }

    /** 
     * Readable representation of the player state.
     * Useful for debugging and test output.
     */
    @Override
    public String toString() {
        return "Player[position=" + this.position
                + ", lives=" + this.lives
                + ", gold=" + this.gold
                + ", inventory=" + this.inventory
                + "]";
    }


     @Override
    public PlayerImpl moveTo(final Position newPos) {
        return new PlayerImpl(newPos, this.lives, this.gold, this.inventory);
    }

    @Override
    public PlayerImpl addGold(int num) {
        return new PlayerImpl(this.position, this.lives, this.gold + num, this.inventory);
    }

    @Override
    public PlayerImpl addLives(int num) {
        return new PlayerImpl(this.position, this.lives + num, this.gold, this.inventory);
    }

    @Override
    public PlayerImpl addItem(final ItemTypes item, final int quantity) {
        return new PlayerImpl(this.position, this.lives, this.gold, this.inventory.add(item, quantity));
    }

    @Override
    public PlayerImpl useItem(final ItemTypes item, final int quantity) {
        return new PlayerImpl(this.position, this.lives, this.gold, this.inventory.remove(item, quantity));
    }
}
