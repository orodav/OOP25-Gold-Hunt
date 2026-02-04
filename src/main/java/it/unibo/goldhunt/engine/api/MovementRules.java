package main.java.it.unibo.goldhunt.engine.api;

import it.unibo.goldhunt.player.api.Player;

public interface MovementRules {

    /** Implementazione mirata su "Strategy Pattern" 
     * Rispetto del SRP
    */
    boolean isReachable(Position from, Position to, Player player);

    boolean canEnter(Position from, Position to, Player player);

    boolean mustStopOn(Position p, Player player);

    /* nell'implementazione: dovrò guardare Inventory, Item, Board */
}
