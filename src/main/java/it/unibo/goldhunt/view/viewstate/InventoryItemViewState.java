package it.unibo.goldhunt.view.viewstate;

import it.unibo.goldhunt.items.api.ItemTypes;

/**
 * Immutable snaphot describing a single inventory item displayed in the UI.
 * 
 * @param type the item type
 * @param name the display name
 * @param shortString a short representation for compact UI
 * @param quantity the amount currently owned
 * @param usable whether the item can currently be used
 * @param selected whether the item is corrently selected in the UI
 */
public record InventoryItemViewState(
    ItemTypes type,
    String name,
    String shortString,
    int quantity,
    boolean usable,
    boolean selected
) { }
