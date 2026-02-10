package it.unibo.goldhunt.engine.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import it.unibo.goldhunt.engine.api.GameMode;
import it.unibo.goldhunt.engine.api.LevelState;
import it.unibo.goldhunt.engine.api.Status;

public class StatusTest {

    @Test
    void shouldReturnCorrectInitialValues() {
        final Status status = StatusImpl.createStartingState();
        assertEquals(LevelState.PLAYING, status.levelState());
        assertEquals(GameMode.LEVEL, status.gameMode());
        assertEquals(1, status.levelNumber());
    }

    @Test
    void shouldThrowIfConstructorDependenciesNull() {
        assertThrows(IllegalArgumentException.class, 
            () -> new StatusImpl(null, GameMode.LEVEL, 1)
        );
        assertThrows(IllegalArgumentException.class,
            () -> new StatusImpl(LevelState.PLAYING, null, 1)
        );
    }

    @Test
    void shouldThrowIfConstructorLevelNegative() {
        assertThrows(IllegalArgumentException.class, 
            () -> new StatusImpl(LevelState.PLAYING, GameMode.LEVEL, -1)
        );
    }

    @Test
    void shouldChangeLevelState() {
        final StatusImpl prevState = new StatusImpl(
            LevelState.PLAYING,
            GameMode.LEVEL,
            1
        );
        final Status updated = prevState.withLevelState(LevelState.WON);
        assertEquals(LevelState.WON, updated.levelState());
        assertEquals(prevState.gameMode(), updated.gameMode());
        assertEquals(prevState.levelNumber(), updated.levelNumber());
        assertNotSame(prevState, updated);
        assertEquals(LevelState.PLAYING, prevState.levelState());
    }

    @Test
    void shouldChangeGameMode() {
        final StatusImpl prevState = new StatusImpl(
            LevelState.PLAYING, 
            GameMode.LEVEL, 
            1
        );
        final Status updated = prevState.withGameMode(GameMode.DIFFICULTY);
        assertEquals(GameMode.DIFFICULTY, updated.gameMode());
        assertEquals(prevState.levelState(), updated.levelState());
        assertEquals(prevState.levelNumber(), updated.levelNumber());
        assertNotSame(prevState, updated);
        assertEquals(GameMode.LEVEL, prevState.gameMode());
    }

    @Test
    void shouldChangeLevelNumber() {
        final StatusImpl prevState = new StatusImpl(
            LevelState.PLAYING, 
            GameMode.LEVEL, 
            1
        );
        final Status updated = prevState.withLevelNumber(3);
        assertEquals(3, updated.levelNumber());
        assertEquals(prevState.levelState(), updated.levelState());
        assertEquals(prevState.gameMode(), updated.gameMode());
        assertNotSame(prevState, updated);
        assertEquals(1, prevState.levelNumber());
    }

    @Test
    void withLevelStateShouldThrowIfNull() {
        final StatusImpl status = new StatusImpl(
            LevelState.PLAYING, 
            GameMode.LEVEL, 
            1
        );
        assertThrows(IllegalArgumentException.class, 
            () -> status.withLevelState(null)
        );
    }

    @Test
    void withGameModeShouldThrowIfNull() {
        final StatusImpl status = new StatusImpl(
            LevelState.PLAYING, 
            GameMode.LEVEL, 
            1
        );
        assertThrows(IllegalArgumentException.class, 
            () -> status.withGameMode(null)
        );
    }

    @Test
    void withLevelNumberShouldThrowIfNegative() {
        final StatusImpl status = new StatusImpl(
            LevelState.PLAYING, 
            GameMode.LEVEL, 
            1
        );
        assertThrows(IllegalArgumentException.class, 
            () -> status.withLevelNumber(-1)
        );
    }

    @Test
    void shouldChainUpdates() {
        final StatusImpl prevStatus = new StatusImpl(
            LevelState.PLAYING, 
            GameMode.LEVEL, 
            1
        );
        final Status updatedStatus = prevStatus
            .withGameMode(GameMode.DIFFICULTY)
            .withLevelNumber(3)
            .withLevelState(LevelState.WON);
        assertEquals(LevelState.WON, updatedStatus.levelState());
        assertEquals(GameMode.DIFFICULTY, updatedStatus.gameMode());
        assertEquals(3, updatedStatus.levelNumber());
        assertEquals(LevelState.PLAYING, prevStatus.levelState());
        assertEquals(GameMode.LEVEL, prevStatus.gameMode());
        assertEquals(1, prevStatus.levelNumber());
    }
}
