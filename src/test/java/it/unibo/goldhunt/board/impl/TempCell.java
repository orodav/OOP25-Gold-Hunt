package it.unibo.goldhunt.board.impl;

import java.util.Optional;

import it.unibo.goldhunt.board.api.Cell;
import it.unibo.goldhunt.items.api.CellContent;

public class TempCell implements Cell {

    private static final int NO_ADJACENT_TRAPS = 0;
    private static final int MAX_ADJACENT_TRAPS = 8;

    private boolean revealed;
    private boolean flagged;
    private int adjacentTraps;
    private Optional<CellContent> content;

    public TempCell() {
        this.revealed = false;
        this.flagged = false;
        this.adjacentTraps = NO_ADJACENT_TRAPS;
        this.content = Optional.empty();
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
    public boolean isRevealed() {
        return this.revealed;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void toggleFlag() {
        if (!isRevealed()) {
            this.flagged = !flagged;
        }
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
    public void setAdjacentTraps(int n) {
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
    public void setContent(CellContent content) {
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

}
