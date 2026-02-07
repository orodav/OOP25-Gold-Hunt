// AZZU

package it.unibo.goldhunt.board.impl;

import it.unibo.goldhunt.items.api.CellContent;

/**
 * Support class for BaseCellFactoryTest and BaseCellTest.
 */
public final class TempCellContent implements CellContent {

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean applyEffect() {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String shortString() {
        return "For testing only";
    }

}
