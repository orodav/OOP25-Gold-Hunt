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
    void shouldChangeLevelState() {
        final StatusImpl prevState = (StatusImpl) StatusImpl.createStartingState();
        final Status updated = prevState.withLevelState(LevelState.WON);
        assertEquals(LevelState.WON, updated.levelState());
        assertEquals(prevState.gameMode(), updated.gameMode());
        assertEquals(prevState.levelNumber(), updated.levelNumber());
    }

    @Test
    void shouldChangeGameMode() {
        final StatusImpl prevState = (StatusImpl) StatusImpl.createStartingState();
        final Status updated = prevState.withGameMode(GameMode.DIFFICULTY);
        assertEquals(GameMode.DIFFICULTY, updated.gameMode());
        assertEquals(prevState.levelState(), updated.levelState());
        assertEquals(prevState.levelNumber(), updated.levelNumber());
    }

    @Test
    void shouldChangeLevelNumber() {
        final StatusImpl prevState = (StatusImpl) StatusImpl.createStartingState();
        final Status updated = prevState.withLevelNumber(3);
        assertEquals(3, updated.levelNumber());
        assertEquals(prevState.levelState(), updated.levelState());
        assertEquals(prevState.gameMode(), updated.gameMode());
    }

    /*
    @Test
    void shouldReturnNewInstanceWhenChangingState() {
        final StatusImpl prevState = (StatusImpl) StatusImpl.createStartingState();
        final Status updated = prevStatwith(LevelState.LOSS);
        assertNotSame(prevState, updated);
        assertEquals(LevelState.PLAYING, prevState.levelState());
        assertEquals(LevelState.LOSS, updated.levelState());
        assertEquals(1, prevState.levelNumber());
    } */

    /*@Test
    void shouldChainUpdatesCorrectly() {
        final StatusImpl prevState = (StatusImpl) StatusImpl.createStartingState();
        final Status nextState = prevStatwith(LevelState.LOSS);
        final Status nextMode = ((StatusImpl) nextState).withGameMode(GameMode.LEVEL);
        final Status updated = ((StatusImpl) nextModewith(3);
        assertEquals(LevelState.LOSS, updated.levelState());
        assertEquals(GameMode.LEVEL, updated.gameMode());
        assertEquals(3, updated.levelNumber());
        assertEquals(LevelState.PLAYING, prevState.levelState());
        assertEquals(GameMode.LEVEL, prevState.gameMode());
        assertEquals(1, prevState.levelNumber());
    } */

    /* test per eventuali equals
    @Test
    void shouldBeEqualWhenSameState() {}

    @Test
    void shouldNoteBeEqualWhenDifferentState() {}
    */
}
