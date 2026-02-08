package it.unibo.goldhunt.engine.api;

//davv
public interface Status {

    /** 
     * This interface represents the description of the game status.
     */

    LevelState levelState();

    GameMode gameMode();

    int levelNumber();

    Status withLevelState(LevelState newState);

    Status withGameMode(GameMode newMode);

    Status withLevelNumber(int newLevel);
}
