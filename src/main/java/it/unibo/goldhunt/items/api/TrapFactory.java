package it.unibo.goldhunt.items.api;

import it.unibo.goldhunt.player.api.PlayerOperations;

/**
 * Factory interface to create traps
 * <p>
 * Implementation of this interface produce {@link Revealable} traps
 * that can be placed in game cells.
 */
public interface TrapFactory {


    Revealable createTrap(PlayerOperations playerop);
}
