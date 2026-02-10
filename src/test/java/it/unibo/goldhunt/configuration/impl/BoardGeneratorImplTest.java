package it.unibo.goldhunt.configuration.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.goldhunt.board.api.Board;
import it.unibo.goldhunt.board.api.BoardFactory;
import it.unibo.goldhunt.board.api.Cell;
import it.unibo.goldhunt.configuration.api.BoardGenerator;
import it.unibo.goldhunt.configuration.api.LevelConfig;
import it.unibo.goldhunt.engine.api.Position;
import it.unibo.goldhunt.items.api.CellContent;
import it.unibo.goldhunt.items.impl.ItemFactoryImpl;
import it.unibo.goldhunt.items.impl.TrapFactoryImpl;
import it.unibo.goldhunt.player.impl.InventoryImpl;
import it.unibo.goldhunt.player.impl.PlayerImpl;

/**
 * Tests for {@link BoardGeneratorImpl}.
 * Verifies board generation, content placement, safety
 * and computations.
 */
class BoardGeneratorImplTest {

    private BoardGenerator generator;
    private LevelConfig config;
    private Position start;
    private Position exit;

    @BeforeEach
    void setup() {
        var player = new PlayerImpl(new Position(0, 0), 3, 0, new InventoryImpl());
        BoardFactory boardFactory = size -> new ConnectedFakeBoard(size);
        this.generator = new BoardGeneratorImpl(boardFactory, new TrapFactoryImpl(player), new ItemFactoryImpl(), player);
        this.config = new EasyConfig();
        this.start = new Position(0, 0);
        this.exit = new Position(config.getBoardSize() - 1, config.getBoardSize() - 1);
    }

    @Test
    void testBoardIsGeneratedSuccessfully() {
        assertDoesNotThrow(() -> generator.generate(config, start, exit));
    }

    @Test
    void testBoardHasCorrectSize() {
        Board board = generator.generate(config, start, exit);
        assertEquals(config.getBoardSize(), board.getBoardSize());
    }

    @Test
    void testStartCellIsEmpty() {
        Board board = generator.generate(config, start, exit);
        assertTrue(board.getCell(start).getContent().isEmpty());
    }

    @Test
    void testExitCellIsEmpty() {
        Board board = generator.generate(config, start, exit);
        assertTrue(board.getCell(exit).getContent().isEmpty());
    }

    @Test
    void testExactlyCorrectNumberOfTraps() {
        Board board = generator.generate(config, start, exit);
        long traps = countTraps(board);
        assertEquals(config.getTrapCount(), traps, "10 traps attesi");
    }

    @Test
    void testExactlyCorrectNumberOfItems() {
        Board board = generator.generate(config, start, exit);
        int expectedItems = config.getItemConfig().values().stream().mapToInt(Integer::intValue).sum();
        long actualItems = board.getBoardCells().stream().filter(c -> c.hasContent() && !c.getContent().get().isTrap()).count();
        assertEquals(expectedItems, actualItems);
    }

    @Test
    void testAdjacentTrapsAreComputedCorrectly() {
        Board board = generator.generate(config, start, exit);
        for (Cell cell : board.getBoardCells()) {
            int computed = cell.getAdjacentTraps();
            int actual = countAdjacentTraps(board, cell);
            assertEquals(actual, computed);
        }
    }

    @Test
    void testAllCellsAreInitiallyHidden() {
        Board board = generator.generate(config, start, exit);
        boolean anyRevealed = board.getBoardCells().stream().anyMatch(Cell::isRevealed);
        assertFalse(anyRevealed);
    }

    @Test
    void testStartAndExitAreReachablePositions() {
        Board board = generator.generate(config, start, exit);
        assertDoesNotThrow(() -> board.getCell(start));
        assertDoesNotThrow(() -> board.getCell(exit));
    }

    @Test
    void testStartAndExitAreOnSafePath() {
        Board board = generator.generate(config, start, exit);
        assertTrue(board.getCell(start).getContent().isEmpty());
        assertTrue(board.getCell(exit).getContent().isEmpty());
    }

    private long countTraps(Board board) {
        return board.getBoardCells().stream().filter(c -> c.hasContent() && c.getContent().get().isTrap()).count();
    }

    private int countAdjacentTraps(Board board, Cell cell) {
        Position pos = board.getCellPosition(cell);
        return (int) board.getAdjacentCells(pos).stream().filter(n -> n.hasContent() && n.getContent().get().isTrap()).count();
    }


    private static final class FakeCell implements Cell {
        private Optional<CellContent> content = Optional.empty();
        private int adjacentTraps = 0;
        
        @Override
        public boolean equals(Object obj) {
            return super.equals(obj); 
        }
        @Override
        public int hashCode() {
            return super.hashCode();
        }

        @Override
        public boolean hasContent() {
            return content.isPresent();
        }

        @Override
        public Optional<CellContent> getContent() {
            return content;
        }

        @Override
        public void setContent(CellContent c) {
            this.content = Optional.of(c);
        }

        @Override
        public int getAdjacentTraps() {
            return adjacentTraps;
        }

        @Override
        public void setAdjacentTraps(int n) {
            this.adjacentTraps = n;
        }

        @Override
        public boolean isRevealed() {
            return false;
        }

        @Override
        public void reveal() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void toggleFlag() {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean isFlagged() {
            return false;
        }

        @Override
        public void removeContent() {
            content = Optional.empty();
        }
    }

    private static final class ConnectedFakeBoard implements Board {
        private final int size;
        private final List<Cell> cells;

        ConnectedFakeBoard(int size) {
            this.size = size;
            this.cells = new ArrayList<>();
            for (int i = 0; i < size * size; i++) {
                cells.add(new FakeCell());
            }
        }

        @Override
        public int getBoardSize() {
            return size;
        }

        @Override
        public List<Cell> getBoardCells() {
            return new ArrayList<>(cells);
        }

        @Override
        public Cell getCell(Position p) {
            return cells.get(p.y() * size + p.x());
        }

        @Override
        public Position getCellPosition(Cell c) {
            int i = cells.indexOf(c);
            return new Position(i % size, i / size);
        }

        @Override
        public List<Cell> getAdjacentCells(Position p) {
            List<Cell> adj = new ArrayList<>();
            for (int dx = -1; dx <= 1; dx++) {
                for (int dy = -1; dy <= 1; dy++) {
                    if (dx == 0 && dy == 0) continue;
                    int nx = p.x() + dx;
                    int ny = p.y() + dy;
                    if (nx >= 0 && ny >= 0 && nx < size && ny < size) {
                        adj.add(getCell(new Position(nx, ny)));
                    }
                }
            }
            return adj;
        }

        @Override public List<Cell> getRow(int i) { return List.of(); }
        @Override public List<Cell> getColumn(int i) { return List.of(); }

        @Override
        public boolean isPositionValid(Position p) {
            return p.x() >= 0 && p.y() >= 0
                && p.x() < size && p.y() < size;
        }

        @Override
        public boolean isAdjacent(Position p1, Position p2) {
            return false;
        }
    }
}



