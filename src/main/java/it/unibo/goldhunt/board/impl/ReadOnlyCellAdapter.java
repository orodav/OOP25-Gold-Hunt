// AZZU

package it.unibo.goldhunt.board.impl;

import java.util.Objects;
import java.util.Optional;

import it.unibo.goldhunt.board.api.Cell;
import it.unibo.goldhunt.board.api.ReadOnlyCell;
import it.unibo.goldhunt.items.api.CellContent;

public class ReadOnlyCellAdapter implements ReadOnlyCell {

    private final Cell cell;

    public ReadOnlyCellAdapter(final Cell cell) {
        Objects.requireNonNull(cell);
        this.cell = cell;
    }

    @Override
    public boolean isRevealed() {
        return cell.isRevealed();
    }

    @Override
    public boolean isFlagged() {
        return cell.isFlagged();
    }

    @Override
    public int adjacentTraps() {
        return cell.getAdjacentTraps();
    }

    @Override
    public Optional<String> contentID() {
        if (!cell.isRevealed()) {
            return Optional.empty();
        }
        return cell.getContent().map(CellContent::shortString);
    }


}
