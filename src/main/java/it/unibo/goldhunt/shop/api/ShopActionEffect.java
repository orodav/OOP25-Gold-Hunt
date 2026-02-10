package it.unibo.goldhunt.shop.api;

/**
 * Describes the effect of a shop action.
 */
public enum ShopActionEffect {

    /**
     * The action has been applied successfully.
     */
    APPLIED,

    /**
     * The action has been blocked by shop rules.
     */
    BLOCKED
}
