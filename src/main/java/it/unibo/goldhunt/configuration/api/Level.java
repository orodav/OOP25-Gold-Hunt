//SARA
package it.unibo.goldhunt.configuration.api;

import it.unibo.goldhunt.board.api.Board;
import it.unibo.goldhunt.engine.api.Position;
import it.unibo.goldhunt.player.api.PlayerOperations;

/**
 * This interface models a game level, responsible for initializing the board,
 * the player position, and the initial player lives.
 */
public interface Level {

    /**
     * Initializes the game board according to the level configuration.
     * 
     * @throws IllegalStateException if board generation fails
     */
    public void initBoard();

    /**
     * Initializes the player's position to the starting cell.
     * 
     * @throws IllegalStateException if player repositioning fails
     */
    public void initPlayerPosition();

    /**
     * Initializes the player's lives.
     * 
     * @throws IllegalStateException if lives initialization fails
     */
    public void initLives();

    /**
     * Returns the current game board.
     * 
     * @return the current game {@link Board}
     */
    public Board getBoard();

    /**
     * Returns the start position.
     * 
     * @return the start {@link Position}
     */
    public Position getStart();

    /**
     * Returns the exit position.
     * 
     * @return the exit {@link Position}
     */
    public Position getExit();

    /**
     * Returns the player.
     * 
     * @return the {@link player}
     */
    public PlayerOperations getPlayer();
}



