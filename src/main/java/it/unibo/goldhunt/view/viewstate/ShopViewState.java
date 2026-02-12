package it.unibo.goldhunt.view.viewstate;

import java.util.List;

/**
 * Immutable snapshot describing the current shop state and available purchases.
 * 
 * @param remainingPurchases the number of purchases still allowed in this session
 * @param items the items available for purchase in the shop
 */
public record ShopViewState(
    int remainingPurchases,
    List<ShopItemViewState> items
) { }
