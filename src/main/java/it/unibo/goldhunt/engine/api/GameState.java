package it.unibo.goldhunt.engine.api;

import it.unibo.goldhunt.player.api.PlayerOperations;
import it.unibo.goldhunt.shop.api.Shop;

import java.util.Optional;

import it.unibo.goldhunt.board.api.ReadOnlyBoard;

/**
 * Represents an immutable snapshot of the current game state.
 * 
 * <p>
 * A {@code GameState} provides read-only access to all the
 * relevant components of the game at a specific moment in time.
 * It is typically used by UI layers or controllers to observe
 * the state of the game without directly modifying it.
 */
public interface GameState {

    /**
     * Returns the current read-only view of the board.
     * 
     * @return the {@link ReadOnlyBoard} representing the board state
     */
    ReadOnlyBoard board();

    /**
     * Returns the current player instance.
     * 
     * @return the player associated with this game state
     */
    PlayerOperations player();

    /**
     * Returns the current game status.
     * 
     * @return the status describing level state, mode and progression
     */
    Status status();

    /**
     * Returns the current {@link Shop} if the game is in shop mode.
     * 
     * @return an {@link Optional} containing the shop if present,
     *         an {@link Optional#empty()} otherwise
     */
    Optional<Shop> shop();
}
