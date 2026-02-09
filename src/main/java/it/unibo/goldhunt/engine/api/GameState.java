package it.unibo.goldhunt.engine.api;

import it.unibo.goldhunt.player.api.Player;
import it.unibo.goldhunt.board.api.ReadOnlyBoard;

/**
 * Read-only snapshot of the current game state.
 */
public interface GameState {

    ReadOnlyBoard board();

    Player player();

    Status status();
}
