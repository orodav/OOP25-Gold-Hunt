package it.unibo.goldhunt.view.api;

import it.unibo.goldhunt.configuration.api.Difficulty;
import it.unibo.goldhunt.root.GameSession;

/**
 * Builds new game sessions for the GUI layer.
 */
public interface SessionFactory {

    /**
     * Creates a new game session for the given difficulty.
     * 
     * @param difficulty the difficulty
     * @return the new session
     * @throws NullPointerException if {@code difficulty} is null
     */
    GameSession newSession(Difficulty difficulty);

    /**
     * Resets the current run/session
     */
}
