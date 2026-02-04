package it.unibo.goldhunt.engine.impl;

import it.unibo.goldhunt.engine.api.GameMode;
import it.unibo.goldhunt.engine.api.LevelState;
import it.unibo.goldhunt.engine.api.Status;

public final class StatusImpl implements Status{

    private final LevelState levelState;
    private final GameMode gameMode;
    private final int levelNumber;

    public StatusImpl(
        final LevelState levelState,
        final GameMode gameMode,
        final int levelNumber
    ) {
        this.levelState = levelState;
        this.gameMode = gameMode;
        this.levelNumber = levelNumber;
    }

    @Override
    public LevelState levelState() {
        return this.levelState;
    }

    public Status getLevelState(final LevelState newState) {
        return new StatusImpl(newState, this.gameMode, this.levelNumber);
    }

    @Override
    public GameMode gameMode() {
        return this.gameMode;
    }

    public Status getGameMode(final GameMode newMode) {
        return new StatusImpl(this.levelState, newMode, this.levelNumber);
    }

    @Override
    public int levelNumber() {
        return this.levelNumber;
    }

    public Status getLevelNumber(final int newLevel) {
        return new StatusImpl(this.levelState, this.gameMode, newLevel);
    }

    public static Status createStartingState() {
        return new StatusImpl(LevelState.PLAYING, GameMode.LEVEL, 1);
    }
}
