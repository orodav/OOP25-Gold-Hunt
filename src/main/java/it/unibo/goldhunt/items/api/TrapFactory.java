package it.unibo.goldhunt.items.api;

import it.unibo.goldhunt.player.api.PlayerOperations;

/**
 * Factory interface to create traps
 * 
 * <p>
 * Implementation of this interface produce {@link Revealable} traps
 * that can be placed in game cells.
 */
@FunctionalInterface
public interface TrapFactory {

    /**
     * Creates a new trap instance for the given player.
     * 
     * @param playerop the player context for the trap
     * @return a new {@link Revealable} trap 
     */
    Revealable createTrap(PlayerOperations playerop);
}
