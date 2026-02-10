package it.unibo.goldhunt.items.impl;

import java.util.List;

import it.unibo.goldhunt.board.api.Cell;
import it.unibo.goldhunt.items.api.ClearCells;
import it.unibo.goldhunt.items.api.KindOfItem;
import it.unibo.goldhunt.player.api.PlayerOperations;

//luca
public class Dynamite extends Item implements ClearCells{

    private final static String ITEM_NAME = "Dynamite";
    


    @Override
    public String getName() {
        return ITEM_NAME;
    }

    @Override
    public PlayerOperations applyEffect(PlayerOperations playerop) {

        if(context == null){
            throw new IllegalStateException("item cannot bound");
        }

        var board = context.board();

        Cell currentCell = board.getCell(playerop.position());
        
        List<Cell> adjacent = board.getAdjacentCells(board.getCellPosition(currentCell));
        if(adjacent == null || adjacent.isEmpty()){
            throw new IllegalStateException();
        
        }
        disarm(adjacent);
        return playerop;
        }
        

    @Override
    public String shortString() {
        return "D";
    }

    @Override
    public KindOfItem getItem() {
        return KindOfItem.DYNAMITE;
    }

}
