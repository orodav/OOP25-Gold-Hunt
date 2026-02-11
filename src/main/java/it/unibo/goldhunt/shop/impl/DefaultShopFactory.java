package it.unibo.goldhunt.shop.impl;

import java.util.List;

import it.unibo.goldhunt.player.api.PlayerOperations;
import it.unibo.goldhunt.shop.api.Shop;
import it.unibo.goldhunt.shop.api.ShopFactory;
import it.unibo.goldhunt.shop.api.ShopItem;

/**
 * Default implementation of {@link ShopFactory}.
 * This factory creates standard {@link Shop} instances
 * backed by {@link ShopImpl}.
 */
public class DefaultShopFactory implements ShopFactory {
    /**
     * Creates a new {@link ShopImpl} instance.
     * 
     * @param player the player associated with the shop session
     * @param catalog the list of available shop items
     * @param maxPurchases the maximum number of purchases allowed
     * @return a new {@link Shop} instance
     */
    @Override
    public Shop create(
        final PlayerOperations player,
        final List<ShopItem> catalog,
        final int maxPurchases
    ) {
        return new ShopImpl(player, catalog, maxPurchases);
    }
}
