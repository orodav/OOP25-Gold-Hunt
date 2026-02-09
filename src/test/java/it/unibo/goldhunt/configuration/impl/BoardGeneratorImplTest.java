package it.unibo.goldhunt.configuration.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Objects;

import org.junit.jupiter.api.Test;

import it.unibo.goldhunt.board.api.Board;
import it.unibo.goldhunt.board.api.BoardFactory;
import it.unibo.goldhunt.board.api.Cell;
import it.unibo.goldhunt.board.impl.BaseCellFactory;
import it.unibo.goldhunt.board.impl.SquareBoardFactory;
import it.unibo.goldhunt.configuration.api.BoardGenerator;
import it.unibo.goldhunt.configuration.api.LevelConfig;
import it.unibo.goldhunt.engine.api.Position;
import it.unibo.goldhunt.items.api.CellContent;
import it.unibo.goldhunt.items.api.ItemFactory;
import it.unibo.goldhunt.items.api.Revealable;
import it.unibo.goldhunt.items.api.TrapFactory;
import it.unibo.goldhunt.items.impl.Item;

/**
 * Tests for {@link BoardGeneratorImpl}.
 */
class BoardGeneratorImplTest {
    
    private static final class FakeTrap implements Revealable {

        @Override
        public boolean applyEffect() { 
            return true;
        }

        @Override
        public String shortString() {
            return "T";
        }
    }

    private static final class FakeTrapFactory implements TrapFactory {

        @Override
        public Revealable createTrap() {
            return new FakeTrap();
        }
    } 

    private static final class FakeItem extends Item {

        @Override
        public boolean applyEffect() {
            return true;
        }

        @Override
        public String shortString() {
            return "I";
        }

        @Override
        public String getName() {
            return "FakeItem";
        }
    }

    private static final class FakeItemFactory implements ItemFactory {

        @Override
        public Item generateItem(final String item) {
            return new FakeItem();
        }
    }

    private BoardGenerator createGenerator() {
        return new BoardGeneratorImpl(new SquareBoardFactory(new BaseCellFactory()), new FakeTrapFactory(), new FakeItemFactory());
    }

    private int expectedItemCount(final LevelConfig config) {
        int sum = 0;
        for (final Integer value : config.getItemConfig().values()) {
            sum += value;
        }
        return sum;
    }

    @Test
    void easyBoardIsCreated() {
        LevelConfig config = new EasyConfig();
        Board board = createGenerator().generate(config, new Position(0, 0), new Position(config.getBoardSize() - 1, config.getBoardSize() - 1));

        assertNotNull(board);
    }

    @Test
    void easyBoardHasCorrectSize() {
        LevelConfig config = new EasyConfig();
        Board board = createGenerator().generate(config, new Position(0, 0), new Position(config.getBoardSize() - 1, config.getBoardSize() - 1));

        assertEquals(config.getBoardSize(), board.getBoardSize());
    }

    @Test
    void easyCorrectNumberOfTrapsPlaced() {
        LevelConfig config = new EasyConfig();
        Board board = createGenerator().generate(config, new Position(0, 0), new Position(config.getBoardSize() - 1, config.getBoardSize() - 1));

        int trapCount = 0;
        for (Cell cell : board.getBoardCells()) {
            if (cell.hasContent()) {
                CellContent content = cell.getContent().get();
                if (content instanceof Revealable r && r.isTrap()) {
                    trapCount++;
                }
            }
        }

        assertEquals(config.getTrapCount(), trapCount);
    }

    @Test
    void easyCorrectNumberOfItemsPlaced() {
        LevelConfig config = new EasyConfig();
        Board board = createGenerator().generate(config, new Position(0, 0), new Position(config.getBoardSize() - 1, config.getBoardSize() - 1));

        int itemCount = 0;
        for (Cell cell : board.getBoardCells()) {
            if (cell.hasContent()) {
                CellContent content = cell.getContent().get();
                if (!(content instanceof Revealable r && r.isTrap())) {
                    itemCount++;
                }
            }
        }

        assertEquals(expectedItemCount(config), itemCount);
    }

