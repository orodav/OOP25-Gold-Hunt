// AZZU

package it.unibo.goldhunt.board.api;

import it.unibo.goldhunt.engine.api.Position;

public interface ReadOnlyBoard {

    int boardSize();

    ReadOnlyCell cellAt(Position p);
}
