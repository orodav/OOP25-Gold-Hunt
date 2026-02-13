package it.unibo.goldhunt.view.viewstate;

import java.util.List;
import java.util.Objects;

/**
 * Immutable snapshot describing the current shop state and available purchases.
 * 
 * @param remainingPurchases the number of purchases still allowed in this session
 * @param items the items available for purchase in the shop
 */
public record ShopViewState(
    int remainingPurchases,
    List<ShopItemViewState> items
) {

    /**
     * Canonical constructor with validation and defensive copy.
     * 
     * <p>
     * The copied list guarantees immutability of the record state.
     * 
     * @throws IllegalArgumentException if {@code remainingPurchases} is negative
     * @throws NullPointerException if {@code items} is {@code null}
     */
    public ShopViewState {
        if (remainingPurchases < 0) {
            throw new IllegalArgumentException("remainingPurchases must be >= 0");
        }
        Objects.requireNonNull(items, "item can't be null");
        items = List.copyOf(items);
    }
}
