package it.unibo.goldhunt.shop.api;

import it.unibo.goldhunt.player.api.PlayerOperations;

public record ShopActionResult(
    ShopActionType type,
    ShopStopReason reason,
    ShopActionEffect effect,
    int remainingPurchases,
    PlayerOperations player,
    Shop shop
) {}
