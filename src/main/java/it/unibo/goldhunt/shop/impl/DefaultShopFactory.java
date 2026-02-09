package it.unibo.goldhunt.shop.impl;

import java.util.List;

import it.unibo.goldhunt.player.api.PlayerOperations;
import it.unibo.goldhunt.shop.api.Shop;
import it.unibo.goldhunt.shop.api.ShopFactory;
import it.unibo.goldhunt.shop.api.ShopItem;

public class DefaultShopFactory implements ShopFactory{
    
    @Override
    public Shop create(
        final PlayerOperations player,
        final List<ShopItem> catalog,
        final int maxPurchases
    ) {
        return new ShopImpl(player, catalog, maxPurchases);
    }
}
