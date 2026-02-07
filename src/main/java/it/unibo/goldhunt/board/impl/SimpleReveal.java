// AZZU

package it.unibo.goldhunt.board.impl;

import java.util.Objects;

import it.unibo.goldhunt.board.api.Board;
import it.unibo.goldhunt.board.api.RevealStrategy;
import it.unibo.goldhunt.engine.api.Position;

/**
 * This class implements RevealStrategy.
 * With this reveal strategy, cells are only revealed one at a time.
 */
public final class SimpleReveal implements RevealStrategy {

    /**
     * {@inheritDoc}
     */
    @Override
    public void reveal(final Board b, final Position p) {
        Objects.requireNonNull(b);
        b.getCell(p).reveal();
    }

}
