package it.unibo.goldhunt.shop.api;

import it.unibo.goldhunt.items.api.ItemTypes;

/**
 * Models an item sold in the shop.
 * 
 * @param type the item type
 * @param price the price in gold for a single unit
 */
public record ShopItem(
    ItemTypes type,
    int price
) { }
