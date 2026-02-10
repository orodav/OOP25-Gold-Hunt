package it.unibo.goldhunt.configuration.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
        public Cell getCell(Position p) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Position getCellPosition(Cell cell) {
            throw new UnsupportedOperationException();
        }

        @Override
        public List<Cell> getAdjacentCells(Position p) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int getBoardSize() {
            return this.size;
        }

        @Override
        public List<Cell> getRow(int index) {
            throw new UnsupportedOperationException();
        }

        @Override
        public List<Cell> getColumn(int index) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean isPositionValid(Position p) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean isAdjacent(Position p1, Position p2) {
            throw new UnsupportedOperationException();
        }
    }

    private static final class FakeBoardGenerator implements BoardGenerator {
        Position receivedStart;
        Position receivedExit;
        LevelConfig receivedConfig;
        
        @Override
        public Board generate(LevelConfig config, Position start, Position exit) {
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
        public PlayerOperations moveTo(Position p) {
            return new FakePlayer(p, lives);
        }

        @Override
        public PlayerOperations addGold(int num) {
            throw new UnsupportedOperationException();
        }

        @Override
        public PlayerOperations addLives(int num) {
            return new FakePlayer(position, lives + num);
        }

        @Override
        public PlayerOperations addItem(ItemTypes item, int quantity) {
            throw new UnsupportedOperationException("Unimplemented method 'addItem'");
        }

        @Override
        public PlayerOperations useItem(ItemTypes item, int quantity) {
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
        public PlayerOperations withInventory(Inventory inventory) {
            throw new UnsupportedOperationException("Unimplemented method 'withInventory'");
        }
    }


    @Test
    void testEasyInitBoardCreatesBoard() {
        Level level = new LevelImpl(new EasyConfig(), new FakeBoardGenerator(), new FakePlayer(new Position(0, 0), 0));
        level.initBoard();
        assertNotNull(level.getBoard());
    }

    @Test
    void testEasyInitBoardPassesCorrectParametersToBoardGenerator() {
        LevelConfig config = new EasyConfig();
        FakeBoardGenerator generator = new FakeBoardGenerator();
        Level level = new LevelImpl(config, generator, new FakePlayer(new Position(1,1), 0));
        level.initBoard();

        assertEquals(config, generator.receivedConfig);
        assertEquals(new Position(0, 0), generator.receivedStart);
        assertEquals(new Position(config.getBoardSize() - 1, config.getBoardSize() - 1), generator.receivedExit);
    }

    @Test
    void testEasyInitBoardSetsExitCorrectly() {
        LevelConfig config = new EasyConfig();
        Level level = new LevelImpl(config, new FakeBoardGenerator(), new FakePlayer(new Position(0, 0), 0));
        level.initBoard();
        assertEquals(new Position(config.getBoardSize() - 1, config.getBoardSize() - 1), level.getExit());
    }

    @Test
    void testEasyInitPlayerPositionMovesPlayerToStart() {
        Level level = new LevelImpl(new EasyConfig(), new FakeBoardGenerator(), new FakePlayer(new Position(5, 5), 0));
        level.initPlayerPosition();
        assertEquals(new Position(0, 0), level.getPlayer().position());
    }

    @Test
    void testEasyInitLivesAddsThreeLives() { 
        Level level = new LevelImpl(new EasyConfig(), new FakeBoardGenerator(), new FakePlayer(new Position(0, 0), 0));
        level.initLives();
        assertEquals(3, level.getPlayer().livesCount());
    }


    @Test
    void testMediumInitBoardCreatesBoard() {
        Level level = new LevelImpl(new MediumConfig(), new FakeBoardGenerator(), new FakePlayer(new Position(0, 0), 0));
        level.initBoard();
        assertNotNull(level.getBoard());
    }

    @Test
    void testMediumInitBoardPassesCorrectParametersToBoardGenerator() {
        LevelConfig config = new MediumConfig();
        FakeBoardGenerator generator = new FakeBoardGenerator();
        Level level = new LevelImpl(config, generator, new FakePlayer(new Position(1,1), 0));
        level.initBoard();

        assertEquals(config, generator.receivedConfig);
        assertEquals(new Position(0, 0), generator.receivedStart);
        assertEquals(new Position(config.getBoardSize() - 1, config.getBoardSize() - 1), generator.receivedExit);
    }

    @Test
    void testMediumInitBoardSetsExitCorreclty() {
        LevelConfig config = new MediumConfig();
        Level level = new LevelImpl(config, new FakeBoardGenerator(), new FakePlayer(new Position(0, 0), 0));
        level.initBoard();
        assertEquals(new Position(config.getBoardSize() - 1, config.getBoardSize() - 1), level.getExit());
    }

    @Test
    void testMediumInitPlayerPositionMovesPlayerToStart() {
        Level level = new LevelImpl(new MediumConfig(), new FakeBoardGenerator(), new FakePlayer(new Position(5, 5), 0));
        level.initPlayerPosition();
        assertEquals(new Position(0, 0), level.getPlayer().position());
    }

    @Test
    void testMediumInitLivesAddsThreeLives() { 
        Level level = new LevelImpl(new MediumConfig(), new FakeBoardGenerator(), new FakePlayer(new Position(0, 0), 0));
        level.initLives();
        assertEquals(3, level.getPlayer().livesCount());
    }


    @Test
    void testHardInitBoardCreatesBoard() {
        Level level = new LevelImpl(new HardConfig(), new FakeBoardGenerator(), new FakePlayer(new Position(0, 0), 0));
        level.initBoard();
        assertNotNull(level.getBoard());
    }

    @Test
    void testHardInitBoardPassesCorrectParametersToBoardGenerator() {
        LevelConfig config = new HardConfig();
        FakeBoardGenerator generator = new FakeBoardGenerator();
        Level level = new LevelImpl(config, generator, new FakePlayer(new Position(1,1), 0));
        level.initBoard();

        assertEquals(config, generator.receivedConfig);
        assertEquals(new Position(0, 0), generator.receivedStart);
        assertEquals(new Position(config.getBoardSize() - 1, config.getBoardSize() - 1), generator.receivedExit);
    }

    @Test
    void testHardInitBoardSetsExitCorreclty() {
        LevelConfig config = new HardConfig();
        Level level = new LevelImpl(config, new FakeBoardGenerator(), new FakePlayer(new Position(0, 0), 0));
        level.initBoard();
        assertEquals(new Position(config.getBoardSize() - 1, config.getBoardSize() - 1), level.getExit());
    }

    @Test
    void testHardInitPlayerPositionMovesPlayerToStart() {
        Level level = new LevelImpl(new HardConfig(), new FakeBoardGenerator(), new FakePlayer(new Position(5, 5), 0));
        level.initPlayerPosition();
        assertEquals(new Position(0, 0), level.getPlayer().position());
    }

    @Test
    void testHardInitLivesAddsThreeLives() { 
        Level level = new LevelImpl(new HardConfig(), new FakeBoardGenerator(), new FakePlayer(new Position(0, 0), 0));
        level.initLives();
        assertEquals(3, level.getPlayer().livesCount());
    }
}
