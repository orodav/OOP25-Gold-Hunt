package it.unibo.goldhunt.engine.api;

/**
 * Represents the current progression state of a level.
 * 
 * A {@code LevelState} indicates whether the player is still
 * actively playing the level or if the level has reached
 * a terminal condition (victory or defeat)
 */
public enum LevelState {

    /**
     * The level is currently active.
     * The player can continue performing actions.
     */
    PLAYING,
    
    /**
     * The level has been successfully completed.
     * The player has achieved victory.
     */
    WON,
    
    /**
     * The level has ended in defeat.
     */
    LOSS;
}
