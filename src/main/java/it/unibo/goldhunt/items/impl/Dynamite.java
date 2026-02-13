package it.unibo.goldhunt.items.impl;

import java.util.List;

import it.unibo.goldhunt.board.api.Cell;
import it.unibo.goldhunt.items.api.ClearCells;
import it.unibo.goldhunt.items.api.KindOfItem;
import it.unibo.goldhunt.player.api.PlayerOperations;

//luca
/**
 * Represents the "Dynamite" item.
 * 
 * <p>
 * When used it disarms the cells in the adjacent cells and reveals them.
 */
public final class Dynamite extends AbstractItem implements ClearCells { 

    private static final String ITEM_NAME = "Dynamite";

    /**
     * Returns the name of the item.
     * 
     * @return "Dynamite"
     */
    @Override
    public String getName() {
        return ITEM_NAME;
    }

    /**
     * Applies the Dynamite effect, clearing traps in the adjacent cells.
     * 
     * @param playerop the player using the item 
     * @return the same PlayerOperations object after the effect
     * @throws IllegalStateException if the item context is not bound or no adjacent cells exist
     */
    @Override
    public PlayerOperations applyEffect(final PlayerOperations playerop) {

        if (getContext() == null) {
            throw new IllegalStateException("item cannot bound");
        }

        final var board = getContext().board();

        final Cell currentCell = board.getCell(playerop.position());
        final List<Cell> adjacent = board.getAdjacentCells(board.getCellPosition(currentCell));
        if (adjacent == null || adjacent.isEmpty()) {
            throw new IllegalStateException();
        }
        disarm(adjacent);
        return playerop;
        }

    /**
     * Returns a short string representation of the item.
     * 
     * @return "D"
     */
    @Override
    public String shortString() {
        return "D";
    }

    /**
     * Returns the type of this item.
     * 
     * @return {@link KindOfItem#DYNAMITE}
     */
    @Override
    public KindOfItem getItem() {
        return KindOfItem.DYNAMITE;
    }

    @Override
    public PlayerOperations toInventory(final PlayerOperations playerop) {
        return playerop.addItem(this.getItem(), 1);
    }
}
