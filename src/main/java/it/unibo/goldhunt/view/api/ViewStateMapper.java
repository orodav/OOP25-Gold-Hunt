package it.unibo.goldhunt.view.api;

import java.util.Optional;

import it.unibo.goldhunt.engine.api.ActionResult;
import it.unibo.goldhunt.root.GameSession;
import it.unibo.goldhunt.shop.api.ShopActionResult;
import it.unibo.goldhunt.view.viewstate.GameViewState;
import it.unibo.goldhunt.view.viewstate.ScreenType;

/**
 * Default implementation of {@link ViewStateMapper}.
 * 
 * <p>
 * This class is responsible for translating the current
 * {@link GameSession} state and action results into immutable
 * {@link GameViewState} instances consumable by the UI layer.
 */
public interface ViewStateMapper {

    /**
     * Builds an immutable {@link GameViewState} snapshot from the current session.
     * 
     * @param session the current game session
     * @param message an optional UI meggase to attach to the snapshot
     * @param screen the phase of the UI
     * @return the snapshot
     */
    GameViewState fromSession(GameSession session, Optional<String> message, ScreenType screen);

    /**
     * Extracts an optional UI message from an {@link ActionResult}.
     * 
     * @param result the result to inspect
     * @return an optional message
     */
    Optional<String> messageFromActionResult(ActionResult result);

    /**
     * Extracts an optional UI message from a {@link ShopActionResult}.
     * 
     * @param result the shop action result to inspect
     * @return an optional message describing the outcome
     */
    Optional<String> messageFromShopActionResult(ShopActionResult result);
}
