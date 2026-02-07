package it.unibo.goldhunt.configuration.impl;

import it.unibo.goldhunt.board.api.Board;
import it.unibo.goldhunt.configuration.api.BoardGenerator;
import it.unibo.goldhunt.configuration.api.Level;
import it.unibo.goldhunt.configuration.api.LevelConfig;
import it.unibo.goldhunt.engine.api.Position;
import it.unibo.goldhunt.player.api.PlayerOperations;

public class LevelImpl implements Level {

    private static final Position START = new Position(0, 0);
    private static final int INITIAL_LIVES = 3;

    private final LevelConfig config;
    private final BoardGenerator boardGenerator;
        
    private PlayerOperations player;
    private Board board;
    private Position exit;

    public LevelImpl(LevelConfig config, BoardGenerator boardGenerator, PlayerOperations player) {
        this.config = config;
        this.boardGenerator = boardGenerator;
        this.player = player;
    }

    @Override
    public void initBoard() {
        
        final int size = config.getBoardSize();
        this.exit = new Position(size - 1, size - 1);
        this.board = boardGenerator.generate(config, START, exit);
    }

    @Override
    public void initPlayerPosition() {
        this.player = player.moveTo(START);
    }

    @Override
    public void initLives() {
        this.player = player.addLives(INITIAL_LIVES);
    }

    @Override
    public Board getBoard() {
        return board;
    }

    @Override
    public Position getStart() {
        return START;
    }

    @Override
    public Position getExit() {
        return exit;
    }

    @Override
    public PlayerOperations getPlayer() {
        return player;
    }
}
