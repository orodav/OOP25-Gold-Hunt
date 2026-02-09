package it.unibo.goldhunt.engine.api;

/**
 * Extended Engine API providing a read-only snapshot of the game.
 */
public interface EngineWithState extends Engine {

    GameState state();
}
