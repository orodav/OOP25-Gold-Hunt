package it.unibo.goldhunt.shop.api;

import it.unibo.goldhunt.items.api.ItemTypes;

/**
 * Represents an item available for purchase in the shop.
 * 
 * @param type the {@link ItemTypes} of the item
 * @param price the price in gold for a single unit
 */
public record ShopItem(
    ItemTypes type,
    int price
) { }
