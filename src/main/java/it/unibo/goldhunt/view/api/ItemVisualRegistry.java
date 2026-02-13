package it.unibo.goldhunt.view.api;

import java.util.Set;

import javax.swing.Icon;

/**
 * Registry responsible for managing the visual information associated with game items.
 * 
 * <p>
 * An {@code ItemVisualRegistry} provides presentation data such as textual glyphs,
 * human-readable names, and graphical icons.
 */
public interface ItemVisualRegistry {

    /**
     * Returns the glyph of the item.
     * 
     * @param getContentID the identifier for the item.
     * @return the glyph of the item
     */
    String getGlyph(String getContentID);

    /**
     * Returns the full name of the item.
     * 
     * @param contentID the identifier of the item.
     * @return the name of the item.
     */
    String getItemName(String contentID);

    /**
     * Returns the graphical icon of the item.
     * 
     * @param id the identifier of the item.
     * @return the icon of the given item.
     */
    Icon getIcon(String id);

    /**
     * Returns the set of all items identifiers registred.
     * 
     * @return a {@link Set} containing all IDs
     */
    Set<String> getAllItemsID();

}
