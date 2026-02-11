package it.unibo.goldhunt.shop.api;

import it.unibo.goldhunt.player.api.PlayerOperations;

/**
 * Represents the result of a shop action.
 * 
 * <p>
 * A {@code ShopActionResult} encapsulates the outcome of a purchase attempt.
 * 
 * @param type type the type of shop action performed
 * @param reason the rasion why the action completed or stopped
 * @param effect the effect producec by the action
 * @param remainingPurchases the number of purchases still allowed
 * @param player the updated player after the action
 * @param shop the updated shop instance after the action
 */
public record ShopActionResult(
    ShopActionType type,
    ShopStopReason reason,
    ShopActionEffect effect,
    int remainingPurchases,
    PlayerOperations player,
    Shop shop
) { }
