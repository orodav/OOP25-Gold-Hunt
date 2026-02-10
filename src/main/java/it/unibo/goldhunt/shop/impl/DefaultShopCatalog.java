package it.unibo.goldhunt.shop.impl;

import java.util.List;

import it.unibo.goldhunt.items.api.KindOfItem;
import it.unibo.goldhunt.shop.api.ShopItem;

public final class DefaultShopCatalog {

    private final static int SHIELD_PRICE = 7;
    private final static int PICKAXE_PRICE = 12;
    private final static int DYNAMITE_PRICE = 9;
    private final static int CHART_PRICE = 4;
    
    private DefaultShopCatalog() { }

    public static List<ShopItem> create() {
        return List.of(
            new ShopItem(KindOfItem.SHIELD, SHIELD_PRICE),
            new ShopItem(KindOfItem.PICKAXE, PICKAXE_PRICE),
            new ShopItem(KindOfItem.DYNAMITE, DYNAMITE_PRICE),
            new ShopItem(KindOfItem.CHART, CHART_PRICE)
        );
    }
}
