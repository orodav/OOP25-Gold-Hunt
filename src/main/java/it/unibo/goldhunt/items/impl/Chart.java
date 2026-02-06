package it.unibo.goldhunt.items.impl;

import java.util.HashSet;

import java.util.Set;

import it.unibo.goldhunt.board.api.Cell;
import it.unibo.goldhunt.items.api.Revealable;


//luca
public class Chart extends Item{

    Set<Cell> collectedCells = new HashSet<>();

    private final static String ITEM_NAME = "Map";

    @Override
    public String getName() {
        return ITEM_NAME;
    }

    @Override
    public boolean applyEffect() {
        recursiveCollect(board.getCell(player.position()), RADIUS, collectedCells);
        collectedCells.stream()
        .filter(c-> c.getContent().isPresent() && c.getContent().get() instanceof Revealable)
        .forEach(Cell::toggleFlag);
        return true;

    }
    
    private void recursiveCollect(Cell pos, int radius, Set<Cell> collected){
        collected.add(pos);

        if(radius<=0){
            return;
        }

        

        for(Cell nbor : board.getAdjacentCells(board.getCellPosition(pos))){
            if(!collected.contains(nbor)){
                recursiveCollect(nbor, radius - 1, collected);
            }
        }
    }

    @Override
    public String shortString() {
        return "M";
    }

}
