package it.unibo.goldhunt.engine.impl;

import it.unibo.goldhunt.engine.api.GameMode;
import it.unibo.goldhunt.engine.api.LevelState;
import it.unibo.goldhunt.engine.api.Status;

/**
 * Immutable implementation of {@link Status}.
 * 
 * <p>
 * Encapsulates the curret level state, game mode, and level number.
 * All modification methods return a new {@code StatusImpl} instance.
 */
public final class StatusImpl implements Status {

    private final LevelState levelState;
    private final GameMode gameMode;
    private final int levelNumber;

    /**
     * Creates a new status instance.
     * 
     * @param levelState the current level state
     * @param gameMode the current game mode
     * @param levelNumber the current level number
     * @throws IllegalArgumentException if {@code levelState} or {@code gameMode}
     *                                  is {@code null}, or if 
     *                                  {@code levelNumber} is negative
     */
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

    /**
     * {@inheritDoc}
     */
    @Override
    public LevelState levelState() {
        return this.levelState;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Status withLevelState(final LevelState newState) {
        if (newState == null) {
            throw new IllegalArgumentException("state can't be null");
        }
        return new StatusImpl(newState, this.gameMode, this.levelNumber);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameMode gameMode() {
        return this.gameMode;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Status withGameMode(final GameMode newMode) {
        if (newMode == null) {
            throw new IllegalArgumentException("mode can't be null");
        }
        return new StatusImpl(this.levelState, newMode, this.levelNumber);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int levelNumber() {
        return this.levelNumber;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Status withLevelNumber(final int newLevel) {
        if (newLevel < 0) {
            throw new IllegalArgumentException("level can't be < 0");
        }
        return new StatusImpl(this.levelState, this.gameMode, newLevel);
    }

    /**
     * Creates the default starting status.
     * 
     * @return the initial status instance
     */
    public static Status createStartingState() {
        return new StatusImpl(LevelState.PLAYING, GameMode.LEVEL, 1);
    }
}
