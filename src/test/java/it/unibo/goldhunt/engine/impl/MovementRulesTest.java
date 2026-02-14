package it.unibo.goldhunt.engine.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import it.unibo.goldhunt.board.api.Board;
import it.unibo.goldhunt.board.api.Cell;
import it.unibo.goldhunt.engine.api.MovementRules;
import it.unibo.goldhunt.engine.api.Position;
import it.unibo.goldhunt.items.api.CellContent;
import it.unibo.goldhunt.items.api.ItemTypes;
import it.unibo.goldhunt.player.api.Inventory;
import it.unibo.goldhunt.player.api.Player;
import it.unibo.goldhunt.player.api.PlayerOperations;
import it.unibo.goldhunt.player.impl.InventoryImpl;

/**
 * Testing class for MovementRules implementation.
 */
class MovementRulesTest {

    private static final int SEVEN = 7;
    private static final int NINE = 9;

    private Position pos(final int x, final int y) {
        return new Position(x, y);
    }

    @Test
    void shouldThrowIfConstructorBoardNull() {
        assertThrows(IllegalArgumentException.class, 
            () -> new MovementRulesImpl(null)
        );
    }

    @Test
    void constructorWithPredShouldThrowIfNull() {
        final Board imgBoard = new TestBoard(5);
        assertThrows(IllegalArgumentException.class, 
            () -> new MovementRulesImpl(null, (p, pl) -> false, (p, pl) -> false)
        );
        assertThrows(IllegalArgumentException.class, 
            () -> new MovementRulesImpl(
                imgBoard, 
                null,
                (p, pl) -> false
            )
        );
        assertThrows(IllegalArgumentException.class, 
            () -> new MovementRulesImpl(
                imgBoard, 
                (p, pl) -> false,
                null
            )
        );
    }

    @Test
    void canEnterShouldReturnFalseForSamePosition() {
        final MovementRules rules = new MovementRulesImpl(
            new TestBoard(5)
        );
        final Player player = new BasicPlayer(pos(0, 0));
        final Position from = new Position(1, 1);
        final Position to = new Position(1, 1);
        assertNotSame(from, to);
        assertEquals(from, to);
        assertFalse(rules.canEnter(from, to, player));
    }

    @Test
    void canEnterShouldReturnFalseForInvalidTo() {
        final MovementRules rules = new MovementRulesImpl(new TestBoard(4));
        final Player player = new BasicPlayer(pos(0, 0));
        assertFalse(rules.canEnter(pos(0, 0), pos(0, 4), player));
    }

    @Test
    void canEnterShouldRespectBlockedPredicate() {
        final Board b = new TestBoard(5);
        final Player player = new BasicPlayer(pos(0, 0));
        final MovementRules rules = new MovementRulesImpl(
            b,
            (p, pl) -> p.equals(pos(1, 0)),
            (p, pl) -> false
        );
        assertFalse(rules.canEnter(pos(0, 0), pos(1, 0), player));
        assertTrue(rules.canEnter(pos(0, 0), pos(0, 1), player));
    }

    @Test
    void canEnterShouldAllowDiagonalWhenAdjacent() {
        final MovementRules rules = new MovementRulesImpl(new TestBoard(5));
        final Player player = new BasicPlayer(pos(0, 0));
        assertTrue(rules.canEnter(pos(0, 0), pos(1, 1), player));
    }

    @Test
    void canEnterShouldReturnFalseWhenNotAdjacent() {
        final MovementRules rules = new MovementRulesImpl(new TestBoard(4));
        final Player player = new BasicPlayer(pos(0, 0));
        assertFalse(rules.canEnter(pos(0, 0), pos(3, 3), player));
    }

    @Test
    void canEnterShouldThrowIfAnyArgumentNull() {
        final MovementRules rules = new MovementRulesImpl(new TestBoard(3));
        final Player player = new BasicPlayer(pos(0, 0));
        assertThrows(IllegalArgumentException.class, 
            () -> rules.canEnter(null, pos(0, 1), player)
        );
        assertThrows(IllegalArgumentException.class, 
            () -> rules.canEnter(pos(0, 0), null, player)
        );
        assertThrows(IllegalArgumentException.class, 
            () -> rules.canEnter(pos(0, 0), pos(0, 1), null)
        );
    }

    @Test
    void mustStopOnShouldReturnFalseForInvalid() {
        final Board b = new TestBoard(5);
        final Player player = new BasicPlayer(pos(0, 0));
        final MovementRules rules = new MovementRulesImpl(
            b,
            (p, pl) -> false,
            (p, pl) -> p.equals(pos(0, 1))
        );
        assertFalse(rules.mustStopOn(pos(NINE, NINE), player));
        assertTrue(rules.mustStopOn(pos(0, 1), player));
        assertFalse(rules.mustStopOn(pos(1, 1), player));
    }

