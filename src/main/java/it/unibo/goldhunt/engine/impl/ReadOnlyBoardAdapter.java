package it.unibo.goldhunt.engine.impl;

import java.util.Objects;
import java.util.Optional;

import it.unibo.goldhunt.board.api.Board;
import it.unibo.goldhunt.board.api.Cell;
import it.unibo.goldhunt.board.api.ReadOnlyBoard;
import it.unibo.goldhunt.board.api.ReadOnlyCell;
import it.unibo.goldhunt.engine.api.Position;



public final class ReadOnlyBoardAdapter implements ReadOnlyBoard {

    private final Board board;

    public ReadOnlyBoardAdapter(final Board board) {
        this.board = Objects.requireNonNull(board, "board can't be null");
    }

    public ReadOnlyCell cellAt(final Position p) {
        Objects.requireNonNull(p, "position can't be null");
        final Cell cell = this.board.getCell(p);
        return new ReadOnlyCellAdapter(cell);
    }

    @Override
    public int boardSize() {
        return this.board.getBoardSize();
    }

    private static final class ReadOnlyCellAdapter implements ReadOnlyCell {

        private final Cell cell;

        private ReadOnlyCellAdapter(final Cell cell) {
            this.cell = Objects.requireNonNull(cell, "cell can't be null");
        }

        @Override
        public boolean isRevealed() {
            return this.cell.isRevealed();
        }

        @Override
        public boolean isFlagged() {
            return this.cell.isFlagged();
        }

        @Override
        public int adjacentTraps() {
            return this.cell.getAdjacentTraps();
        }

        @Override
        public Optional<String> contentID() {
            return this.cell.getContent().map(c -> c.shortString());
        }
    }
}
