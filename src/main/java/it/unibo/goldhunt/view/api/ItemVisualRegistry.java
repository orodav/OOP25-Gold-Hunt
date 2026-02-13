package it.unibo.goldhunt.view.api;

import java.util.Set;

import javax.swing.Icon;

public interface ItemVisualRegistry {

    String getGlyph(String getContentID);

    String getItemName(String contentID);

    Icon getIcon(String id);

    Set<String> getAllItemsID();

}
