package it.unibo.goldhunt.engine.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.goldhunt.board.api.Board;
import it.unibo.goldhunt.board.api.Cell;
import it.unibo.goldhunt.board.api.RevealStrategy;
import it.unibo.goldhunt.engine.api.ActionEffect;
import it.unibo.goldhunt.engine.api.ActionResult;
import it.unibo.goldhunt.engine.api.GameMode;
import it.unibo.goldhunt.engine.api.LevelState;
import it.unibo.goldhunt.engine.api.Position;
import it.unibo.goldhunt.engine.api.Status;
import it.unibo.goldhunt.items.api.CellContent;
import it.unibo.goldhunt.player.api.Inventory;
import it.unibo.goldhunt.player.api.PlayerOperations;
import it.unibo.goldhunt.player.impl.InventoryImpl;
import it.unibo.goldhunt.player.impl.PlayerImpl;

public class RevealServiceTest {

    private Status status;
    private TestBoard board;
    private TestStrategy strategy;
    private PlayerOperations player;

    private static PlayerOperations makePlayer(final Position p) {
        final Inventory inventory = new InventoryImpl();
        return new PlayerImpl(p, 3, 0, inventory);
    }

    private static final class TestStrategy implements RevealStrategy {

        int calls;
        Position lastPos;

        @Override
        public void reveal(final Board b, final Position p) {
            this.calls++;
            this.lastPos = p;
        }
    }

    private static final class TestCell implements Cell {
        private boolean flagged;
        private boolean revealed;

        TestCell(final boolean flagged, final boolean revealed) {
            this.flagged = flagged;
            this.revealed = revealed;
        }

        @Override
        public void reveal() {
            if (!this.flagged) {
                this.revealed = true;
            }
        }

        @Override
        public boolean isRevealed() {
            return this.revealed;
        }

        @Override
        public void toggleFlag() {
            if (!this.revealed) {
                this.flagged = !this.flagged;
            }
        }

        @Override
        public boolean isFlagged() {
            return this.flagged;
        }

        @Override
        public int getAdjacentTraps() {
            throw new UnsupportedOperationException("Unimplemented method 'getAdjacentTraps'");
        }

        @Override
        public void setAdjacentTraps(int n) {
            throw new UnsupportedOperationException("Unimplemented method 'setAdjacentTraps'");
        }

        @Override
        public boolean hasContent() {
            throw new UnsupportedOperationException("Unimplemented method 'hasContent'");
        }

        @Override
        public Optional<CellContent> getContent() {
            throw new UnsupportedOperationException("Unimplemented method 'getContent'");
        }

        @Override
        public void setContent(CellContent content) {
            throw new UnsupportedOperationException("Unimplemented method 'setContent'");
        }

        @Override
        public void removeContent() {
            throw new UnsupportedOperationException("Unimplemented method 'removeContent'");
        }
    }

    private static final class TestBoard implements Board {
        
        private final Set<Position> validPos;
        private final Map<Position, TestCell> cells;
        private final Map<Cell, Position> reverse;

        TestBoard(
            final Set<Position> validPos,
            final Map<Position, TestCell> cells
        ) {
            this.validPos = validPos;
            this.cells = new HashMap<>(cells);
            this.reverse = new HashMap<>();
            for (final var e : this.cells.entrySet()) {
                this.reverse.put(e.getValue(), e.getKey());
            }
        }

        @Override
        public boolean isPositionValid(final Position p) {
            return this.validPos.contains(p);
        }

        @Override
        public Cell getCell(final Position p) {
            return this.cells.get(p);
        }

        @Override
        public Position getCellPosition(final Cell cell) {
            return this.reverse.get(cell);
        }

        @Override
        public List<Cell> getBoardCells() {
            throw new UnsupportedOperationException("Unimplemented method 'getBoardCells'");
        }

        @Override
        public List<Cell> getAdjacentCells(Position p) {
            throw new UnsupportedOperationException("Unimplemented method 'getAdjacentCells'");
        }

        @Override
        public int getBoardSize() {
            throw new UnsupportedOperationException("Unimplemented method 'getBoardSize'");
        }

        @Override
        public List<Cell> getRow(int index) {
            throw new UnsupportedOperationException("Unimplemented method 'getRow'");
        }

        @Override
        public List<Cell> getColumn(int index) {
            throw new UnsupportedOperationException("Unimplemented method 'getColumn'");
        }

        @Override
        public boolean isAdjacent(Position p1, Position p2) {
            throw new UnsupportedOperationException("Unimplemented method 'isAdjacent'");
        }
    }

    @BeforeEach
    void init() {
        this.status = StatusImpl.createStartingState();
        this.strategy = new TestStrategy();
        this.player = makePlayer(new Position(0, 0));
        final Position p00 = new Position(0, 0);
        final Position p01 = new Position(0, 1);
        final Map<Position, TestCell> cells = Map.of(
            p00, new TestCell(false, false),
            p01, new TestCell(false, false)
        );
        this.board = new TestBoard(Set.of(p00, p01), cells);
    }

