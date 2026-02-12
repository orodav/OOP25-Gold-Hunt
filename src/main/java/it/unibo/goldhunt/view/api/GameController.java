package it.unibo.goldhunt.view.api;

import it.unibo.goldhunt.view.viewstate.GameViewState;

/**
 * Acts as the UI-facing controller.
 * Handles {@link GuiCommand}s and publish immutable {@link GameViewState} snapshots.
 */
public interface GameController {

    /**
     * Returns the most recent immutable snapshot of the UI state.
     * 
     * @return the corrent GameViewState
     */
    GameViewState state();

    /**
     * Handles a GUI command and returns the updated UI state snapshot.
     * @param command
     * @return
     */
    GameViewState handle(GuiCommand command);
}