    @Test
    void pathCalculationShouldReturnEmptyWhenInvalid() {
        final Player player = new BasicPlayer(pos(0, 0));
        final MovementRules rules = new MovementRulesImpl(new TestBoard(2));
        assertTrue(rules.pathCalculation(pos(NINE, NINE), pos(0, 0), player).isEmpty());
        assertTrue(rules.pathCalculation(pos(0, 0), pos(NINE, NINE), player).isEmpty());
        final MovementRules blocked = new MovementRulesImpl(
            new TestBoard(3), 
            (p, pl) -> 
                    p.equals(pos(1, 0))
                    || p.equals(pos(0, 1))
                    || p.equals(pos(1, 1)),
            (p, pl) -> false
        );
        assertTrue(blocked.pathCalculation(pos(0, 0), pos(2, 2), player).isEmpty());
    }

    @Test
    void pathCalculationShouldReturnEmptyWhenFromEqualsTo() {
        final Player player = new BasicPlayer(pos(0, 0));
        final MovementRules rules = new MovementRulesImpl(new TestBoard(3));
        final var optional = rules.pathCalculation(pos(1, 1), pos(1, 1), player);
        assertTrue(optional.isPresent());
        assertTrue(optional.get().isEmpty());
    }

    @Test
    void pathCalculationShouldReturnValidPathWhenReachable() {
        final Player player = new BasicPlayer(pos(0, 0));
        final MovementRules rules = new MovementRulesImpl(new TestBoard(3));
        final var from = pos(0, 0);
        final var to = pos(2, 0);
        final var optional = rules.pathCalculation(from, to, player);
        assertTrue(optional.isPresent());
        final var path = optional.get();
        assertFalse(path.isEmpty());
        assertEquals(to, path.get(path.size() - 1));
        Position prev = from;
        for (final Position step : path) {
            final int xDelta = Math.abs(step.x() - prev.x());
            final int yDelta = Math.abs(step.y() - prev.y());
            assertTrue(
                xDelta <= 1 && yDelta <= 1 && !(xDelta == 0 && yDelta == 0));
            prev = step;
        }
    }

    @Test
    void nextUnitaryStepShouldBehaveCorrectly() {
        final Player player = new BasicPlayer(pos(0, 0));
        final MovementRules rules = new MovementRulesImpl(new TestBoard(4));
        final var from = pos(0, 0);
        final var to = pos(2, 0);
        assertTrue(rules.nextUnitaryStep(pos(1, 1), pos(1, 1), player).isEmpty());
        final var step = rules.nextUnitaryStep(from, to, player);
        assertTrue(step.isPresent());
        final var s = step.get();
        final var xDelta = Math.abs(s.x() - from.x());
        final var yDelta = Math.abs(s.y() - from.y());
        assertTrue(xDelta <= 1 && yDelta <= 1 && !(xDelta == 0 && yDelta == 0));
    }

    @Test
    void nextUnitaryStepShouldReturnEmptyWhenPathInvalid() {
        final Player player = new BasicPlayer(pos(0, 0));
        final MovementRules rules = new MovementRulesImpl(new TestBoard(3));
        assertTrue(rules.nextUnitaryStep(pos(0, 0), pos(NINE, NINE), player).isEmpty());
    }

    @Test
    void isReachableShouldReturnFalseForPositionsInvalid() {
        final Player player = new BasicPlayer(pos(0, 0));
        final MovementRules rules = new MovementRulesImpl(new TestBoard(5));
        assertFalse(rules.isReachable(pos(SEVEN, SEVEN), pos(0, 0), player));
        assertFalse(rules.isReachable(pos(0, 0), pos(SEVEN, SEVEN), player));
    }

    @Test
    void isReachableShouldReturnTrueWhenFromEqualsTo() {
        final Player player = new BasicPlayer(pos(0, 0));
        final MovementRules rules = new MovementRulesImpl(new TestBoard(5));
        assertTrue(rules.isReachable(pos(1, 1), pos(1, 1), player));
    }

    @Test
    void isReachableShouldDependOnPathExistence() {
        final Player player = new BasicPlayer(pos(0, 0));
        final MovementRules reach = new MovementRulesImpl(new TestBoard(5));
        assertTrue(reach.isReachable(pos(0, 0), pos(2, 2), player));
        final MovementRules blocked = new MovementRulesImpl(
            new TestBoard(5), 
            (p, pl) -> 
                    p.equals(pos(1, 0))
                    || p.equals(pos(0, 1))
                    || p.equals(pos(1, 1)), 
            (p, pl) -> false
        );
        assertFalse(blocked.isReachable(pos(0, 0), pos(2, 2), player));
    }

