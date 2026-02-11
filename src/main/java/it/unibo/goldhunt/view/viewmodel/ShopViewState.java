package it.unibo.goldhunt.view.viewmodel;

import java.util.List;

/**
 * Immutable snapshot describing the current shop state and available purchases.
 */
public record ShopViewState(
    int remainingPurchases,
    List<ShopItemViewState> items
) { }
