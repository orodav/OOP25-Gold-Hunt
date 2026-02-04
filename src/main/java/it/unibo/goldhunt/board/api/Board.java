package it.unibo.goldhunt.board.api;

import java.util.List;

import it.unibo.goldhunt.engine.api.Position;

public interface Board {

    Cell[][] getBoard();

    List<Cell> getBoardCells();

    Cell getCell(Position p);

    Position getCellPosition(Cell cell);

    void setCell(Cell cell, Position p);

    List<Cell> getAdjacentCells(Position p);

    int getBoardSize();

    List<Cell> getRow(int index);

    List<Cell> getColumn(int index);

    boolean isPositionValid(Position p);

    boolean isAdjacent(Position p1, Position p2);

}
