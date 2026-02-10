package it.unibo.goldhunt.items.impl;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.goldhunt.board.api.Board;
import it.unibo.goldhunt.board.api.Cell;
import it.unibo.goldhunt.engine.api.Position;
import it.unibo.goldhunt.items.api.CellContent;
import it.unibo.goldhunt.items.api.ItemContext;
import it.unibo.goldhunt.items.api.ItemTypes;
import it.unibo.goldhunt.items.api.Revealable;
import it.unibo.goldhunt.player.api.Inventory;
import it.unibo.goldhunt.player.api.PlayerOperations;

public class ChartTest {

    private Chart chart;
    private Board board;

    @BeforeEach
    void init() {
        board = new FakeBoard(5);
        FakePlayer player = new FakePlayer(new Position(2,2));
        chart = new Chart();

        chart.context = new ItemContext(board, player, null);
    }

    @Test
    void testApplyEffectTrap() {

        TrapFake trap = new TrapFake();
        Position trapPos = new Position(2, 3);
        Cell targetCell = board.getCell(trapPos);
        PlayerOperations playerop = new FakePlayer(trapPos);
        targetCell.setContent(trap);

        chart.applyEffect(playerop);

        assertTrue(targetCell.isFlagged(), "Trap should be revealed (flagged)");
    }

    @Test
    void testApplyEffectNormalCells() {
        Position emptyPos = new Position(1, 1);
        Cell emptyCell = board.getCell(emptyPos);
        FakePlayer player = new FakePlayer(emptyPos);
        
        chart.applyEffect(player);

        assertFalse(emptyCell.isFlagged(), "Empty cells should not be flagged");
    }

    static class CellFake implements Cell {
        private CellContent content;
        private boolean flagged = false;

        @Override
        public void reveal() {
        }
        @Override
        public boolean isRevealed() {
            return false; 
            }
        @Override
        public void toggleFlag() {
            flagged = !flagged; 
            }
        @Override
        public boolean isFlagged() {
            return flagged; 
        }
        @Override
        public int getAdjacentTraps() { 
            return 0; 
        }
        @Override
        public void setAdjacentTraps(int n) {
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
        public void setContent(CellContent content) { 
            this.content = content; 
        }
        @Override
        public void removeContent() { 
            content = null; 
        }
    }

    static class FakeBoard implements Board {
        private final int size;
        private final Map<Position, Cell> cells = new HashMap<>();

        public FakeBoard(int size) {
            this.size = size; 
            }

        @Override
        public Cell getCell(Position p) {
            return cells.computeIfAbsent(p, k -> new CellFake());
        }
        @Override public int getBoardSize() {
            return size; 
            }
        @Override public boolean isPositionValid(Position p) {
            return p.x() >= 0 && p.x() < size && p.y() >= 0 && p.y() < size; 
            }

        @Override
        public List<Cell> getBoardCells() {
            throw new UnsupportedOperationException("Unimplemented method 'getBoardCells'");
        }

        @Override
        public Position getCellPosition(Cell cell) {
            for (Map.Entry<Position, Cell> entry : cells.entrySet()) {
                if (entry.getValue() == cell) {
                    return entry.getKey();
                }
            }
            return null;
        }

        @Override
        public List<Cell> getAdjacentCells(Position p) {
            List<Cell> adj = new ArrayList<>();
            int[] dx = {-1, 0, 1, 0};
            int[] dy = {0, -1, 0, 1};

            for (int i = 0; i < 4; i++) {
                int nborx = p.x() + dx[i];
                int nbory = p.y() + dy[i];
                Position nborp = new Position(nborx, nbory);
                if (isPositionValid(nborp)) {
                    adj.add(getCell(nborp));
                }
            }
            return adj;
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

    static class FakePlayer implements PlayerOperations {
        private Position pos; 

        FakePlayer(Position pos){
            this.pos = pos;
        }  
        @Override
        public Position position() {
            return this.pos;
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

    static class TrapFake implements Revealable {

        private boolean revealed = false;

        @Override
        public String shortString() {
            return "T";
        }

        public boolean revealed(){
        return revealed;
        }

        @Override
        public PlayerOperations applyEffect(PlayerOperations playerop) {
            return playerop;
        }
    }


}
   