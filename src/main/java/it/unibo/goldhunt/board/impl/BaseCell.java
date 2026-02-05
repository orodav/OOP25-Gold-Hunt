// AZZU

package it.unibo.goldhunt.board.impl;

import java.util.Optional;

import it.unibo.goldhunt.board.api.Cell;
import it.unibo.goldhunt.items.api.CellContent;

public final class BaseCell implements Cell {

    private static final int NO_ADJACENT_TRAPS = 0;
    private static final int MAX_ADJACENT_TRAPS = 8;

    private boolean revealed;
    private boolean flagged;
    private int adjacentTraps;
    private Optional<CellContent> content;

    protected BaseCell() {
        this.revealed = false;
        this.flagged = false;
        this.adjacentTraps = NO_ADJACENT_TRAPS;
        this.content = Optional.empty();
    }

    protected BaseCell(final Optional<CellContent> content) {
        this();
        this.content = content;
    }

    public void reveal() {
        if (!isFlagged()) {
            this.revealed = true;
        }
    }

    public void toggleFlag() {
        if (!isRevealed()) {
            flagged = !flagged;
        }
    }

    public boolean isRevealed() {
        return this.revealed;
    }

    public boolean isFlagged() {
        return this.flagged;
    }

    public int getAdjacentTraps() {
        return this.adjacentTraps;
    }

    public void setAdjacentTraps(final int n) {
        if (n >= NO_ADJACENT_TRAPS && n <= MAX_ADJACENT_TRAPS) {
            this.adjacentTraps = n;
        }
        else {
            throw new IllegalArgumentException("The cell cannot have more than 8 or negative adjacent traps");
        }
    }

    public boolean hasContent() {
        return this.content.isPresent();
    }

    public Optional<CellContent> getContent() {
        return this.content;
    }

    public void setContent(final CellContent content) {
        if (this.content.isPresent()) {
            throw new IllegalStateException("The cell already has content");
        }
        this.content = Optional.of(content);
    }

    public void removeContent() {
        this.content = Optional.empty();
    }

    public String toString() {
        return "Cell[revealed=" + this.revealed
        + ", flagged=" + this.flagged
        + ", adjacentTraps=" + this.adjacentTraps
        + ",content=" + this.content.map(Object::toString).orElse("empty")
        + "]";
    }

}
