package it.unibo.goldhunt.items.impl;
import java.util.List;
//luca
import java.util.Random;

import it.unibo.goldhunt.board.api.Cell;
import it.unibo.goldhunt.items.api.ClearCells;

public class Pickaxe extends Item implements ClearCells{

    private final static String ITEM_NAME = "Pickaxe";
    
    @Override
    public String getName() {
        return ITEM_NAME;
    }

    @Override
    public boolean applyEffect() {
        Random random = new Random();
        int idx = random.nextInt(board.getBoardSize());
        List<Cell> cells = random.nextBoolean() 
        ? board.getRow(idx) 
        : board.getColumn(idx);

        disarm(cells);
        return true;
    }

    @Override
    public String shortString() {
        return "P";
    }
}