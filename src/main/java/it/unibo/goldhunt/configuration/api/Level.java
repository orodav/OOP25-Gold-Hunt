package it.unibo.goldhunt.configuration.api;

import it.unibo.goldhunt.board.api.Board;
import it.unibo.goldhunt.engine.api.Position;
import it.unibo.goldhunt.player.api.PlayerOperations;

public interface Level {
    public void initBoard();

    public void initPlayerPosition();

    public void initLives();

    public Board getBoard();

    public Position getStart();

    public Position getExit();

    public PlayerOperations getPlayer();
}

