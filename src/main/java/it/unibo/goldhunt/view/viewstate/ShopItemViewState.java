package it.unibo.goldhunt.view.viewstate;

import it.unibo.goldhunt.items.api.ItemTypes;

/**
 * Immutable snapshot describing how a single shop item should be displayed in the UI.
 * 
 * @param type the item type
 * @param name the display name
 * @param shortString a short representation for compact UI
 * @param price the item price
 * @param affordable whether the player can afford the item
 * @param enabled whether the item button should be enabled
 */
public record ShopItemViewState(
    ItemTypes type,
    String name,
    String shortString,
    int price,
    boolean affordable,
    boolean enabled
) { }
