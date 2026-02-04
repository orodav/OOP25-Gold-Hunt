package main.java.it.unibo.goldhunt.engine.impl;

import java.util.List;

import it.unibo.goldhunt.board.api.Cell;
import it.unibo.goldhunt.engine.api.Position;
import it.unibo.goldhunt.player.api.Player;

public interface BoardView {

    boolean isPositionValid(Position p);

    boolean isAdjacent(Position from, Position to);

    boolean isBlockedFor(Position p, Player player); //luca

    boolean isStopCellFor(Position p, Player player); //luca

    Iterable<Position> neighborsOf(Position p);
    List<Cell> getAdjacentCells(Position p);

    //sempre qui?
    boolean toggleFlag(Position p);
}
