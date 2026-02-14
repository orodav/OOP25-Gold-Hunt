
package it.unibo.goldhunt.configuration.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.nio.channels.UnsupportedAddressTypeException;
import java.util.List;

import org.junit.jupiter.api.Test;

import it.unibo.goldhunt.board.api.Board;
import it.unibo.goldhunt.board.api.Cell;
import it.unibo.goldhunt.configuration.api.BoardGenerator;
import it.unibo.goldhunt.configuration.api.LevelConfig;
import it.unibo.goldhunt.configuration.api.Level;
import it.unibo.goldhunt.engine.api.Position;
import it.unibo.goldhunt.items.api.ItemTypes;
import it.unibo.goldhunt.player.api.Inventory;
import it.unibo.goldhunt.player.api.PlayerOperations;

/**
 * Tests for {@link LevelImpl}.
 */
class LevelImplTest {

    @Test
    void testEasyInitBoardCreatesBoard() {
        final Level level = new LevelImpl(new EasyConfig(), new FakeBoardGenerator(), new FakePlayer(new Position(0, 0), 0));
        level.initBoard();
        assertNotNull(level.getBoard());
    }

    @Test
    void testEasyInitBoardPassesCorrectParametersToBoardGenerator() {
        final LevelConfig config = new EasyConfig();
        final FakeBoardGenerator generator = new FakeBoardGenerator();
        final Level level = new LevelImpl(config, generator, new FakePlayer(new Position(1, 1), 0));
        level.initBoard();

        assertEquals(config, generator.receivedConfig);
        assertEquals(new Position(0, 0), generator.receivedStart);
        assertEquals(new Position(config.getBoardSize() - 1, config.getBoardSize() - 1), generator.receivedExit);
    }

    @Test
    void testEasyInitBoardSetsExitCorrectly() {
        final LevelConfig config = new EasyConfig();
        final Level level = new LevelImpl(config, new FakeBoardGenerator(), new FakePlayer(new Position(0, 0), 0));
        level.initBoard();
        assertEquals(new Position(config.getBoardSize() - 1, config.getBoardSize() - 1), level.getExit());
    }

    @Test
    void testEasyInitPlayerPositionMovesPlayerToStart() {
        final Level level = new LevelImpl(new EasyConfig(), new FakeBoardGenerator(), new FakePlayer(new Position(5, 5), 0));
        level.initPlayerPosition();
        assertEquals(new Position(0, 0), level.getPlayer().position());
    }

    @Test
    void testEasyInitLivesSetsThreeLives() { 
        final Level level = new LevelImpl(new EasyConfig(), new FakeBoardGenerator(), new FakePlayer(new Position(0, 0), 0));
        level.initLives();
        assertEquals(3, level.getPlayer().livesCount());
    }

    @Test
    void testMediumInitBoardCreatesBoard() {
        final Level level = new LevelImpl(new MediumConfig(), new FakeBoardGenerator(), new FakePlayer(new Position(0, 0), 0));
        level.initBoard();
        assertNotNull(level.getBoard());
    }

    @Test
    void testMediumInitBoardPassesCorrectParametersToBoardGenerator() {
        final LevelConfig config = new MediumConfig();
        final FakeBoardGenerator generator = new FakeBoardGenerator();
        final Level level = new LevelImpl(config, generator, new FakePlayer(new Position(1, 1), 0));
        level.initBoard();

        assertEquals(config, generator.receivedConfig);
        assertEquals(new Position(0, 0), generator.receivedStart);
        assertEquals(new Position(config.getBoardSize() - 1, config.getBoardSize() - 1), generator.receivedExit);
    }

    @Test
    void testMediumInitBoardSetsExitCorreclty() {
        final LevelConfig config = new MediumConfig();
        final Level level = new LevelImpl(config, new FakeBoardGenerator(), new FakePlayer(new Position(0, 0), 0));
        level.initBoard();
        assertEquals(new Position(config.getBoardSize() - 1, config.getBoardSize() - 1), level.getExit());
    }

    @Test
    void testMediumInitPlayerPositionMovesPlayerToStart() {
        final Level level = new LevelImpl(new MediumConfig(), new FakeBoardGenerator(), new FakePlayer(new Position(5, 5), 0));
        level.initPlayerPosition();
        assertEquals(new Position(0, 0), level.getPlayer().position());
    }

