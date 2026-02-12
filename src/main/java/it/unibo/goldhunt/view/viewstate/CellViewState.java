package it.unibo.goldhunt.view.viewstate;

import it.unibo.goldhunt.engine.api.Position;

/**
 * Immutable snapshot describing how a single board cell should be displayed in the UI.
 * 
 * @param pos the cell position
 * @param revealed whether the cell is currently revealed
 * @param flagged whether the cell is currently flagged
 * @param adjacentTraps the number of adjacent traps
 * @param symbol a short symbol to show in the cell
 * @param styleKey a UI style key for theming
 */
public record CellViewState(
    Position pos,
    boolean revealed,
    boolean flagged,
    int adjacentTraps,
    String symbol,
    String styleKey
) { }
