package it.unibo.goldhunt.shop.api;

import java.util.List;

import it.unibo.goldhunt.player.api.PlayerOperations;

public interface ShopFactory {

    Shop create(
        PlayerOperations player,
        List<ShopItem> catalog,
        int maxPurchases
    );
}
