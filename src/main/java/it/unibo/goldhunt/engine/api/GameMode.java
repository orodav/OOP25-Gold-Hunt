package it.unibo.goldhunt.engine.api;

/**
 * Represents the current operational mode of the game.
 * 
 * A {@code GameMode} defines the high-level context in which
 * the engine in operating. It determines which actions are
 * available and how the game flow is structured.
 */
public enum GameMode {

    /**
     * The game is in difficulty-selection mode.
     * 
     * In this mode, the player typically chooses the overall
     * difficulty level before starting the actual gameplay.
     */
    DIFFICULTY,
    
    /**
     * The game is in an active level.
     * 
     * In this mode, the player can perform standard gameplay
     * actions such as move, reveal, or flag.
     */
    LEVEL,
    
    /**
     * The game is currently in the shop.
     * 
     * In this mode, gameplay actions are usually restricted
     * and the player can purchase items or leave the shop.
     */
    SHOP;
}
