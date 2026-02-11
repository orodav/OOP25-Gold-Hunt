package it.unibo.goldhunt.view.api;

import java.util.Optional;

import it.unibo.goldhunt.engine.api.GameState;
import it.unibo.goldhunt.shop.api.Shop;
import it.unibo.goldhunt.view.viewmodel.GameViewState;

/**
 * Maps the current GameState and optional Shop into an immutable GameViewState for rendering.
 */
public interface GamePresenter {

    /**
     * Builds a {@link GameViewState} from the given game state.
     * 
     * @param state the current game state
     * @param shop the optional shop
     * @param message a UI message to show
     * @return an immutable snapshot for the GUI
     * @throws NullPointerException if {@code state}, {@code shop}
     *         or {@code message} is {@code null}
     */
    GameViewState map(GameState state, Optional<Shop> shop, String message);
}
