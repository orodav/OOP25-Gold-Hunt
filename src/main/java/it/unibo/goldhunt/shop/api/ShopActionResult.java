package it.unibo.goldhunt.shop.api;

import it.unibo.goldhunt.player.api.Player;

public record ShopActionResult(
    ShopActionType type,
    ShopStopReason reason,
    ShopActionEffect effect,
    int remainingPurchases,
    Player player,
    Shop shop
) {}