    private static final class BasicPlayer implements PlayerOperations {

        private final Position position;
        private final Inventory inventory;

        BasicPlayer(final Position position) {
            if (position == null) {
                throw new IllegalArgumentException("parameter can't be null");
            }
            this.position = position;
            this.inventory = new InventoryImpl();
        }

        BasicPlayer(final Position position, final Inventory inventory) {
            if (position == null) {
                throw new IllegalArgumentException("position can't be null");
            }
            if (inventory == null) {
                throw new IllegalArgumentException("inventory can't be null");
            }
            this.position = position;
            this.inventory = inventory;
        }

        @Override
        public Position position() {
            return this.position;
        }

        @Override
        public int livesCount() {
            return 0;
        }

        @Override
        public int goldCount() {
            return 0;
        }

        @Override
        public Inventory inventory() {
            return this.inventory;
        }

        @Override
        public PlayerOperations withInventory(final Inventory newInventory) {
        if (newInventory == null) {
            throw new IllegalArgumentException("inventory can't be null");
        }
        return new BasicPlayer(this.position, newInventory);
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
        public PlayerOperations setLives(int num) {
            throw new UnsupportedOperationException("Unimplemented method 'setLives'");
        }

    }

    private static final class TestCell implements Cell {

        @Override
        public void reveal() {
            throw new UnsupportedOperationException("Unimplemented method 'reveal'");
        }

        @Override
        public boolean isRevealed() {
            throw new UnsupportedOperationException("Unimplemented method 'isRevealed'");
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
            throw new UnsupportedOperationException("Unimplemented method 'hasContent'");
        }

        @Override
        public Optional<CellContent> getContent() {
            throw new UnsupportedOperationException("Unimplemented method 'getContent'");
        }

        @Override
        public void setContent(final CellContent content) {
            throw new UnsupportedOperationException("Unimplemented method 'setContent'");
        }

        @Override
        public void removeContent() {
            throw new UnsupportedOperationException("Unimplemented method 'removeContent'");
        }

    }

    private final class TestBoard implements Board {

        private final int size;
        private final Cell[][] cells;
        private final Map<Cell, Position> positionCell = new HashMap<>();

        TestBoard(final int size) {
            this.size = size;
            this.cells = new Cell[size][size];
            for (int y = 0; y < size; y++) {
                for (int x = 0; x < size; x++) {
                    final Cell c = new TestCell();
                    cells[y][x] = c;
                    positionCell.put(c, pos(x, y));
                }
            }
        }

        private Cell cellFromPosition(final Position p) {
            return this.cells[p.y()][p.x()];
        }

        private int x(final Position p) {
            return p.x();
        }

        private int y(final Position p) {
            return p.y();
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
        public Position getCellPosition(final Cell cell) {
            if (cell == null) {
                throw new IllegalArgumentException("cell can't be null");
            }
            final Position p = positionCell.get(cell);
            if (p == null) {
                throw new IllegalArgumentException("position can't be null");
            }
            return p;
        }

        @Override
        public List<Cell> getAdjacentCells(final Position p) {
            if (p == null) {
                throw new IllegalArgumentException("position cannot be null");
            }
            if (!isPositionValid(p)) {
                throw new IndexOutOfBoundsException("position must be valid");
            }
            final List<Cell> res = new ArrayList<>();
            for (int xDelta = -1; xDelta <= 1; xDelta++) {
                for (int yDelta = -1; yDelta <= 1; yDelta++) {
                    if (xDelta == 0 && yDelta == 0) {
                        continue;
                    }
                    final Position pos = new Position(p.x() + xDelta, p.y() + yDelta);
                    if (isPositionValid(pos)) {
                        res.add(cellFromPosition(pos));
                    }
                }
            }
            return res;
        }

        @Override
        public int getBoardSize() {
            throw new UnsupportedOperationException("Unimplemented method 'getBoardSize'");
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
        public boolean isPositionValid(final Position p) {
            if (p == null) {
                throw new IllegalArgumentException("p");
            }
            final int xPos = x(p);
            final int yPos = y(p);
            return xPos >= 0 && xPos < size 
                    && yPos >= 0 && yPos < size;
        }

        @Override
        public boolean isAdjacent(final Position p1, final Position p2) {
            if (p1 == null || p2 == null) {
                throw new IllegalArgumentException("parameters can't be null");
            }
            if (!isPositionValid(p1) || !isPositionValid(p2)) {
                throw new IndexOutOfBoundsException("parameters must have valid values");
            }
            final int xDelta = Math.abs(x(p1) - x(p2));
            final int yDelta = Math.abs(y(p1) - y(p2));
            return xDelta <= 1 && yDelta <= 1 && !(xDelta == 0 && yDelta == 0);
        }

    }
}
