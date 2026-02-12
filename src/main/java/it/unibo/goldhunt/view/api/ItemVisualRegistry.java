package it.unibo.goldhunt.view.api;

import javax.swing.Icon;

public interface ItemVisualRegistry {

    String getGlyph(final String getContentID);

    String getTooltip(final String contentID);

    String getStyleKey(final String contentID);

    Icon getIcon(String id);
}
