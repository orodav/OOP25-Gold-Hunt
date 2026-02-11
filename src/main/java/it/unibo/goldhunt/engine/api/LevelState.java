package it.unibo.goldhunt.engine.api;

/**
 * Represents the current outcome state of a level.
 * 
 * A {@code LevelState} describes whether the player is still playing the
 * level or if a terminal condition has been reached.
 */
public enum LevelState {

    /**
     * The level is currently active and the player can continue playing.
     */
    PLAYING,
    
    /**
     * The player has successfully completed the level.
     */
    WON,
    
    /**
     * The player has failed the level due to reaching a losing condition.
     */
    LOSS;
}
