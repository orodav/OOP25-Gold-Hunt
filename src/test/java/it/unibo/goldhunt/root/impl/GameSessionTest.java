package it.unibo.goldhunt.root.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import it.unibo.goldhunt.configuration.api.Difficulty;
import it.unibo.goldhunt.engine.api.ActionResult;
import it.unibo.goldhunt.engine.api.ActionType;
import it.unibo.goldhunt.engine.api.Position;
import it.unibo.goldhunt.root.GameFactory;
import it.unibo.goldhunt.root.GameSession;

class GameSessionTest {

    @Test
    void moveShouldReturnAnActionResultWithMove() {
        final GameSession session = new GameFactory().newGame(Difficulty.EASY);
        final ActionResult result = session.move(new Position(0, 0));
        assertNotNull(result);
        assertEquals(ActionType.MOVE, result.type());
    }

    @Test
    void moveShouldReturnAnActionResultWithReveal() {
        final GameSession session = new GameFactory().newGame(Difficulty.EASY);
        final ActionResult result = session.reveal(new Position(0, 0));
        assertNotNull(result);
        assertEquals(ActionType.REVEAL, result.type());
    }

    @Test
    void toggleFlagShouldReturnActionResultWithFlag() {
        final GameSession session = new GameFactory().newGame(Difficulty.EASY);
        final ActionResult flag = session.toggleFlag(new Position(0, 0));
        assertNotNull(flag);
        assertEquals(ActionType.FLAG, flag.type());
    }
}
