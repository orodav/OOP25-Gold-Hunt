package it.unibo.goldhunt.engine.api;

import it.unibo.goldhunt.items.api.ItemTypes;
import it.unibo.goldhunt.shop.api.ShopActionResult;

/**
 * Extended version of {@link Engine} providing access to a read-only
 *  snapshot of the current game state.
 * 
 * <p>
 * This interface is typically used by components that need to
 * observe the complete game state without directly modifying it.
 */
public interface EngineWithState extends Engine {

    /**
     * Returns an immutable snapshot representing the current
     * overall state of the game.
     * 
     * @return the current {@link GameState}
     */
    GameState state();

    /**
     * Further extension of {@link EngineWithState} that enables
     * shop-related actions.
     * 
     * <p>
     * This interface should be used when the game mode includes 
     * an in-game shop, allowing the player to purchase items
     * and exit the shop context.
     */
    interface EngineWithShopActions extends EngineWithState {
        /**
         * Attempts to buy an item of the specified {@link ItemTypes}.
         * 
         * @param type the type of item to purchase
         * @return a {@link ShopActionResult} describing the outcome
         * @throws IllegalArgumentException if {@code type} is {@code null}
         */
        ShopActionResult buy(ItemTypes type);

        /**
         * Leaves the shop and returns to the main game context.
         * 
         * <p>
         * This method does not return a result because leaving
         * the shop is considered a state transition.
         */
        void leaveShop();
    }
}
