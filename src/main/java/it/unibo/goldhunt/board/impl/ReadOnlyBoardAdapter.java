// AZZU

package it.unibo.goldhunt.board.impl;

import java.util.Objects;

import it.unibo.goldhunt.board.api.Board;
import it.unibo.goldhunt.board.api.ReadOnlyBoard;
import it.unibo.goldhunt.board.api.ReadOnlyCell;
import it.unibo.goldhunt.engine.api.Position;

public class ReadOnlyBoardAdapter implements ReadOnlyBoard {

    private final Board board;

    public ReadOnlyBoardAdapter(final Board board) {
        Objects.requireNonNull(board);
        this.board = board;
    }

    @Override
    public int boardSize() {
        return this.board.getBoardSize();
    }

    @Override
    public ReadOnlyCell cellAt(final Position p) {
        return new ReadOnlyCellAdapter(board.getCell(p));
    }

}
