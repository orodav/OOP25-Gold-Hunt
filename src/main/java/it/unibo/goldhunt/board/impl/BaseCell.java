// AZZU

package it.unibo.goldhunt.board.impl;

import java.util.Optional;

import it.unibo.goldhunt.board.api.Cell;
import it.unibo.goldhunt.items.api.CellContent;

/**
 * This class is the implementation of Cell.
 */
public final class BaseCell implements Cell {

    private static final int NO_ADJACENT_TRAPS = 0;
    private static final int MAX_ADJACENT_TRAPS = 8;

    private boolean revealed;
    private boolean flagged;
    private int adjacentTraps;
    private Optional<CellContent> content;

    /**
     * This constructor creates an empty BaseCell.
     */
    protected BaseCell() {
        this.revealed = false;
        this.flagged = false;
        this.adjacentTraps = NO_ADJACENT_TRAPS;
        this.content = Optional.empty();
    }

    /**
     * This constructor creates a BaseCell with content.
     * 
     * @param content the cell's object
     */
    protected BaseCell(final Optional<CellContent> content) {
        this();
        this.content = content;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void reveal() {
        if (!isFlagged()) {
            this.revealed = true;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void toggleFlag() {
        if (!isRevealed()) {
            flagged = !flagged;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isRevealed() {
        return this.revealed;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isFlagged() {
        return this.flagged;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getAdjacentTraps() {
        return this.adjacentTraps;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setAdjacentTraps(final int n) {
        if (n >= NO_ADJACENT_TRAPS && n <= MAX_ADJACENT_TRAPS) {
            this.adjacentTraps = n;
        } else {
            throw new IllegalArgumentException("The cell cannot have more than 8 or negative adjacent traps");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasContent() {
        return this.content.isPresent();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<CellContent> getContent() {
        return this.content;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setContent(final CellContent content) {
        if (this.content.isPresent()) {
            throw new IllegalStateException("The cell already has content");
        }
        this.content = Optional.of(content);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeContent() {
        this.content = Optional.empty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "Cell[revealed=" + this.revealed
        + ", flagged=" + this.flagged
        + ", adjacentTraps=" + this.adjacentTraps
        + ",content=" + this.content.map(Object::toString).orElse("empty")
        + "]";
    }

}
