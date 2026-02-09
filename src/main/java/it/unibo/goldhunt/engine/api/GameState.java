package it.unibo.goldhunt.engine.api;

import it.unibo.goldhunt.player.api.Player;
import it.unibo.goldhunt.board.api.Board;

/**
 * Read-only snapshot of the current game state.
 */
public interface GameState {

    Board board();

    Player player();

    Status status();
}
