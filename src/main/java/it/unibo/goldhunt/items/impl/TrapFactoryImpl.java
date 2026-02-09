package it.unibo.goldhunt.items.impl;

import it.unibo.goldhunt.items.api.Revealable;
import it.unibo.goldhunt.items.api.TrapFactory;
import it.unibo.goldhunt.player.api.PlayerOperations;

public class TrapFactoryImpl implements TrapFactory{

    PlayerOperations playerop;

    public TrapFactoryImpl(PlayerOperations playerop){
        this.playerop = playerop;
    }
    @Override
    public Revealable createTrap(PlayerOperations playerop) {
        var trap = new Trap();
        trap.bind(playerop);
        return trap;
    }
}
