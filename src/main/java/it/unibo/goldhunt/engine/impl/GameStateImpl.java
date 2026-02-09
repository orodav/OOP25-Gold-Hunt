package it.unibo.goldhunt.engine.impl;

import java.util.Objects;
import java.util.Optional;

import it.unibo.goldhunt.engine.api.GameState;
import it.unibo.goldhunt.engine.api.Status;
import it.unibo.goldhunt.player.api.Player;
import it.unibo.goldhunt.shop.api.Shop;
import it.unibo.goldhunt.board.api.ReadOnlyBoard;

public record GameStateImpl(
    ReadOnlyBoard board,
    Player player,
    Status status,
    Optional<Shop> shop
) implements GameState {
    
    public GameStateImpl {
        Objects.requireNonNull(board, "board can't be null");
        Objects.requireNonNull(player, "player can't be null");
        Objects.requireNonNull(status, "status can't be null");
        Objects.requireNonNull(shop, "shop can't be null");
    }
}
