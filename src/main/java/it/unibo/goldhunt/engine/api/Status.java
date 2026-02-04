package main.java.it.unibo.goldhunt.engine.api;

//davv
public interface Status {

    /** This interface represents the description of the game status.
     * It describes in what game phase the player is, at what level
     * the player is and the game difficulty chosen.
     */

    LevelState levelState();

    GameMode gameMode();

    int levelNumber();
}