    private RevealService makeService() {
        return new RevealService(
            this.board, 
            this.strategy, 
            () -> this.status, 
            () -> this.player
        );
    }

    @Test
    void revealShouldThrowIfNull() {
        assertThrows(IllegalArgumentException.class, 
            () -> makeService().reveal(null)
        );
    }

    @Test
    void revealShouldReturnBlockedIfNotPlaying() {
        this.status = new StatusImpl(LevelState.WON, GameMode.LEVEL, 1);
        final ActionResult ar = makeService().reveal(new Position(0, 0));
        assertEquals(LevelState.WON, ar.levelState());
        assertEquals(ActionEffect.BLOCKED, ar.effect());
        assertEquals(0, this.strategy.calls);
    }

    @Test
    void revealShouldReturnInvalidIfOutOfBounds() {
        final ActionResult ar = makeService().reveal(new Position(13, 22));
        assertEquals(LevelState.PLAYING, ar.levelState());
        assertEquals(ActionEffect.INVALID, ar.effect());
        assertEquals(0, this.strategy.calls);
    }

    @Test 
    void revealShouldBlockIfCellAlreadyFlagged() {
        final Position p = new Position(0,0);
        final Map<Position, TestCell> newCells = new HashMap<>();
        newCells.put(p, new TestCell(true, false));
        newCells.put(new Position(0, 1), new TestCell(false, false));
        this.board = new TestBoard(Set.of(p, new Position(0, 1)), newCells);
        final ActionResult ar = makeService().reveal(p);
        assertEquals(ActionEffect.BLOCKED, ar.effect());
        assertEquals(0, this.strategy.calls);
    }

    @Test
    void revealShouldBlockIfCellAlreadyRevealed() {
        final Position p = new Position(0, 0);
        final Map<Position, TestCell> newCells = new HashMap<>();
        newCells.put(p, new TestCell(false, true));
        newCells.put(new Position(0, 1), new TestCell(false, false));
        this.board = new TestBoard(Set.of(p, new Position(0, 1)), newCells);
        final ActionResult ar = makeService().reveal(p);
        assertEquals(ActionEffect.BLOCKED, ar.effect());
        assertEquals(0, this.strategy.calls);
    }

    @Test
    void revealShouldApplyStrategyIfOk() {
        final Position p = new Position(0, 0);
        final ActionResult ar = makeService().reveal(p);
        assertEquals(ActionEffect.APPLIED, ar.effect());
        assertEquals(1, this.strategy.calls);
        assertEquals(p, this.strategy.lastPos);
    }

    @Test
    void toggleFlagShouldThrowIfNull() {
        assertThrows(IllegalArgumentException.class, 
            () -> makeService().toggleFlag(null)
        );
    }

    @Test
    void toggleFlagShouldReturnBlockedIfNotPlaying() {
        this.status = new StatusImpl(LevelState.WON, GameMode.LEVEL, 1);
        final ActionResult ar = makeService().toggleFlag(new Position(0, 0));
        assertEquals(LevelState.WON, ar.levelState());
        assertEquals(ActionEffect.BLOCKED, ar.effect());
    }

    @Test
    void toggleFlagShouldReturnInvalidIfOutOfBounds() {
        final ActionResult ar = makeService().toggleFlag(new Position(12, 23));
        assertEquals(ActionEffect.INVALID, ar.effect());
    }

    @Test
    void toggleFlagShouldBlockIfCellRevealed() {
        final Position p = new Position(0, 0);
        final Map<Position, TestCell> newCells = new HashMap<>();
        newCells.put(p, new TestCell(false, true));
        newCells.put(new Position(0, 1), new TestCell(false, false));
        this.board = new TestBoard(Set.of(p, new Position(0, 1)), newCells);
        final ActionResult ar = makeService().reveal(p);
        assertEquals(ActionEffect.BLOCKED, ar.effect());
        assertFalse(((TestCell) this.board.getCell(p)).isFlagged());
    }

    @Test
    void toggleFlagShouldApplyWhenAddingFlag() {
        final Position p = new Position(0, 0);
        final ActionResult ar = makeService().toggleFlag(p);
        assertEquals(ActionEffect.APPLIED, ar.effect());
        assertTrue(((TestCell) this.board.getCell(p)).isFlagged());
    }

    @Test
    void toggleFlagShouldRemoveWhenRemovingFlag() {
        final Position p = new Position(0, 0);
        makeService().toggleFlag(p);
        final ActionResult ar = makeService().toggleFlag(p);
        assertEquals(ActionEffect.REMOVED, ar.effect());
        assertFalse(((TestCell) this.board.getCell(p)).isFlagged());
    }
}
