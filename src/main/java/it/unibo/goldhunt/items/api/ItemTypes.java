package it.unibo.goldhunt.items.api;

import it.unibo.goldhunt.player.api.PlayerOperations;

//luca

/**
 * Represent the item that can occupy the cell.
 * 
 * <p>
 * An {@code ItemTypes} instance is also a {@link CellContent},
 * meaning it can be placed inside a cell and apply effect when
 * activated.
 */
public interface ItemTypes extends CellContent {

    /**
     * Returns the name of the item type.
     * 
     * @return the name of the item.
     */
    String getName();

    /**
     * Returns the kind of item.
     * 
     * @return the kind of item 
     */
    KindOfItem getItem();

    default PlayerOperations toInventory(final PlayerOperations playerop){
        return playerop.addItem(getItem(), 1);
    }
}
