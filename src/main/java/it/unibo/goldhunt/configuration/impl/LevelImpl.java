//SARA
package it.unibo.goldhunt.configuration.impl;

import it.unibo.goldhunt.board.api.Board;
import it.unibo.goldhunt.configuration.api.BoardGenerator;
import it.unibo.goldhunt.configuration.api.Level;
import it.unibo.goldhunt.configuration.api.LevelConfig;
import it.unibo.goldhunt.engine.api.Position;
import it.unibo.goldhunt.player.api.PlayerOperations;

/**
 * This class is the implementation of Level.
 */
public class LevelImpl implements Level {

    private static final Position START = new Position(0, 0);
    private static final int INITIAL_LIVES = 3;

    private final LevelConfig config;
    private final BoardGenerator boardGenerator;
        
    private PlayerOperations player;
    private Board board;
    private Position exit;

    /**
     * Creates a new instance of LevelImpl.
     * @param config the level configuration
     * @param boardGenerator the board generator
     * @param player the player
     */
    public LevelImpl(LevelConfig config, BoardGenerator boardGenerator, PlayerOperations player) {
        this.config = config;
        this.boardGenerator = boardGenerator;
        this.player = player;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initBoard() {
        
        final int size = config.getBoardSize();
        this.exit = new Position(size - 1, size - 1);
        this.board = boardGenerator.generate(config, START, exit);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initPlayerPosition() {
        this.player = player.moveTo(START);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initLives() {
        this.player = player.addLives(INITIAL_LIVES);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Board getBoard() {
        return board;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Position getStart() {
        return START;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Position getExit() {
        return exit;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PlayerOperations getPlayer() {
        return player;
    }
}
