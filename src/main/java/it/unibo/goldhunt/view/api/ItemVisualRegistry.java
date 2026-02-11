package it.unibo.goldhunt.view.api;

import java.awt.Color;

public interface ItemVisualRegistry {

    String getGlyph(String getContentID);

    Color getColor(String contentID);

    String getItemDescription(String contentID);

}
