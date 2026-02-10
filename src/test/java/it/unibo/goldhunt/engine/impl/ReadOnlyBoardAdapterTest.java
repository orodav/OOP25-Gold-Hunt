package it.unibo.goldhunt.engine.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import it.unibo.goldhunt.board.api.Board;
import it.unibo.goldhunt.board.api.Cell;
import it.unibo.goldhunt.board.api.ReadOnlyCell;
import it.unibo.goldhunt.engine.api.Position;
import it.unibo.goldhunt.items.api.CellContent;
import it.unibo.goldhunt.player.api.PlayerOperations;

public class ReadOnlyBoardAdapterTest {

    private static final class TestContent implements CellContent {

        private final String id;
        TestContent(final String id) {
            this.id = id;
        }

        @Override
        public PlayerOperations applyEffect(PlayerOperations player) {
            return player;
        }

        @Override
        public String shortString() {
            return this.id;
        }
    }

    private static final class TestCell implements Cell {

        private final boolean revealed;
        private final boolean flagged;
        private final int adjacentTraps;
        private final Optional<CellContent> content;

        TestCell(
            final boolean revealed,
            final boolean flagged,
            final int adjacentTraps,
            final Optional<CellContent> content
        ) {
            this.revealed = revealed;
            this.flagged = flagged;
            this.adjacentTraps = adjacentTraps;
            this.content = content;
        }

        @Override
        public void reveal() {
            throw new UnsupportedOperationException("not needed");
        }

        @Override
        public boolean isRevealed() {
            return this.revealed;
        }

        @Override
        public void toggleFlag() {
            throw new UnsupportedOperationException("not needed");
        }
    
        @Override
        public boolean isFlagged() {
            return this.flagged;
        }

        @Override
        public int getAdjacentTraps() {
            return this.adjacentTraps;
        }

        @Override
        public void setAdjacentTraps(int n) {
            throw new UnsupportedOperationException("not needed");
        }

        @Override
        public boolean hasContent() {
            return this.content.isPresent();
        }

        @Override
        public Optional<CellContent> getContent() {
            return this.content;
        }

        @Override
        public void setContent(CellContent content) {
            throw new UnsupportedOperationException("not needed");
        }

        @Override
        public void removeContent() {
            throw new UnsupportedOperationException("not needed");
        }
    }

    private static final class TestBoard implements Board {

        private final int size;
        private final Cell cell;

        TestBoard(final int size, final Cell cell) {
            this.size = size;
            this.cell = cell;
        }

        @Override
        public List<Cell> getBoardCells() {
            throw new UnsupportedOperationException("not needed");
        }

        @Override
        public Cell getCell(Position p) {
            return this.cell;
        }

        @Override
        public Position getCellPosition(Cell cell) {
            throw new UnsupportedOperationException("not needed");
        }

        @Override
        public List<Cell> getAdjacentCells(Position p) {
            throw new UnsupportedOperationException("not needed");
        }

        @Override
        public int getBoardSize() {
            return this.size;
        }

        @Override
        public List<Cell> getRow(int index) {
            throw new UnsupportedOperationException("not needed");
        }

        @Override
        public List<Cell> getColumn(int index) {
            throw new UnsupportedOperationException("not needed");
        }

        @Override
        public boolean isPositionValid(Position p) {
            throw new UnsupportedOperationException("not needed");
        }

        @Override
        public boolean isAdjacent(Position p1, Position p2) {
            throw new UnsupportedOperationException("not needed");
        }

    }

    @Test
    void constructorShouldThrowIfBoardNull() {
        assertThrows(NullPointerException.class, 
            () -> new ReadOnlyBoardAdapter(null)
        );
    }

    @Test
    void constructorShouldThrowIfPositionNull() {
        final var adapter = new ReadOnlyBoardAdapter(
            new TestBoard(3, new TestCell(
                false, false, 0, Optional.empty())));
        assertThrows(NullPointerException.class, 
            () -> adapter.cellAt(null)
        );
    }

    @Test
    void boardSizeShouldDelegateBoard() {
        final var adapter = new ReadOnlyBoardAdapter(
            new TestBoard(7, new TestCell(
                false, false, 0, Optional.empty()))
        );
        assertEquals(7, adapter.boardSize());
    }

    @Test
    void cellAtShouldExposeReadOnlyCellDelegatingToCell() {
        final Cell cell = new TestCell(
            true, false, 5, Optional.empty()
        );
        final var adapter = new ReadOnlyBoardAdapter(
            new TestBoard(3, cell)
        );
        final ReadOnlyCell roCell = adapter.cellAt(new Position(0, 0));
        assertTrue(roCell.isRevealed());
        assertFalse(roCell.isFlagged());
        assertEquals(5, roCell.getAdjacentTraps());
        assertTrue(roCell.contentID().isEmpty());
    }

    @Test
    void contentIDShouldExposeShortString() {
        final CellContent content = new TestContent("Test");
        final Cell cell = new TestCell(
            false, true, 1, Optional.of(content));
        final var adapter = new ReadOnlyBoardAdapter(new TestBoard(
            3, cell));
        final ReadOnlyCell roCell = adapter.cellAt(new Position(0, 0));
        assertEquals(Optional.of("Test"), roCell.contentID());
    }

    @Test
    void cellAtShouldThrowIfBoardReturnNullCell() {
        final Board board = new TestBoard(3, null);
        final var adapter = new ReadOnlyBoardAdapter(board);
        assertThrows(NullPointerException.class, 
            () -> adapter.cellAt(new Position(0, 0))
        );
    }
}
