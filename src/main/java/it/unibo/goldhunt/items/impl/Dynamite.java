package it.unibo.goldhunt.items.impl;

import java.util.List;

import it.unibo.goldhunt.board.api.Cell;
import it.unibo.goldhunt.items.api.ClearCells;

//luca
public class Dynamite extends Item implements ClearCells{

    private final static String ITEM_NAME = "Dynamite";
    


    @Override
    public String getName() {
        return ITEM_NAME;
    }

    @Override
    public boolean applyEffect() {

        if(context == null){
            throw new IllegalStateException("item cannot bound");
        }

        var board = context.board();
        var playerop = context.playerop();

        Cell currentCell = board.getCell(playerop.position());
        
        List<Cell> adjacent = board.getAdjacentCells(board.getCellPosition(currentCell));
        if(adjacent == null || adjacent.isEmpty()){
            throw new IllegalStateException("no cells nearby");
        }
        disarm(adjacent);
        return true;
        }
        

    @Override
    public String shortString() {
        return "D";
    }

}
