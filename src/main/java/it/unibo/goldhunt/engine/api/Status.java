package it.unibo.goldhunt.engine.api;

/**
 * Represents the aggregated status of the current game.
 * 
 * <p>
 * A {@link Status} encapsulates high-level information about the game,
 * such as {@link LevelState}, {@link GameMode} and the level number.
 * 
 * <p>
 * Implementations are expected to be immutable.
 */
public interface Status {

    /**
     * Returns the current state of the level.
     * 
     * @return the {@link LevelState}
     */
    LevelState levelState();

    /**
     * Returns the current game mode.
     * 
     * @return the {@link GameMode}
     */
    GameMode gameMode();

    /**
     * Returns the current level number.
     * 
     * @return the level index
     */
    int levelNumber();

    /**
     * Returns a new {@code Status} with the specified level state.
     * 
     * @param newState the new level state
     * @return a new {@code Status} reflecting the updated level state
     */
    Status withLevelState(LevelState newState);

    /**
     * Returns a new {@code Status} with the specified game mode.
     * 
     * @param newMode the new game mode
     * @return a new {@code Status} reflecting the updated game mode
     */
    Status withGameMode(GameMode newMode);

    /**
     * Returns a new {@code Status} with the specified level number.
     * 
     * @param newLevel the new level number
     * @return a new {@code Status} reflecting the updated level number
     */
    Status withLevelNumber(int newLevel);
}
