// AZZU

package it.unibo.goldhunt.board.impl;

import org.junit.jupiter.api.BeforeEach;

import it.unibo.goldhunt.board.api.CellFactory;

/**
 * This class tests {@link SquareBoardFactory}.
 */
public class SquareBoardFactoryTest {

    private CellFactory cellFactory;
    private SquareBoardFactory boardFactory;

    @BeforeEach
    void init() {
        this.cellFactory = new BaseCellFactory();
        this.boardFactory = new SquareBoardFactory(this.cellFactory);
    }
}
