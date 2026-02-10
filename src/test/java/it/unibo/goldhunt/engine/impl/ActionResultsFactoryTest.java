package it.unibo.goldhunt.engine.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import it.unibo.goldhunt.engine.api.ActionEffect;
import it.unibo.goldhunt.engine.api.ActionType;
import it.unibo.goldhunt.engine.api.GameMode;
import it.unibo.goldhunt.engine.api.LevelState;
import it.unibo.goldhunt.engine.api.Status;
import it.unibo.goldhunt.engine.api.StopReason;

public class ActionResultsFactoryTest {

    private Status statusPlaying() {
        return new StatusImpl(
            LevelState.PLAYING, 
            GameMode.LEVEL, 
            1
        );
    }

    @Test
    void moveShouldBuildActionResultCorrectly() {
        final var status = statusPlaying();
        final var res = ActionResultsFactory.move(
            status,
            StopReason.REACHED_CELL,
            ActionEffect.APPLIED
        );
        assertEquals(ActionType.MOVE, res.type());
        assertEquals(StopReason.REACHED_CELL, res.reason());
        assertEquals(LevelState.PLAYING, res.levelState());
        assertEquals(ActionEffect.APPLIED, res.effect());
    }

    @Test
    void revealShouldBuildActionResultCorrectly() {
        final var status = statusPlaying();
        final var res = ActionResultsFactory.reveal(
            status, 
            ActionEffect.INVALID
        );
        assertEquals(ActionType.REVEAL, res.type());
        assertEquals(StopReason.NONE, res.reason());
        assertEquals(LevelState.PLAYING, res.levelState());
        assertEquals(ActionEffect.INVALID, res.effect());
    }

    @Test
    void flagShouldBuildActionResultCorrectly() {
        final var status = statusPlaying();
        final var res = ActionResultsFactory.flag(
            status, 
            ActionEffect.REMOVED
        );
        assertEquals(ActionType.FLAG, res.type());
        assertEquals(StopReason.NONE, res.reason());
        assertEquals(LevelState.PLAYING, res.levelState());
        assertEquals(ActionEffect.REMOVED, res.effect());
    }

    @Test
    void shouldUseStatusLevelState() {
        final Status status = new StatusImpl(
            LevelState.WON, 
            GameMode.LEVEL, 
            1
        );
        final var res = ActionResultsFactory.reveal(
            status, 
            ActionEffect.APPLIED
        );
        assertEquals(LevelState.WON, res.levelState());
    }

}
