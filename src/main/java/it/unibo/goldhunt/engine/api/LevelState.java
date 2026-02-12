package it.unibo.goldhunt.engine.api;

/**
 * Represents the current outcome state of a level.
 * 
 * <p>
 * A {@code LevelState} describes whether the player is still playing the
 * level or if a terminal condition has been reached.
 */
public enum LevelState {
    /**
     * The level is currently active.
     * The player can continue performing actions.
     */
    PLAYING,
    /**
     * The player has successfully completed the level.
     */
    WON,
    /**
     * The player has failed the level due to reaching a losing condition.
     */
    LOSS
}
