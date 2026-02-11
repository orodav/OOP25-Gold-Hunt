package it.unibo.goldhunt.engine.api;

import java.util.List;
import java.util.Optional;

import it.unibo.goldhunt.player.api.Player;

/**
 * Defines the movement logic applied by the engine.
 * 
 * {@code MovementRules} encaspulates all constraints and policies
 * governing how a {@link Player} can move on the board.
 * It separates path computation and rule validation from the
 * engine, promoting modularity and testability.
 */
public interface MovementRules {

    /**
     * Determines whether the target position is reachable from
     * the starting position acording to the movement rules.
     * 
     * @param from the starting position
     * @param to the target position
     * @param player the player attempting the movement
     * @return {@code true} if the target is accessible, 
     *         {@code false} otherwise
     * @throws IllegalArgumentException if any argument is {@code null}
     */
    boolean isReachable(Position from, Position to, Player player);

    /**
     * Determines whether the player is allowed to enter the target position.
     * 
     * This method checks local constraints
     * 
     * @param from the starting position
     * @param to the target position
     * @param player the player attempting the movement
     * @return {@code true} if the player can enter the target position,
     *         {@code false} otherwise
     * @throws IllegalArgumentException if any argument is {@code null}
     */
    boolean canEnter(Position from, Position to, Player player);

    /**
     * Determines whether movement must stop upon reaching the 
     * specified position.
     * 
     * This can be used for special tiles.
     * 
     * @param p the position being evaluated
     * @param player the player currently moving
     * @return {@code true} if movement must stop at {@code p}
     *         {@code false} otherwise
     * @throws IllegalArgumentException if any argument is {@code null}
     */
    boolean mustStopOn(Position p, Player player);

    /**
     * Computes the next single step from the starting position.
     * 
     * @param from the starting position
     * @param to the target position
     * @param player the player attempting the movement
     * @return an {@link Optional} containing the next step position,
     *         or {@link Optional#empty()} if no valid step exists
     * @throws IllegalArgumentException if any argument is {@code null}
     */
    Optional<Position> nextUnitaryStep(Position from, Position to, Player player);

    /**
     * Calculaters a valid path from the starting position to
     * the target position according to the movement rules.
     * 
     * @param from the starting position
     * @param to the target position
     * @param player the player attempting the movement
     * @return an {@link Optional} containing the ordered list of positions,
     *         or {@link Optional#empty()} if no valid path exists
     */
    Optional<List<Position>> pathCalculation(Position from, Position to, Player player);
}
