package it.unibo.goldhunt.shop.api;

import java.util.List;

import it.unibo.goldhunt.items.api.ItemTypes;

/**
 * Models the in-game shop available at the end of a level.
 * 
 * The shop is immutable: each purchase attempt returns a 
 * {@link ShopActionResult} containing the outcome 
 * and the updated player.
 */
public interface Shop {

    /**
     * Returns the list of items currently available for purchase.
     * the GUI should build its buttons from this list.
     * Items not sold are not shown.
     * 
     * @return the list of purchasable {@link ShopItem}
     */
    List<ShopItem> items();

    /**
     * Attempts to buy exactly one unit of the specifice item.
     * 
     * @param type the item type to buy
     * @return a {@link ShopActionResult} describing the outcome
     *         and the updated player state
     * @throws IllegalArgumentException if {@code type} is {@code null}
     */
    ShopActionResult buy(ItemTypes type);

    /**
     * Returns the remaining number of purchases allowed in this shop sesison.
     * 
     * @return the remaining purchases
     */
    int remainingPurchases();
}
