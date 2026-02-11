package it.unibo.goldhunt.shop.impl;

import java.util.List;

import it.unibo.goldhunt.items.api.KindOfItem;
import it.unibo.goldhunt.shop.api.ShopItem;

/**
 * Provides the default shop catalog configuration.
 * 
 * This utility class defines the standard set of items
 * available in the shop and their prices.
 * 
 * The catalog is immutable and intended for use as a
 * default configuration in the {@code GameFactory}
 */
public final class DefaultShopCatalog {

    private final static int SHIELD_PRICE = 7;
    private final static int PICKAXE_PRICE = 12;
    private final static int DYNAMITE_PRICE = 9;
    private final static int CHART_PRICE = 4;
    
    private DefaultShopCatalog() { }

    /**
     * Creates the default list of {@link ShopItem} available for purchase.
     * 
     * @return an immutable list of shop items with predefined prices
     */
    public static List<ShopItem> create() {
        return List.of(
            new ShopItem(KindOfItem.SHIELD, SHIELD_PRICE),
            new ShopItem(KindOfItem.PICKAXE, PICKAXE_PRICE),
            new ShopItem(KindOfItem.DYNAMITE, DYNAMITE_PRICE),
            new ShopItem(KindOfItem.CHART, CHART_PRICE)
        );
    }
}
