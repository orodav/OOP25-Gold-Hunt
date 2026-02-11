package it.unibo.goldhunt.engine.impl;

import java.util.Objects;
import java.util.Optional;

import it.unibo.goldhunt.engine.api.GameState;
import it.unibo.goldhunt.engine.api.Status;
import it.unibo.goldhunt.player.api.Player;
import it.unibo.goldhunt.shop.api.Shop;
import it.unibo.goldhunt.board.api.ReadOnlyBoard;

/**
 * Immutable implementation of {@link GameState}.
 * 
 * <p>
 * Provides a read-only shapshot of the current game state.
 * 
 * @param board the game board
 * @param player the player
 * @param status the game status
 * @param shop the shop
 */
public record GameStateImpl(
    ReadOnlyBoard board,
    Player player,
    Status status,
    Optional<Shop> shop
) implements GameState {
    /**
     * Creates a new immutable game state snapshot.
     * 
     * @param board the game board
     * @param player the player
     * @param status the game status
     * @param shop the shop
     */
    public GameStateImpl {
        Objects.requireNonNull(board, "board can't be null");
        Objects.requireNonNull(player, "player can't be null");
        Objects.requireNonNull(status, "status can't be null");
        Objects.requireNonNull(shop, "shop can't be null");
    }
}
