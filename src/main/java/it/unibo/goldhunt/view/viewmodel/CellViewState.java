package it.unibo.goldhunt.view.viewmodel;

import it.unibo.goldhunt.engine.api.Position;

/**
 * Immutable shanpshot describing how a single board cell should be
 * displayed in the UI.
 */
public record CellViewState(
    Position pos,
    boolean revealed,
    boolean flagged,
    int adjactentTraps,
    String glyph,
    String styleKey
) { }
