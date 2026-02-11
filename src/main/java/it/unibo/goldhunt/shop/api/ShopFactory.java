package it.unibo.goldhunt.shop.api;

import java.util.List;

import it.unibo.goldhunt.player.api.PlayerOperations;

/**
 * Factory responsible for creating {@link Shop} instances.
 * 
 * <p>
 * A {@code ShopFactory} initializes a shop session for a given player.
 */
@FunctionalInterface
public interface ShopFactory {

    /**
     * Creates a new shop instance.
     * 
     * @param player the player associated with the shop session
     * @param catalog the list of available {@link ShopItem}
     * @param maxPurchases the maximum number of purchases allowed
     * @return a new {@code Shop} instance
     * @throws IllegalArgumentException if {@code player} or {@code catalog}
     *                                  is {@code null}, or if
     *                                  {@code maxPurchases is negative}
     */
    Shop create(
        PlayerOperations player,
        List<ShopItem> catalog,
        int maxPurchases
    );
}
