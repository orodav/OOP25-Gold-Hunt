package it.unibo.goldhunt.shop.api;

import java.util.List;

import it.unibo.goldhunt.items.api.ItemTypes;

/**
 * Models the in-game shop available at the end of a level.
 * The shop is immutable: a purchase return a result containing
 * the updated Player.
 */
public interface Shop {

    /**
     * Returns the list of items currently available for purchase.
     * the GUI should build its buttons from this list.
     * Items not sold are not shown.
     * @return
     */
    List<ShopItem> items();

    /**
     * Attempts to buy exactly one unit of the specifice item.
     * 
     * @param type the item type to buy
     * @return a {@link ShopActionResult} describing the outcome
     * and the updated player
     * @throws IllegalArgumentException if {@code type} is {@code null}
     */
    ShopActionResult buy(ItemTypes type);

    /**
     * Returns the remaining number of purchases allowed in this shop sesison.
     * 
     * @return remaining purchases (>= 0)
     */
    int remainingPurchases();
}
