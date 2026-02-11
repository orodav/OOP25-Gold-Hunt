package it.unibo.goldhunt.engine.api;

/**
 * Represents the effect produces by the execution of a game action.
 * 
 * <p>
 * An {@code ActionEffect} describes the outcome of an action in terms of
 * its impact on the game state, independently from the reason why the 
 * action stopped or the overall level state.
 */
public enum ActionEffect {
    /**
     * The action has been successfully executed and its effect
     * has been applied to the game state.
     */
    APPLIED,
    /**
     * The action resulted in the removal of something.
     */
    REMOVED,
    /**
     * The action cloud not be executed because it was
     * prevented by the current game conditions.
     */
    BLOCKED,
    /**
     * The action was not valid.
     */
    INVALID,
    /**
     * The action produced no relevant effect.
     */
    NONE
}
