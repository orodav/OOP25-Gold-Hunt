package it.unibo.goldhunt.root.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import it.unibo.goldhunt.configuration.api.Difficulty;
import it.unibo.goldhunt.root.GameFactory;
import it.unibo.goldhunt.root.GameSession;

class GameFactoryTest {

    @Test
    void newGameShouldCreateNonNullSessionAndSubsystems() {
        final GameFactory factory = new GameFactory();
        final GameSession session = factory.newGame(Difficulty.EASY);
        assertNotNull(session);
        assertNotNull(session.level());
        assertNotNull(session.engine());
        assertNotNull(session.shop());
        assertTrue(session.shop().isEmpty());
    }

    @Test
    void newGameShouldExposeReadableState() {
        final GameFactory factory = new GameFactory();
        final GameSession session = factory.newGame(Difficulty.EASY);
        assertNotNull(session.engine().state());
        assertNotNull(session.engine().state().board());
        assertNotNull(session.engine().state().player());
        assertNotNull(session.engine().state().status());
        assertTrue(session.engine().state().board().boardSize() > 0);
    }

    @Test
    void newGameShouldThrowIfDifficultyNull() {
        final GameFactory factory = new GameFactory();
        assertThrows(NullPointerException.class, 
            () -> factory.newGame(null)
        );
    }
}
