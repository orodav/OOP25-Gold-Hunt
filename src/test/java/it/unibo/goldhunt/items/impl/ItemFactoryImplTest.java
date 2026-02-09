package it.unibo.goldhunt.items.impl;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.goldhunt.board.api.Board;
import it.unibo.goldhunt.board.api.Cell;
import it.unibo.goldhunt.engine.api.Position;
import it.unibo.goldhunt.items.api.ItemTypes;
import it.unibo.goldhunt.player.api.Inventory;
import it.unibo.goldhunt.player.api.PlayerOperations;

public class ItemFactoryImplTest {
    private ItemFactoryImpl itemFactoryImpl;

    @BeforeEach
    void init(){
        itemFactoryImpl = new ItemFactoryImpl();
    }

    @Test
    void testCorrectItem(){
        assertTrue(itemFactoryImpl.generateItem("M") instanceof Chart);
        assertTrue(itemFactoryImpl.generateItem("D") instanceof Dynamite);
        assertTrue(itemFactoryImpl.generateItem("G") instanceof Gold);
        assertTrue(itemFactoryImpl.generateItem("L") instanceof Lifes);
        assertTrue(itemFactoryImpl.generateItem("C") instanceof LuckyClover);
        assertTrue(itemFactoryImpl.generateItem("P") instanceof Pickaxe);
        assertTrue(itemFactoryImpl.generateItem("S") instanceof Shield);
        assertTrue(itemFactoryImpl.generateItem("X") instanceof GoldX3);

    }

    @Test
    void generateItems(){
        String key[] = {"M", "D", "G", "L", "C", "P", "S", "X"};
        for (String str : key) {
            Item item = itemFactoryImpl.generateItem(str);
            assertNotNull(item, "should return an item given the key");
        }
    }

    @Test
    void generateWrongItem(){
        assertThrows(IllegalArgumentException.class, () -> itemFactoryImpl.generateItem("Q"));
    }

    @Test
    void testObjectsAreDiff(){
        Item firsItem = itemFactoryImpl.generateItem("M");
        Item secondItem = itemFactoryImpl.generateItem("M");
        assertNotSame(firsItem, secondItem, "every call should return a different object");
    }


    @Test
    void testItemContextBound(){
        Board board = new FakeBoard();
        PlayerOperations player = new FakePlayer();
        Inventory inventory = new FakeInventory();

        Item item = itemFactoryImpl.generateItem("M", board, player, inventory);
        assertNotNull(item);
        assertNotNull( item.context, "item should have a bound context");
    }


    private static final class FakeBoard implements Board{

        @Override
        public List<Cell> getBoardCells() {
            throw new UnsupportedOperationException("Unimplemented method 'getBoardCells'");
        }

        @Override
        public Cell getCell(Position p) {
            throw new UnsupportedOperationException("Unimplemented method 'getCell'");
        }

        @Override
        public Position getCellPosition(Cell cell) {
            throw new UnsupportedOperationException("Unimplemented method 'getCellPosition'");
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
        public boolean isPositionValid(Position p) {
            throw new UnsupportedOperationException("Unimplemented method 'isPositionValid'");
        }

        @Override
        public boolean isAdjacent(Position p1, Position p2) {
            throw new UnsupportedOperationException("Unimplemented method 'isAdjacent'");
        }
        
    }


    private static final class FakePlayer implements PlayerOperations{

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
        public PlayerOperations withInventory(Inventory inventory) {
            throw new UnsupportedOperationException("Unimplemented method 'withInventory'");
        }

        @Override
        public PlayerOperations moveTo(Position p) {
            throw new UnsupportedOperationException("Unimplemented method 'moveTo'");
        }

        @Override
        public PlayerOperations addGold(int num) {
            throw new UnsupportedOperationException("Unimplemented method 'addGold'");
        }

        @Override
        public PlayerOperations addLives(int num) {
            throw new UnsupportedOperationException("Unimplemented method 'addLives'");
        }

        @Override
        public PlayerOperations addItem(ItemTypes item, int quantity) {
            throw new UnsupportedOperationException("Unimplemented method 'addItem'");
        }

        @Override
        public PlayerOperations useItem(ItemTypes item, int quantity) {
            throw new UnsupportedOperationException("Unimplemented method 'useItem'");
        }
        
    }


    private static final class FakeInventory implements Inventory{

        @Override
        public Inventory add(ItemTypes item, int quantity) {
            throw new UnsupportedOperationException("Unimplemented method 'add'");
        }

        @Override
        public Inventory remove(ItemTypes item, int quantity) {
            throw new UnsupportedOperationException("Unimplemented method 'remove'");
        }

        @Override
        public int quantity(ItemTypes item) {
            throw new UnsupportedOperationException("Unimplemented method 'quantity'");
        }
        
    }


}
