package it.unibo.goldhunt.board.impl;

import java.util.Optional;

import it.unibo.goldhunt.board.api.Cell;
import it.unibo.goldhunt.board.api.CellFactory;
import it.unibo.goldhunt.items.api.CellContent;

/**
 * This class is the implementation of {@link CellFactory}.
 */
public final class BaseCellFactory implements CellFactory {

    /**
     * {@inheritDoc}
     */
    @Override
    public Cell createCell() {
        return new BaseCell();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Cell createCell(final Optional<CellContent> content) {
        return new BaseCell(content);
    }

}
