package it.unibo.goldhunt.items.impl;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.goldhunt.board.api.Board;
import it.unibo.goldhunt.board.api.Cell;
import it.unibo.goldhunt.engine.api.Position;
import it.unibo.goldhunt.items.api.CellContent;
import it.unibo.goldhunt.items.api.ItemContext;
import it.unibo.goldhunt.items.api.ItemTypes;
import it.unibo.goldhunt.items.api.Revealable;
import it.unibo.goldhunt.player.api.Inventory;
import it.unibo.goldhunt.player.api.PlayerOperations;

/**
 * Test suite for the Chart item.
 * Verifies that the chart correctly identifies and flags adjacent traps.
 */
class ChartTest {

    private static final int DEFAULT_BOARD_SIZE = 5;
    private static final int PLAYER_POS_COORD = 2;
    private static final int ADJACENT_COORD = 3;

    private Chart chart;
    private Board board;

    /**
     * Setup for each test: initializes a fake board, player, and the chart context.
     */
    @BeforeEach
    void init() {
        board = new FakeBoard(DEFAULT_BOARD_SIZE);
        final FakePlayer player = new FakePlayer(new Position(PLAYER_POS_COORD, PLAYER_POS_COORD));
        chart = new Chart();
        chart.bind(new ItemContext(board, player, null));
    }

    /**
     * Tests if the chart flags a cell when a trap is located in an adjacent position.
     */
    @Test
    void testApplyEffectTrap() {
        final TrapFake trap = new TrapFake();
        final Position trapPos = new Position(PLAYER_POS_COORD, ADJACENT_COORD);
        final Cell targetCell = board.getCell(trapPos);
        final PlayerOperations playerop = new FakePlayer(trapPos);
        targetCell.setContent(trap);

        chart.applyEffect(playerop);

        assertTrue(targetCell.isFlagged(), "Trap should be revealed (flagged)");
    }

    /**
     * Verifies that the chart does not flag cells that do not contain traps.
     */
    @Test
    void testApplyEffectNormalCells() {
        final Position emptyPos = new Position(1, 1);
        final Cell emptyCell = board.getCell(emptyPos);
        final FakePlayer player = new FakePlayer(emptyPos);
        chart.applyEffect(player);

        assertFalse(emptyCell.isFlagged(), "Empty cells should not be flagged");
    }

    /**
     * Mock implementation of a Cell for testing purposes.
     */
    static class CellFake implements Cell {
        private CellContent content;
        private boolean flagged;

        @Override
        public void reveal() {
        }

        @Override
        public boolean isRevealed() {
            return false;
        }

        @Override
        public void toggleFlag() {
            flagged = !flagged;
        }

        @Override
        public boolean isFlagged() {
            return flagged;
        }

        @Override
        public int getAdjacentTraps() {
            return 0;
        }

        @Override
        public void setAdjacentTraps(final int n) {
        }

        @Override
        public boolean hasContent() {
            return content != null;
        }

        @Override
        public Optional<CellContent> getContent() {
            return Optional.ofNullable(content);
        }

        @Override
        public void setContent(final CellContent content) {
            this.content = content;
        }

        @Override
        public void removeContent() {
            content = null;
        }
    }

    /**
     * Mock implementation of the Board for testing purposes.
     */
    static class FakeBoard implements Board {
        private final int size;
        private final Map<Position, Cell> cells = new HashMap<>();

        FakeBoard(final int size) {
            this.size = size;
        }

        @Override
        public Cell getCell(final Position p) {
            return cells.computeIfAbsent(p, k -> new CellFake());
        }

        @Override
        public int getBoardSize() {
            return size;
        }

        @Override
        public boolean isPositionValid(final Position p) {
            return p.x() >= 0 && p.x() < size && p.y() >= 0 && p.y() < size;
        }

        @Override
        public List<Cell> getBoardCells() {
            throw new UnsupportedOperationException("Unimplemented method 'getBoardCells'");
        }

        @Override
        public Position getCellPosition(final Cell cell) {
            for (final Map.Entry<Position, Cell> entry : cells.entrySet()) {
                if (entry.equals(cell)) {
                    return entry.getKey();
                }
            }
            return null;
        }

        @Override
        public List<Cell> getAdjacentCells(final Position p) {
            final List<Cell> adj = new ArrayList<>();
            final int[] dx = {-1, 0, 1, 0};
            final int[] dy = {0, -1, 0, 1};

            for (int i = 0; i < 4; i++) {
                final int nborx = p.x() + dx[i];
                final int nbory = p.y() + dy[i];
                final Position nborp = new Position(nborx, nbory);
                if (isPositionValid(nborp)) {
                    adj.add(getCell(nborp));
                }
            }
            return adj;
        }

        @Override
        public List<Cell> getRow(final int index) {
            throw new UnsupportedOperationException("Unimplemented method 'getRow'");
        }

        @Override
        public List<Cell> getColumn(final int index) {
            throw new UnsupportedOperationException("Unimplemented method 'getColumn'");
        }

        @Override
        public boolean isAdjacent(final Position p1, final Position p2) {
            throw new UnsupportedOperationException("Unimplemented method 'isAdjacent'");
        }
    }

    /**
     * Mock implementation of PlayerOperations for testing purposes.
     */
    static class FakePlayer implements PlayerOperations {
        private final Position pos;

        FakePlayer(final Position pos) {
            this.pos = pos;
        }

        @Override
        public Position position() {
            return this.pos;
        }

        @Override
        public int livesCount() {
            throw new UnsupportedOperationException("Unimplemented method 'livesCount'");
        }

        @Override
        public int goldCount() {
            throw new UnsupportedOperationException("Unimplemented method 'goldCount'");
        }

        @Override
        public Inventory inventory() {
            throw new UnsupportedOperationException("Unimplemented method 'inventory'");
        }

        @Override
        public PlayerOperations withInventory(final Inventory inventory) {
            throw new UnsupportedOperationException("Unimplemented method 'withInventory'");
        }

        @Override
        public PlayerOperations moveTo(final Position p) {
            throw new UnsupportedOperationException("Unimplemented method 'moveTo'");
        }

        @Override
        public PlayerOperations addGold(final int num) {
            throw new UnsupportedOperationException("Unimplemented method 'addGold'");
        }

        @Override
        public PlayerOperations addLives(final int num) {
            throw new UnsupportedOperationException("Unimplemented method 'addLives'");
        }

        @Override
        public PlayerOperations addItem(final ItemTypes item, final int quantity) {
            throw new UnsupportedOperationException("Unimplemented method 'addItem'");
        }

        @Override
        public PlayerOperations useItem(final ItemTypes item, final int quantity) {
            throw new UnsupportedOperationException("Unimplemented method 'useItem'");
        }
    }

    /**
     * Mock implementation of a Trap (Revealable) for testing purposes.
     */
    static class TrapFake implements Revealable {
        private boolean revealed;

        @Override
        public String shortString() {
            return "T";
        }

        public boolean revealed() {
            return revealed;
        }

        @Override
        public PlayerOperations applyEffect(final PlayerOperations playerop) {
            return playerop;
        }
    }
}
