/*
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
import it.unibo.goldhunt.engine.api.LevelState;
import it.unibo.goldhunt.engine.api.MovementRules;
import it.unibo.goldhunt.engine.api.Position;
import it.unibo.goldhunt.engine.api.Status;
import it.unibo.goldhunt.items.api.CellContent;
import it.unibo.goldhunt.player.api.Inventory;
import it.unibo.goldhunt.player.api.Player;
import it.unibo.goldhunt.player.api.PlayerOperations;
import it.unibo.goldhunt.player.impl.InventoryImpl;
import it.unibo.goldhunt.player.impl.PlayerImpl;

public class EngineTest {

    private PlayerOperations player;
    private Status status;
    private TestBoard board;
    private TestRules rules;
    private TestStrategy strategy;
    private Position start;
    private Position exit;

    private static PlayerOperations makePlayer(final Position p) {
        final Inventory inventory = new InventoryImpl();
        return new PlayerImpl(p, 3, 0, inventory);
    }

    private static final class TestRules implements MovementRules {
        Optional<List<Position>> path = Optional.empty();
        boolean canEnter = true;
        Set<Position> warnings = Set.of();

        @Override
        public Optional<List<Position>> pathCalculation(
            final Position from,
            final Position to,
            final Player player
        ) {
            return path;
        }

        @Override
        public boolean canEnter(final Position from, final Position to, final Player player) {
            return canEnter;
        }

        @Override
        public boolean mustStopOn(final Position p, final Player player) {
            return warnings.contains(p);
        }

        @Override
        public boolean isReachable(Position from, Position to, Player player) {
            throw new UnsupportedOperationException("Unimplemented method 'isReachable'");
        }

        @Override
        public Optional<Position> nextUnitaryStep(Position from, Position to, Player player) {
            throw new UnsupportedOperationException("Unimplemented method 'nextUnitaryStep'");
        }
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
            if (!flagged) {
                revealed = true;
            }
        }

        @Override
        public boolean isRevealed() {
            return revealed;
        }

        @Override
        public void toggleFlag() {
            if (!revealed) {
                flagged = !flagged;
            }
        }

        @Override
        public boolean isFlagged() {
            return flagged;
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
        this.start = new Position(0, 0);
        this.exit = new Position(0, 2);
        this.player = makePlayer(this.start);
        this.rules = new TestRules();
        this.strategy = new TestStrategy();
        final Position p00 = new Position(0, 0);
        final Position p01 = new Position(0, 1);
        final Position p02 = new Position(0, 2);
        this.board = new TestBoard(
            Set.of(p00, p01, p02), 
            Map.of(
                p00, new TestCell(false, false),
                p01, new TestCell(false, false),
                p02, new TestCell(false, false)
            )
        );
    }
/*
    private EngineImpl makeEngine() {
        return new EngineImpl(
            this.player, 
            this.status, 
            this.board, 
            this.rules, 
            this.strategy, 
            this.start, 
            this.exit
        );
    }

    @Test
    void contructorShouldThrowIfAnyDependencyNull() {
        assertThrows(IllegalArgumentException.class, 
            () -> new EngineImpl(
                null,
                this.status,
                this.board,
                this.rules,
                this.strategy,
                this.start,
                this.exit
            )
        );
        assertThrows(IllegalArgumentException.class, 
            () -> new EngineImpl(
                this.player,
                null,
                this.board,
                this.rules,
                this.strategy,
                this.start,
                this.exit
            )
        );
        assertThrows(IllegalArgumentException.class, 
            () -> new EngineImpl(
                this.player,
                this.status,
                null,
                this.rules,
                this.strategy,
                this.start,
                this.exit
            )
        );
        assertThrows(IllegalArgumentException.class, 
            () -> new EngineImpl(
                this.player,
                this.status,
                this.board,
                null,
                this.strategy,
                this.start,
                this.exit
            )
        );
        assertThrows(IllegalArgumentException.class, 
            () -> new EngineImpl(
                this.player,
                this.status,
                this.board,
                this.rules,
                null,
                this.start,
                this.exit
            )
        );
        assertThrows(IllegalArgumentException.class, 
            () -> new EngineImpl(
                this.player,
                this.status,
                this.board,
                this.rules,
                this.strategy,
                null,
                this.exit
            )
        );
        assertThrows(IllegalArgumentException.class, 
            () -> new EngineImpl(
                this.player,
                this.status,
                this.board,
                this.rules,
                this.strategy,
                this.start,
                null
            )
        );
    }

    @Test
    void revealShouldCallStrategyAndReturnApplied() {
        final EngineImpl engine = makeEngine();
        final Position p = new Position(0, 1);
        final ActionResult ar = engine.reveal(p);
        assertEquals(LevelState.PLAYING, ar.levelState());
        assertEquals(ActionEffect.APPLIED, ar.effect());
        assertEquals(1, this.strategy.calls);
        assertEquals(p, this.strategy.lastPos);
    }

    @Test
    void toggleFlagShouldFlipFlagAndReturnConsequential() {
        final EngineImpl engine = makeEngine();
        final Position p = new Position(0, 1);
        final ActionResult firstAR = engine.toggleFlag(p);
        assertEquals(ActionEffect.APPLIED, firstAR.effect());
        assertTrue(this.board.getCell(p).isFlagged());
        final ActionResult secondAR = engine.toggleFlag(p);
        assertEquals(ActionEffect.REMOVED, secondAR.effect());
        assertFalse(this.board.getCell(p).isFlagged());
    }
    */
