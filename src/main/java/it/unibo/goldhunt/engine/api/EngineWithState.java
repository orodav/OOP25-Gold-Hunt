package it.unibo.goldhunt.engine.api;

import it.unibo.goldhunt.items.api.ItemTypes;
import it.unibo.goldhunt.shop.api.ShopActionResult;

/**
 * Extended Engine API providing a read-only snapshot of the game.
 */
public interface EngineWithState extends Engine {

    GameState state();

    public interface EngineWithShopActions extends EngineWithState {
        
        ShopActionResult buy(ItemTypes type);
        void leaveShop();
    }
}
