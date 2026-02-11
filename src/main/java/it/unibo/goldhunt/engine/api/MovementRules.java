package it.unibo.goldhunt.engine.api;

import java.util.List;
import java.util.Optional;

import it.unibo.goldhunt.player.api.Player;

/**
 * Defines the movement validation and path computation logic for a player.
 * 
 * This interface follows the Strategy Pattern: different implementations
 * may provide alternative movement behaviors depending on the game mode,
 * board structure or rule set.
 * 
 * The interface respects the Single Responsibility Principle (SRP) by
 * encapsulating only movement-related rules and calculations.
 */
public interface MovementRules {

    /**
     * Determines whether a target position is theoretically reachable from
     * a starting position, according to movement constraints.
     * 
     * @param from the starting position
     * @param to the target position
     * @param player the player attempting the movement
     * @return {@code true} if position is reachable,
     *         {@code false} otherwise
    */
    boolean isReachable(Position from, Position to, Player player);

    /**
     * Determines whether the player is allowed to enter the target position.
     * 
     * This method checks rule-based constraints.
     * 
     * @param from the current position
     * @param to the target position
     * @param player the player attempting to move
     * @return {@code true} if the player can enter the position,
     *         {code false} otherwise
     */
    boolean canEnter(Position from, Position to, Player player);

    /**
     * Determines whether movement must stop upon reaching
     * the specified position.
     * 
     * @param p the position reached
     * @param player the player moving
     * @return {@code true} if movement must stop on this position
     */
    boolean mustStopOn(Position p, Player player);

    /**
     * Computes the next unitary step from a starting position
     * towards a target position.
     * 
     * @param from the current position
     * @param to the destination position
     * @param player the player attempting to move
     * @return an {@code Optional} containing the next step if valid
     *            {@code Optional#empty()} otherwise
     */
    Optional<Position> nextUnitaryStep(Position from, Position to, Player player);

    /**
     * Computes a valid movement path from a starting position.
     * 
     * @param from the starting position
     * @param to the target position
     * @param player the player attempting to move
     * @return an {@code Optional} containing the ordered list of positions
     *         representing the path if available,
     *            {@code Optional#empty()} otherwise
     */
    Optional<List<Position>> pathCalculation(Position from, Position to, Player player);
}
