package it.unibo.goldhunt.items.impl;

import it.unibo.goldhunt.items.api.Revealable;
import it.unibo.goldhunt.items.api.TrapFactory;
import it.unibo.goldhunt.player.api.PlayerOperations;

/**
 * Implementation of a factory that creates and initializes traps.
 */
public class TrapFactoryImpl implements TrapFactory{

    PlayerOperations playerop;

    /**
     * Creates a new factory with a reference to the player.
     * @param playerop the player operations.
     */
    public TrapFactoryImpl(final PlayerOperations playerop){
        this.playerop = playerop;
    }

    /**
     * Creates a new Trap and binds it to a player.
     * @param playerop the player to bind the trap to.
     * @return a new initialized {@link Revealable} trap.
     */
    @Override
    public Revealable createTrap(final PlayerOperations playerop) {
        final var trap = new Trap();
        trap.bind(playerop);
        return trap;
    }
}
