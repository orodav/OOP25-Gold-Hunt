package it.unibo.goldhunt.shop.api;

/**
 * Describes the reason why a shop action gets blocked.
 */
public enum ShopStopReason {

    /**
     * The action has been successfully applied.
     */
    NONE,

    /**
     * The player does not have enough gold to complete the purchase.
     */
    NOT_ENOUGH_GOLD,

    /**
     * Maximum number of purchases reached for this shop session.
     */
    LIMIT_REACHED,

    /**
     * Chosen item has already been bought in this shop session.
     */
    ALREADY_BOUGHT,

    /**
     * Chosen item is not sold by this shop.
     */
    ITEM_NOT_SOLD
}
