package it.unibo.goldhunt.view.api;

import java.util.Optional;

import it.unibo.goldhunt.engine.api.GameState;
import it.unibo.goldhunt.shop.api.Shop;
import it.unibo.goldhunt.view.viewmodel.GameViewState;

/**
 * Maps the current GameState and optional Shop into an immutable GameViewState for rendering.
 */
public interface GamePresenter {

    GameViewState map(GameState state, Optional<Shop> shop, String message);
}
