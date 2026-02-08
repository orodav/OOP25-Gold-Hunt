package it.unibo.goldhunt.items.impl;

import it.unibo.goldhunt.items.api.Revealable;
import it.unibo.goldhunt.items.api.TrapFactory;
import it.unibo.goldhunt.player.api.PlayerOperations;

public class TrapFactoryImpl implements TrapFactory{

    private final PlayerOperations playerop;

    public TrapFactoryImpl(PlayerOperations playerop){
        this.playerop = playerop;
    }
    @Override
    public Revealable createTrap() {
        return new Trap(this.playerop);
    }
}
