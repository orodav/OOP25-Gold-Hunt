package it.unibo.goldhunt.view.viewstate;

import it.unibo.goldhunt.engine.api.Position;

/**
 * Immutable snapshot describing how a single board cell should be displayed in the UI.
 */
public record CellViewState(
    Position pos,
    boolean revealed,
    boolean flagged,
    int adjacentTraps,
    String glyph,
    String styleKey
) { }
