package it.unibo.goldhunt.items.impl;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.goldhunt.board.api.Board;
import it.unibo.goldhunt.board.api.Cell;
import it.unibo.goldhunt.board.api.CellFactory;
import it.unibo.goldhunt.board.impl.BaseCellFactory;
import it.unibo.goldhunt.board.impl.SquareBoard;
import it.unibo.goldhunt.engine.api.Position;
import it.unibo.goldhunt.items.api.Revealable;
import it.unibo.goldhunt.player.impl.InventoryImpl;
import it.unibo.goldhunt.player.impl.PlayerImpl;

public class ChartTest {

    private Chart chart;
    private Board board;
    private PlayerImpl player;
    private CellFactory cellFactory;
    private static final int BOARD_SIZE = 5;

    @BeforeEach
    void init(){
        board = SquareBoard.create(BOARD_SIZE);
        cellFactory = new BaseCellFactory();

        for(int x = 0; x < BOARD_SIZE ; x++){
            for(int y = 0; y < BOARD_SIZE; y++){
                board.setCell(cellFactory.createCell(), new Position(x, y));
            }
        }

        Position startPos = new Position(2, 2);
        player = new PlayerImpl(startPos, 3, 0, new InventoryImpl());

        ItemFactoryImpl itemFactory = new ItemFactoryImpl();
        chart = (Chart) itemFactory.generateItem("M");

        chart.board = board;
        chart.player = player;
    }


    @Test
    public void testApplyEffectTrap(){
        Trap trap = new Trap();
        Position trapPos = new Position(2, 3);
        Cell targetCell = board.getCell(trapPos);

        if(trap instanceof Revealable){
            targetCell.setContent(trap);

            chart.applyEffect();
            assertTrue(targetCell.isFlagged(), "trap revaeled");
        }else{
            throw new IllegalStateException("Revealable not implemented");
        }

    }

    @Test
    public void testApplyEffectNormalCells(){
        Position emptyPos = new Position(1,1);
        Cell emptyCell = board.getCell(emptyPos);
        assertFalse(emptyCell.isFlagged(), "cells without revealable should not be flagged");
    }

}
