package it.unibo.goldhunt.view.viewstate;

import java.util.List;
import java.util.Objects;

/**
 * Immutable snapshot containing the information displayed in the inventory section.
 * 
 * @param items the list of inventory items currently available to the player
 */
public record InventoryViewState(
    List<InventoryItemViewState> items
) {
    /**
     * Canonical constructor with validation and defensive copying.
     *
     * <p>
     * Ensures that {@code items} is not {@code null} and defensively copies
     * the provided list to preserve the immutability of the record state.
     *
     * @throws NullPointerException if {@code items} is {@code null}
     */
    public InventoryViewState {
        Objects.requireNonNull(items, "items can't be null");
        items = List.copyOf(items);
    }
}
