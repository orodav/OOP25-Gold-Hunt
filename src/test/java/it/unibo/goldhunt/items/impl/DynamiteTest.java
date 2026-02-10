package it.unibo.goldhunt.items.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.goldhunt.board.api.Board;
import it.unibo.goldhunt.board.api.Cell;
import it.unibo.goldhunt.engine.api.Position;
import it.unibo.goldhunt.items.api.CellContent;
import it.unibo.goldhunt.items.api.ItemContext;
import it.unibo.goldhunt.items.api.ItemTypes;
import it.unibo.goldhunt.player.api.Inventory;
import it.unibo.goldhunt.player.api.PlayerOperations;

public class DynamiteTest {

    private Dynamite dynamite;
    private FakeBoard board;
    private FakePlayer player;


    @BeforeEach
    void init(){
        board = new FakeBoard(8);
        player = new FakePlayer(new Position(2,2));
        dynamite = new Dynamite();

        dynamite.context = new ItemContext(board, player, null);
    }

    @Test
    void testEffectDynamite(){
        PlayerOperations used = dynamite.applyEffect(player);

        assertTrue(used != null, "when used should return true");
        List<Cell> adj = board.getAdjacentCells(player.pos);
        adj.forEach(cell-> assertTrue(((FakeCell) cell ).disarmed, "cell should be disarmed"));
    }

    @Test
    void noAdjCells(){
        player.pos = new Position(0,0);
        final Cell fakecell = new FakeCell();


        final Board emptyBoard = new Board() {

            @Override
            public List<Cell> getBoardCells() {
                throw new UnsupportedOperationException("Unimplemented method 'getBoardCells'");
            }

            @Override
            public Cell getCell(Position p) {
                return fakecell;
            }

            @Override
            public Position getCellPosition(Cell cell) {
                return new Position(0,0);
            }

            @Override
            public List<Cell> getAdjacentCells(Position p) {
                return List.of();
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
                return false;
            }

            @Override
            public boolean isAdjacent(Position p1, Position p2) {
                return false;
            }
            
        };

        dynamite.context = new ItemContext(emptyBoard, player, null);
        assertThrows(IllegalStateException.class, () -> {
            dynamite.applyEffect(player);
        }, "should throw exception when no cells are nearby");
    }

    
    private static final class FakeBoard implements Board{

        private final int size;
        private final Cell[][] cells;
        boolean forceEmptyAdj = false;

        FakeBoard(int size) {
            this.size = size;
            cells = new Cell[size][size];
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    cells[i][j] = new FakeCell();
                }
            }
        }


        @Override
        public List<Cell> getBoardCells() {
            throw new UnsupportedOperationException("Unimplemented method 'getBoardCells'");
        }

        @Override
        public Cell getCell(Position p) {
            return cells[p.x()][p.y()];
        }

        @Override
        public Position getCellPosition(Cell cell) {
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if (cells[i][j] == cell) {
                        return new Position(i, j);
                    }
                }
            }
        
            throw new IllegalArgumentException("Cell not found");
        }

        @Override
        public List<Cell> getAdjacentCells(Position p) {
            if (forceEmptyAdj) return List.of();
            List<Cell> adj = new ArrayList<>();
            int[] dx = {-1, 0, 1, 0};
            int[] dy = {0, -1, 0, 1};
            for (int i = 0; i < 4; i++) {
                int nx = p.x() + dx[i];
                int ny = p.y() + dy[i];
                Position np = new Position(nx, ny);
                if (isPositionValid(np)) adj.add(getCell(np));
            }
            return adj;
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
            return p.x() >= 0 && p.x() < size && p.y() >= 0 && p.y() <size; 
        }

        @Override
        public boolean isAdjacent(Position p1, Position p2) {
            return Math.abs(p1.x() - p2.x()) <= 1 && Math.abs(p1.y() - p2.y()) <= 1;
        }
        
    }

    private final static class FakeCell implements Cell{

        boolean disarmed = false;
        boolean revealed = false;

        @Override
        public void reveal() {
            disarmed = true;
            revealed = true;
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
            return false;
        }

        @Override
        public int getAdjacentTraps() {
            return 0;
        }

        @Override
        public void setAdjacentTraps(int n) {
            throw new UnsupportedOperationException("Unimplemented method 'setAdjacentTraps'");
        }

        @Override
        public boolean hasContent() {
            return false;
        }

        @Override
        public Optional<CellContent> getContent() {
            return Optional.empty();
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

    private static final class FakePlayer implements PlayerOperations{

        private Position pos;

        FakePlayer(Position pos){
            this.pos = pos;
        }

        @Override
        public Position position() {
            return pos;
        }

        @Override
        public int livesCount() {
            return 3;
        }

        @Override
        public int goldCount() {
            return 0;
        }

        @Override
        public Inventory inventory() {
            return null;
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

}
