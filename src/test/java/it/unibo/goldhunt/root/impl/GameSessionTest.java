package it.unibo.goldhunt.root.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import it.unibo.goldhunt.configuration.api.Difficulty;
import it.unibo.goldhunt.engine.api.ActionResult;
import it.unibo.goldhunt.engine.api.Position;
import it.unibo.goldhunt.root.GameFactory;
import it.unibo.goldhunt.root.GameSession;

class GameSessionTest {

    @Test
    void moveShouldReturnAnActionResult() {
        final GameSession session = new GameFactory().newGame(Difficulty.EASY);
        final ActionResult result = session.move(new Position(0, 0));
        assertNotNull(result);
    }

    @Test
    void revealAndToggleFlagShouldReturnActionResult() {
        final GameSession session = new GameFactory().newGame(Difficulty.EASY);
        final ActionResult reveal = session.reveal(new Position(0, 0));
        assertNotNull(reveal);
        final ActionResult flag = session.toggleFlag(new Position(0, 1));
        assertNotNull(flag);
    }
}
