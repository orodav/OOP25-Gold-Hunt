package it.unibo.goldhunt.engine.api;

import java.util.List;
import java.util.Optional;

import it.unibo.goldhunt.player.api.Player;

public interface MovementRules {

    /** Implementazione mirata su "Strategy Pattern" 
     * Rispetto del SRP
    */
    boolean isReachable(Position from, Position to, Player player);

    boolean canEnter(Position from, Position to, Player player);

    boolean mustStopOn(Position p, Player player);

    Optional<Position> nextUnitaryStep(Position from, Position to, Player player);

    Optional<List<Position>> pathCalculation(Position from, Position to, Player player);
}