    @Test
    void easyAdjacentTrapsAreComputedCorrectly() {
        LevelConfig config = new EasyConfig();
        Board board = createGenerator().generate(config, new Position(0, 0), new Position(config.getBoardSize() - 1, config.getBoardSize() - 1));

        for (Cell cell : board.getBoardCells()) {
            int expected = 0;
            for (Cell adj : board.getAdjacentCells(board.getCellPosition(cell))) {
                if (adj.hasContent()) {
                    CellContent content = adj.getContent().get();
                    if (content instanceof Revealable r && r.isTrap()) {
                        expected++;
                    }
                }
            }
            assertEquals(expected, cell.getAdjacentTraps());
        }
    }

    @Test
    void mediumBoardHasCorrectSize() {
        LevelConfig config = new MediumConfig();
        Board board = createGenerator().generate(config, new Position(0, 0), new Position(config.getBoardSize() - 1, config.getBoardSize() - 1));

        assertEquals(config.getBoardSize(), board.getBoardSize());
    }

    @Test
    void mediumCorrectNumberOfTrapsPlaced() {
        LevelConfig config = new MediumConfig();
        Board board = createGenerator().generate(config, new Position(0, 0), new Position(config.getBoardSize() - 1, config.getBoardSize() - 1));

        int trapCount = 0;
        for (Cell cell : board.getBoardCells()) {
            if (cell.hasContent()) {
                CellContent content = cell.getContent().get();
                if (content instanceof Revealable r && r.isTrap()) {
                    trapCount++;
                }
            }
        }

        assertEquals(config.getTrapCount(), trapCount);
    }

    @Test
    void mediumCorrectNumberOfItemsPlaced() {
        LevelConfig config = new MediumConfig();
        Board board = createGenerator().generate(config, new Position(0, 0), new Position(config.getBoardSize() - 1, config.getBoardSize() - 1));

        int itemCount = 0;
        for (Cell cell : board.getBoardCells()) {
            if (cell.hasContent()) {
                CellContent content = cell.getContent().get();
                if (!(content instanceof Revealable r && r.isTrap())) {
                    itemCount++;
                }
            }
        }

        assertEquals(expectedItemCount(config), itemCount);
    }

    @Test
    void hardBoardHasCorrectSize() {
        LevelConfig config = new HardConfig();
        Board board = createGenerator().generate(config, new Position(0, 0), new Position(config.getBoardSize() - 1, config.getBoardSize() - 1));

        assertEquals(config.getBoardSize(), board.getBoardSize());
    }

    @Test
    void hardCorrectNumberOfTrapsPlaced() {
        LevelConfig config = new HardConfig();
        Board board = createGenerator().generate(config, new Position(0, 0), new Position(config.getBoardSize() - 1, config.getBoardSize() - 1));

        int trapCount = 0;
        for (Cell cell : board.getBoardCells()) {
            if (cell.hasContent()) {
                CellContent content = cell.getContent().get();
                if (content instanceof Revealable r && r.isTrap()) {
                    trapCount++;
                }
            }
        }

        assertEquals(config.getTrapCount(), trapCount);
    }

    @Test
    void hardCorrectNumberOfItemsPlaced() {
        LevelConfig config = new HardConfig();
        Board board = createGenerator().generate(config, new Position(0, 0), new Position(config.getBoardSize() - 1, config.getBoardSize() - 1));

        int itemCount = 0;
        for (Cell cell : board.getBoardCells()) {
            if (cell.hasContent()) {
                CellContent content = cell.getContent().get();
                if (!(content instanceof Revealable r && r.isTrap())) {
                    itemCount++;
                }
            }
        }

        assertEquals(expectedItemCount(config), itemCount);
    }
}
