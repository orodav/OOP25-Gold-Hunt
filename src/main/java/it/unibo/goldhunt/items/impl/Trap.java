package it.unibo.goldhunt.items.impl;

import it.unibo.goldhunt.items.api.Revealable;
import it.unibo.goldhunt.player.api.PlayerOperations;

/**
 * Implementation of a Trap item.
 * 
 * <p>
 * A trap is a revealable item that deals damage to the player
 * when its effect is applied.
 */
public class Trap implements Revealable {

    public static final int DAMAGE = -1;

    private PlayerOperations playerop;

    /**
     * Private constructor.
     */
    Trap() {
        //empty constructor for factory instantiation
    }

    /**
     * Binds a player to this trap.
     * 
     * @param playero the player to bind.
     */
    public void bind(final PlayerOperations playero) {
        this.playerop = playero;
    }

    /**
     * Reduces the player's health by the damage amount.
     * 
     * @param playero the player who triggered the trap.
     * @return the updated player state.
     * @throws IllegalStateException if playerop is null.
     */
    @Override
    public PlayerOperations applyEffect(final PlayerOperations playero) {
        if (this.playerop == null) {
            throw new IllegalStateException("cannot bind items");
        }
        return playero.addLives(DAMAGE);
    }

    /**
     * Returns a short string representing the item.
     * 
     * @return "T"
     */
    @Override
    public String shortString() {
        return "T";
    }

    /**
     * @return always true since this is a trap.
     */
    @Override
    public boolean isTrap() {
        return true;
    }
}
