package it.unibo.goldhunt.engine.impl;

import java.util.Objects;

import it.unibo.goldhunt.engine.api.GameState;
import it.unibo.goldhunt.engine.api.Status;
import it.unibo.goldhunt.player.api.Player;
import it.unibo.goldhunt.board.api.ReadOnlyBoard;

public record GameStateImpl(
    ReadOnlyBoard board,
    Player player,
    Status status
) implements GameState {
    
    public GameStateImpl {
        Objects.requireNonNull(board, "board can't be null");
        Objects.requireNonNull(player, "player can't be null");
        Objects.requireNonNull(status, "status can't be null");
    }
}
