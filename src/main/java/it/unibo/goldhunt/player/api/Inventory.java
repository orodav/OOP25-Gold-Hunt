package main.java.it.unibo.goldhunt.player.api;

//davv
public interface Inventory {

    void add(Item item, int quantity);

    void remove(Item item, int quantity);

    /** Default Method */
    default boolean hasAtLeast(Item item, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be > 0");
        }
        return this.quantity(item) >= quantity;
    }

    int quantity(Item item);
}

/* per Testing: 
    quantity per nr item = 0
    funzionamento corretto di add e remove
    comportamento remove oltre la quantità 
*/