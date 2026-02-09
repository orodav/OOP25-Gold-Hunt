package it.unibo.goldhunt.items.impl;

import java.util.HashSet;

import java.util.Set;

import it.unibo.goldhunt.board.api.Board;
import it.unibo.goldhunt.board.api.Cell;
import it.unibo.goldhunt.items.api.Revealable;


//luca

/**
 * Represent the "Map" item in the game.
 * <p>
 * When used, the {@code Chart} reveals nearby traps within a certain radius.
 * It flags any cell that contains a {@link Revealable} content.
 */
public class Chart extends Item{

    /**
     * Set of cells that have already been collected during the effect.
     */
    private final Set<Cell> collectedCells = new HashSet<>();

    /**
     * Name of the item
     */
    private final static String ITEM_NAME = "Map";

    /**
     * Returns the name of the item
     * 
     * @return the String "Map"
     */
    @Override
    public String getName() {
        return ITEM_NAME;
    }

    private void recursiveCollect(Cell pos, int radius, Set<Cell> collected, Board board){
        collected.add(pos);

        if(radius <= 0){
            return;
        }

        for(Cell nbor : board.getAdjacentCells(board.getCellPosition(pos))){
            if(!collected.contains(nbor)){
                recursiveCollect(nbor, radius - 1, collected, board);
            }
        }
    }

    @Override
    public String shortString() {
        return "M";
    }

    @Override
    public boolean applyEffect() {
        if (context == null) {
            throw new IllegalStateException("item context not bound");
        }   
        var board = context.board();
        var playerop = context.playerop();

        recursiveCollect(board.getCell(playerop.position()), RADIUS, collectedCells, board);
        collectedCells.stream()
        .filter(c-> c.getContent().isPresent() && c.getContent().get() instanceof Revealable)
        .forEach(Cell::toggleFlag);
        return true;
    }

}
