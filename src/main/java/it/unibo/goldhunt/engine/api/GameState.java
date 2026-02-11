package it.unibo.goldhunt.engine.api;

import it.unibo.goldhunt.player.api.Player;
import it.unibo.goldhunt.shop.api.Shop;

import java.util.Optional;

import it.unibo.goldhunt.board.api.ReadOnlyBoard;

/**
 * Represents a read-only snapshot of the current game state.
 * 
 * A {@code GameState} provides access to the main components of the game
 * without allowing direct mutation.
 */
public interface GameState {

    /**
     * Returns the read-only representation of the game board.
     * 
     * @return the current board view
     */
    ReadOnlyBoard board();

    /**
     * Returns the current player instance.
     * 
     * @return the player associated with this game state
     */
    Player player();

    /**
     * Returns the current game status.
     * 
     * @return the status describing level state, mode and progression
     */
    Status status();

    /**
     * Returns the shop associated with the current state, if available.
     * 
     * The shop may not be present depending on current {@link GameMode}
     * or game phase.
     * 
     * @return an {@code Optional} containing the shop if present,
     *            {@code Optional#empty()} otherwise
     */
    Optional<Shop> shop();
}
