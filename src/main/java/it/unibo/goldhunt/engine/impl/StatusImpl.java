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
        if (levelState == null || gameMode == null) {
            throw new IllegalArgumentException("dependencies can't be null");
        }
        if (levelNumber < 0) {
            throw new IllegalArgumentException("levelNumber can't be negative");
        }
        this.levelState = levelState;
        this.gameMode = gameMode;
        this.levelNumber = levelNumber;
    }

    @Override
    public LevelState levelState() {
        return this.levelState;
    }

    public Status withLevelState(final LevelState newState) {
        if (newState == null) {
            throw new IllegalArgumentException("state can't be null");
        }
        return new StatusImpl(newState, this.gameMode, this.levelNumber);
    }

    @Override
    public GameMode gameMode() {
        return this.gameMode;
    }

    public Status withGameMode(final GameMode newMode) {
        if (newMode == null) {
            throw new IllegalArgumentException("mode can't be null");
        }
        return new StatusImpl(this.levelState, newMode, this.levelNumber);
    }

    @Override
    public int levelNumber() {
        return this.levelNumber;
    }

    public Status withLevelNumber(final int newLevel) {
        if (newLevel < 0) {
            throw new IllegalArgumentException("level can't be < 0");
        }
        return new StatusImpl(this.levelState, this.gameMode, newLevel);
    }

    public static Status createStartingState() {
        return new StatusImpl(LevelState.PLAYING, GameMode.LEVEL, 1);
    }
}
