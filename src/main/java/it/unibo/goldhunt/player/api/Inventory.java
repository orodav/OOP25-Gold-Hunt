package it.unibo.goldhunt.player.api;

import it.unibo.goldhunt.items.api.ItemTypes;

//davv
public interface Inventory {

    Inventory add(ItemTypes item, int quantity);

    Inventory remove(ItemTypes item, int quantity);

    /** Default Method */
    default boolean hasAtLeast(ItemTypes item, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be > 0");
        }
        return this.quantity(item) >= quantity;
    }

    int quantity(ItemTypes item);
}

/* per Testing: 
    quantity per nr item = 0
    funzionamento corretto di add e remove
    comportamento remove oltre la quantità 
*/