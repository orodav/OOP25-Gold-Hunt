package it.unibo.goldhunt.items.impl;
import java.util.List;
//luca
import java.util.Random;

import it.unibo.goldhunt.board.api.Cell;
import it.unibo.goldhunt.items.api.ClearCells;
import it.unibo.goldhunt.items.api.KindOfItem;
import it.unibo.goldhunt.player.api.PlayerOperations;

public class Pickaxe extends Item implements ClearCells{

    private static final String ITEM_NAME = "Pickaxe";
    
    /**
     * Returns the name of the item
     * 
     * @return "Pickaxe"
     */
    @Override
    public String getName() {
        return ITEM_NAME;
    }

    /**
     * Randomly chooses a row or column and disarms all cells within it.
     * 
     * @param playerop the current player.
     * @return the player operations.
     * @throws IllegalStateException if the context is not set.
     */
    @Override
    public PlayerOperations applyEffect(final PlayerOperations playerop) {
        if(context == null) {
            throw new IllegalStateException("cannot bind items");
        }

        final var board = context.board();
        final Random random = new Random();
        final int idx = random.nextInt(board.getBoardSize());
        final List<Cell> cells = random.nextBoolean() 
        ? board.getRow(idx) 
        : board.getColumn(idx);

        disarm(cells);
        return playerop;
    }

    /**
     * Returns a short string representing the item.
     * 
     * @return "P"
     */
    @Override
    public String shortString() {
        return "P";
    }

    /**
     * Returns the type of this item.
     * 
     * @return {@link KindOfItem#PICKAXE}
     */
    @Override
    public KindOfItem getItem() {
        return KindOfItem.PICKAXE;
    }

}
