package it.unibo.goldhunt.view.impl;

import java.util.Optional;

import it.unibo.goldhunt.engine.api.ActionResult;
import it.unibo.goldhunt.engine.api.Position;
import it.unibo.goldhunt.items.api.ItemTypes;
import it.unibo.goldhunt.root.GameSession;
import it.unibo.goldhunt.view.viewstate.GameViewState;

/**
 * Maps the model/session state to immutable UI view states.
 */
public interface ViewStateMapper {

    /**
     * Builds an immutable {@link GameViewState} snapshot from the current session.
     * 
     * @param session the current game session
     * @param message an optional UI meggase to attach to the snapshot
     * @return the snapshot
     */
    GameViewState fromSession(GameSession session, Optional<String> message);

    /**
     * Extracts an optional UI message from an {@link ActionResult}.
     * 
     * @param result the result to inspect
     * @return an optional message
     */
    Optional<String> messageFromActionResult(ActionResult result);

    /**
     * Executes a shop purchase and returns a message describing the outcome.
     * 
     * @param session the current session
     * @param type the item type to buy
     * @return an optional message
     */
    Optional<String> handleBuy(GameSession session, ItemTypes type);

    /**
     * Executes an item usage and returns a message describing the outcome.
     * 
     * @param session the current session
     * @param type the item type to use
     * @param target optional target position
     * @return an optional message
     */
    Optional<String> handleUseItem(GameSession session, ItemTypes type, Optional<Position> target);
}
