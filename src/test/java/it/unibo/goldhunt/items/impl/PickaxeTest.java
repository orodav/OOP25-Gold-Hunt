package it.unibo.goldhunt.items.impl;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import it.unibo.goldhunt.board.api.Board;
import it.unibo.goldhunt.board.api.Cell;
import it.unibo.goldhunt.engine.api.Position;
import it.unibo.goldhunt.items.api.CellContent;

public class PickaxeTest {
    private Pickaxe pick;
    private FakeBoard board;

    @BeforeEach
    void init(){
        board = new FakeBoard(5);
        pick = new Pickaxe();
        pick.board = board;
    }

    @RepeatedTest(10)
    void effectPick(){
        pick.applyEffect();
        boolean found = false;

        for (int i = 0; i < board.size; i++) {
            if (board.isRowDisarmed(i) || board.isColumnDisarmed(i)) {
                found = true;
                break;
            }
        }
        assertTrue(found, "pickaxe should reveal and disarm a row or a column");
    }
    
    @Test
    void pickOnTrap(){
        board.getRow(0).forEach(c -> c.setContent(new FakeTrap()));
    
    pick.applyEffect();
    }

    private final static class FakeTrap implements CellContent{

        @Override
        public boolean applyEffect() {
            return true;
        }

        @Override
        public String shortString() {
            return "T";
        }
        @Override
        public boolean isTrap(){
            return true;
        }
        
    }

    private static final class FakeBoard implements Board{

        private final int size;
        private final Cell[][] cell;

        FakeBoard(int size){
            this.size = size;
            cell = new Cell[size][size];
            for (int i = 0; i < size; i++){
                for (int j = 0; j < size; j++){
                    cell[i][j] = new FakeCell();

                }

            }
        }

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

        boolean isRowDisarmed(int row) {
            for (Cell c : getRow(row)) if (!((FakeCell)c).disarmed) return false;
            return true;
        }
        boolean isColumnDisarmed(int col) {
            for (Cell c : getColumn(col)) if (!((FakeCell)c).disarmed) return false;
            return true;
        }


        @Override
        public int getBoardSize() {
            return size;
        }

        @Override
        public List<Cell> getRow(int index) {
            List<Cell> row = new ArrayList<>();
            for(int j = 0; j < size; j++){
                row.add(cell[index][j]);
            }
            return row;
        }

        @Override
        public List<Cell> getColumn(int index) {
            List<Cell> col = new ArrayList<>();
            for(int i = 0; i < size; i++){
                col.add(cell[i][index]);
            }
            return col;
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

    private static final class FakeCell implements Cell{

        boolean disarmed = false;
        private CellContent content;
        private boolean revealed = false;

        @Override
        public void reveal() {
            revealed = true;
            disarmed = true;
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
            throw new UnsupportedOperationException("Unimplemented method 'isFlagged'");
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
            this.content = null;
        }
        
    }
}
