package it.unibo.goldhunt.view.viewstate;

import java.util.List;

/**
 * Immutable snapshot containing the information displayed in the inventory section.
 */
public record InventoryViewState(
    List<InventoryItemViewState> items
) { }
