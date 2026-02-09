package it.unibo.goldhunt.items.impl;


import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


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
import it.unibo.goldhunt.items.api.Revealable;
import it.unibo.goldhunt.player.api.Inventory;
import it.unibo.goldhunt.player.api.Player;


public class ChartTest {


    private Chart chart;
    private Board board;


    @BeforeEach
    void init() {
        board = new FakeBoard(5);
        Player player = new FakePlayer(new Position(2, 2));
        chart = new Chart();
    }


    @Test
    void testApplyEffectTrap() {


        TrapFake trap = new TrapFake();
        Position trapPos = new Position(2, 3);
        Cell targetCell = board.getCell(trapPos);
        targetCell.setContent(trap);


        chart.applyEffect();


        assertTrue(targetCell.isFlagged(), "Trap should be revealed (flagged)");
    }


    @Test
    void testApplyEffectNormalCells() {
        Position emptyPos = new Position(1, 1);
        Cell emptyCell = board.getCell(emptyPos);


        chart.applyEffect();


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
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'getBoardCells'");
        }


        @Override
        public Position getCellPosition(Cell cell) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'getCellPosition'");
        }


        @Override
        public List<Cell> getAdjacentCells(Position p) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'getAdjacentCells'");
        }


        @Override
        public List<Cell> getRow(int index) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'getRow'");
        }


        @Override
        public List<Cell> getColumn(int index) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'getColumn'");
        }


        @Override
        public boolean isAdjacent(Position p1, Position p2) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'isAdjacent'");
        }


    }


    static class FakePlayer implements Player {
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
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'livesCount'");
        }
        @Override
        public int goldCount() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'goldCount'");
        }
        @Override
        public Inventory inventory() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'inventory'");
        }
       
    }


    static class TrapFake implements Revealable {


        @Override
        public boolean applyEffect() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'applyEffect'");
        }


        @Override
        public String shortString() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'shortString'");
        }
    }
}
   


   

