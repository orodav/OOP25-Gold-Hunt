package it.unibo.goldhunt.view.api;

import it.unibo.goldhunt.board.api.ReadOnlyCell;

/**
 * Defines how a ReadOnlyCell is converted into UI-ready rendering data.
 */
public interface CellRenderer {

    RenderedCell render(ReadOnlyCell cell);

    record RenderedCell(String glyph, String styleKey) { }
}
