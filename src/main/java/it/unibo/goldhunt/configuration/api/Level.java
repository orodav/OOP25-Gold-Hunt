package it.unibo.goldhunt.configuration.api;

import it.unibo.goldhunt.board.api.Board;
import it.unibo.goldhunt.engine.api.Position;

public interface Level {
    public void initBoard();

    public void initInventory();

    public void initPlayerPosition();

    public Board getBoard();

    public Position getStart();

    public Position getExit();
}
