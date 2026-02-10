// AZZU

package it.unibo.goldhunt.board.impl;

import java.util.Objects;
import java.util.Optional;

import it.unibo.goldhunt.board.api.Cell;
import it.unibo.goldhunt.board.api.ReadOnlyCell;
import it.unibo.goldhunt.items.api.CellContent;

/**
 * This class implements {@link ReadOnlyCell}.
 */
public final class ReadOnlyCellAdapter implements ReadOnlyCell {

    private final Cell cell;

    /**
     * {@code ReadOnlyCell}'s constructor.
     * It creates a read-only view of the given {@link Cell}.
     * 
     * @param cell the given cell
     * @throws NullPointerException if {@code cell} is {@code null}.
     */
    public ReadOnlyCellAdapter(final Cell cell) {
        Objects.requireNonNull(cell);
        this.cell = cell;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isRevealed() {
        return cell.isRevealed();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isFlagged() {
        return cell.isFlagged();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getAdjacentTraps() {
        return cell.getAdjacentTraps();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<String> contentID() {
        if (!cell.isRevealed()) {
            return Optional.empty();
        }
        return cell.getContent().map(CellContent::shortString);
    }

}
