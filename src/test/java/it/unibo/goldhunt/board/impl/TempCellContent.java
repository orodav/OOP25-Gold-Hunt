package it.unibo.goldhunt.board.impl;

import it.unibo.goldhunt.items.api.CellContent;
import it.unibo.goldhunt.player.api.PlayerOperations;

/**
 * Support class for {@link BaseCellFactoryTest} and {@link BaseCellTest}.
 */
final class TempCellContent implements CellContent {

    /**
     * {@inheritDoc}
     */
    @Override
    public PlayerOperations applyEffect(final PlayerOperations playerop) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String shortString() {
        return "For testing only";
    }

}
