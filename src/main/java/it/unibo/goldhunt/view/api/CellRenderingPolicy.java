package it.unibo.goldhunt.view.api;

import it.unibo.goldhunt.board.api.ReadOnlyCell;

/**
 * Defines the rendering strategy used to determine how a cell should appear in the UI.
 */
public interface CellRenderingPolicy {

    RenderCell render(ReadOnlyCell cell);

    record RenderCell(String glyph, String styleKey) {}
}
