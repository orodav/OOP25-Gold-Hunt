package it.unibo.goldhunt.items.impl;

import java.util.HashSet;

import java.util.Set;

import it.unibo.goldhunt.board.api.Board;
import it.unibo.goldhunt.board.api.Cell;
import it.unibo.goldhunt.items.api.KindOfItem;
import it.unibo.goldhunt.items.api.Revealable;
import it.unibo.goldhunt.player.api.PlayerOperations;

//luca

/**
 * Represent the "Map" item in the game.
 * 
 * <p>
 * When used, the {@code Chart} reveals nearby traps within a certain radius.
 * It flags any cell that contains a {@link Revealable} content.
 */
public class Chart extends AbstractItem {

    /**
     * Name of the item.
     */
    private static final String ITEM_NAME = "Map";

    /**
     * Set of cells that have already been collected during the effect.
     */
    private final Set<Cell> collectedCells = new HashSet<>();

    /**
     * Returns the name of the item.
     * 
     * @return the String "Map"
     */
    @Override
    public String getName() {
        return ITEM_NAME;
    }

    /**
     * Recursively collects cells within a certai radius.
     * 
     * @param pos starting cell
     * @param radius remining radius
     * @param collected set of collected cells
     * @param board game board
     */
    private void recursiveCollect(final Cell pos, final int radius, final Set<Cell> collected, final Board board) {
        collected.add(pos);

        if (radius <= 0) {
            return;
        }

        for (final Cell nbor : board.getAdjacentCells(board.getCellPosition(pos))) {
            if (!collected.contains(nbor)) {
                recursiveCollect(nbor, radius - 1, collected, board);
            }
        }
    }

    /**
     * Returns a short string representation of the item.
     * 
     * @return "M"
     */
    @Override
    public String shortString() {
        return "M";
    }

    /**
     * Apply the effect to reveal traps nearby.
     * 
     * @param playerop the player using the item
     * @return the same PlayerOperations object after the effect
     * @throws IllegalStateException if the item context is not bound
     */
    @Override
    public PlayerOperations applyEffect(final PlayerOperations playerop) {
        if (getContext() == null) {
            throw new IllegalStateException("item context not bound");
        }

        final var board = getContext().board();

        recursiveCollect(board.getCell(playerop.position()), RADIUS, collectedCells, board);

        collectedCells.stream()
        .filter(c -> c.getContent().isPresent() && c.getContent().get() instanceof Revealable)
        .forEach(Cell::toggleFlag);
        return playerop;
    }

    /**
     * Returns the type of this item.
     * 
     * @return {@link KindOfItem#CHART}
     */
    @Override
    public KindOfItem getItem() {
        return KindOfItem.CHART;
    }

}
