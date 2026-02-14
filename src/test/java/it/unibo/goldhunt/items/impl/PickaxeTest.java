package it.unibo.goldhunt.items.impl;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import it.unibo.goldhunt.board.api.Board;
import it.unibo.goldhunt.board.api.Cell;
import it.unibo.goldhunt.engine.api.Position;
import it.unibo.goldhunt.items.api.CellContent;
import it.unibo.goldhunt.items.api.ItemContext;
import it.unibo.goldhunt.items.api.ItemTypes;
import it.unibo.goldhunt.player.api.Inventory;
import it.unibo.goldhunt.player.api.PlayerOperations;

/**
 * Test class for {@link Pickaxe} item.
 * Verifies that the pickaxe correctly reveals and disarms rows or columns on the board.
 */
class PickaxeTest {
    private Pickaxe pick;
    private FakeBoard board;
    private FakePlayer player;

    /**
     * Sets up the test environment with a fake board and player.
     */
    @BeforeEach
    void init() {
        board = new FakeBoard(10);
        pick = new Pickaxe();
        player = new FakePlayer();
        final ItemContext context = new ItemContext(board, null, null);
        pick.bind(context);
    }

    /**
     * Tests multiple times that applying the pickaxe effect disarms at least 
     * one full row or column.
     */
    @RepeatedTest(10)
    void effectPick() {
        pick.applyEffect(player);
        boolean found = false;

        for (int i = 0; i < board.size; i++) {
            if (board.isRowDisarmed(i) || board.isColumnDisarmed(i)) {
                found = true;
                break;
            }
        }
        assertTrue(found, "pickaxe should reveal and disarm a row or a column");
    }

    /**
     * Verifies the pickaxe behavior when used on cells containing traps.
     */
    @Test
    void pickOnTrap() {
        board.getRow(0).forEach(c -> c.setContent(new FakeTrap()));
        pick.applyEffect(player);
    }

    /**
     * Fake trap content for testing disarming logic.
     */
    private static final class FakeTrap implements CellContent {

        @Override
        public PlayerOperations applyEffect(final PlayerOperations playerop) {
            return playerop;
        }

        @Override
        public String shortString() {
            return "T";
        }

        @Override
        public boolean isTrap() {
            return true;
        }
    }

    /**
     * Mock board implementation to track cell states (disarmed/revealed).
     */
    private static final class FakeBoard implements Board {

        private final int size;
        private final Cell[][] cell;

        FakeBoard(final int size) {
            this.size = size;
            cell = new Cell[size][size];
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    cell[i][j] = new FakeCell();

                }
            }
        }

        @Override
        public List<Cell> getBoardCells() {
            throw new UnsupportedOperationException("Unimplemented method 'getBoardCells'");
        }

        @Override
        public Cell getCell(final Position p) {
            throw new UnsupportedOperationException("Unimplemented method 'getCell'");
        }

        @Override
        public Position getCellPosition(final Cell pCell) {
            throw new UnsupportedOperationException("Unimplemented method 'getCellPosition'");
        }

        @Override
        public List<Cell> getAdjacentCells(final Position p) {
            throw new UnsupportedOperationException("Unimplemented method 'getAdjacentCells'");
        }

        boolean isRowDisarmed(final int row) {
            for (final Cell c : getRow(row)) {
                if (!((FakeCell) c).disarmed) {
                    return false;
                }
            }
            return true;
        }

        boolean isColumnDisarmed(final int col) {
            for (final Cell c : getColumn(col)) {
                if (!((FakeCell) c).disarmed) {
                    return false;
                }
            }
            return true;
        }

        @Override
        public int getBoardSize() {
            return size;
        }

        @Override
        public List<Cell> getRow(final int index) {
            final List<Cell> row = new ArrayList<>();
            for (int j = 0; j < size; j++) {
                row.add(cell[index][j]);
            }
            return row;
        }

        @Override
        public List<Cell> getColumn(final int index) {
            final List<Cell> col = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                col.add(cell[i][index]);
            }
            return col;
        }

        @Override
        public boolean isPositionValid(final Position p) {
            throw new UnsupportedOperationException("Unimplemented method 'isPositionValid'");
        }

        @Override
        public boolean isAdjacent(final Position p1, final Position p2) {
            throw new UnsupportedOperationException("Unimplemented method 'isAdjacent'");
        }
    }

    /**
     * Mock cell implementation to simulate revealing and content management.
     */
    private static final class FakeCell implements Cell {

        private boolean disarmed;
        private CellContent content;
        private boolean revealed;

        @Override
        public void reveal() {
            revealed = true;
            disarmed = true;
        }

        @Override
        public boolean isRevealed() {
            return revealed;
        }

        @Override
        public void toggleFlag() {
            throw new UnsupportedOperationException("Unimplemented method 'toggleFlag'");
        }

        @Override
        public boolean isFlagged() {
            throw new UnsupportedOperationException("Unimplemented method 'isFlagged'");
        }

        @Override
        public int getAdjacentTraps() {
            throw new UnsupportedOperationException("Unimplemented method 'getAdjacentTraps'");
        }

        @Override
        public void setAdjacentTraps(final int n) {
            throw new UnsupportedOperationException("Unimplemented method 'setAdjacentTraps'");
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
            this.content = null;
        }
    }

    /**
     * Fake player implementation for effect application tests.
     */
    private static final class FakePlayer implements PlayerOperations {

        @Override
        public Position position() {

            throw new UnsupportedOperationException("Unimplemented method 'position'");
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

        @Override
        public PlayerOperations setLives(final int num) {
            throw new UnsupportedOperationException("Unimplemented method 'setLives'");
        }
    }
}
