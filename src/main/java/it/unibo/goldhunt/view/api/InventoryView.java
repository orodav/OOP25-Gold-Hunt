package it.unibo.goldhunt.view.api;

import javax.swing.JComponent;

import it.unibo.goldhunt.items.api.ItemTypes;
import it.unibo.goldhunt.view.viewstate.InventoryViewState;

/**
 * View component responsible for rendering the inventory section.
 */
public interface InventoryView {

    /**
     * Re-renders the inventory UI based on the provided immutable snapshot.
     *
     * @param state the inventory view state
     * @throws NullPointerException if state is null
     */
    void render(InventoryViewState state);

    /**
     * Returns the root Swing component of this sub-view.
     *
     * @return the root component
     */
    JComponent component();

    /**
     * Sets the listener notified when the user clicks on an inventory item.
     *
     * @param listener the listener
     * @throws NullPointerException if listener is null
     */
    void setListener(Listener listener);

    /**
     * Listener for inventory UI actions.
     */
    @FunctionalInterface
    interface Listener {

        /**
         * Called when the user requests to use an item from the inventory.
         *
         * @param type the item type
         */
        void onUseItem(ItemTypes type);
    }
}
