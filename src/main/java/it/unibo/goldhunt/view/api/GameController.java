package it.unibo.goldhunt.view.api;

import it.unibo.goldhunt.view.viewstate.GameViewState;

/**
 * Acts as the UI-facing controller.
 * Handles GUI commands and publish view state updates.
 */
public interface GameController {

    /**
     * Returns the most recent immutable snapshot of the UI state.
     * 
     * @return the corrent GameViewState
     */
    GameViewState state();
}