    @Test
    void testMediumInitLivesSetsThreeLives() { 
        final Level level = new LevelImpl(new MediumConfig(), new FakeBoardGenerator(), new FakePlayer(new Position(0, 0), 0));
        level.initLives();
        assertEquals(3, level.getPlayer().livesCount());
    }

    @Test
    void testHardInitBoardCreatesBoard() {
        final Level level = new LevelImpl(new HardConfig(), new FakeBoardGenerator(), new FakePlayer(new Position(0, 0), 0));
        level.initBoard();
        assertNotNull(level.getBoard());
    }

    @Test
    void testHardInitBoardPassesCorrectParametersToBoardGenerator() {
        final LevelConfig config = new HardConfig();
        final FakeBoardGenerator generator = new FakeBoardGenerator();
        final Level level = new LevelImpl(config, generator, new FakePlayer(new Position(1, 1), 0));
        level.initBoard();

        assertEquals(config, generator.receivedConfig);
        assertEquals(new Position(0, 0), generator.receivedStart);
        assertEquals(new Position(config.getBoardSize() - 1, config.getBoardSize() - 1), generator.receivedExit);
    }

    @Test
    void testHardInitBoardSetsExitCorreclty() {
        final LevelConfig config = new HardConfig();
        final Level level = new LevelImpl(config, new FakeBoardGenerator(), new FakePlayer(new Position(0, 0), 0));
        level.initBoard();
        assertEquals(new Position(config.getBoardSize() - 1, config.getBoardSize() - 1), level.getExit());
    }

    @Test
    void testHardInitPlayerPositionMovesPlayerToStart() {
        final Level level = new LevelImpl(new HardConfig(), new FakeBoardGenerator(), new FakePlayer(new Position(5, 5), 0));
        level.initPlayerPosition();
        assertEquals(new Position(0, 0), level.getPlayer().position());
    }

    @Test
    void testHardInitLivesSetsThreeLives() { 
        final Level level = new LevelImpl(new HardConfig(), new FakeBoardGenerator(), new FakePlayer(new Position(0, 0), 0));
        level.initLives();
        assertEquals(3, level.getPlayer().livesCount());
    }

    private static final class FakeBoard implements Board {
        private final int size;

        FakeBoard(final int size) {
            this.size = size;
        }

        @Override
        public List<Cell> getBoardCells() {
            throw new UnsupportedOperationException();
        }

        @Override
        public Cell getCell(final Position p) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Position getCellPosition(final Cell cell) {
            throw new UnsupportedOperationException();
        }

        @Override
        public List<Cell> getAdjacentCells(final Position p) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int getBoardSize() {
            return this.size;
        }

        @Override
        public List<Cell> getRow(final int index) {
            throw new UnsupportedOperationException();
        }

        @Override
        public List<Cell> getColumn(final int index) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean isPositionValid(final Position p) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean isAdjacent(final Position p1, final Position p2) {
            throw new UnsupportedOperationException();
        }
    }

    private static final class FakeBoardGenerator implements BoardGenerator {
        private Position receivedStart;
        private Position receivedExit;
        private LevelConfig receivedConfig;

        @Override
        public Board generate(final LevelConfig config, final Position start, final Position exit) {
            this.receivedConfig = config;
            this.receivedStart = start;
            this.receivedExit = exit;
            return new FakeBoard(config.getBoardSize());
        }
    }

    private static final class FakePlayer implements PlayerOperations {
        private final Position position;
        private final int lives;

        FakePlayer(final Position position, final int lives) {
            this.position = position;
            this.lives = lives;
        }

        @Override
        public PlayerOperations moveTo(final Position p) {
            return new FakePlayer(p, lives);
        }

        @Override
        public PlayerOperations addGold(final int num) {
            throw new UnsupportedOperationException();
        }

        @Override
        public PlayerOperations addLives(final int num) {
            throw new UnsupportedAddressTypeException();
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
        public Position position() {
            return position;
        }

        @Override
        public int livesCount() {
            return lives;
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
        public PlayerOperations setLives(int num) {
            return new FakePlayer(this.position, num);
        }
    }
}
