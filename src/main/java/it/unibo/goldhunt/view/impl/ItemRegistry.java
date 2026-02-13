package it.unibo.goldhunt.view.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.swing.Icon;

import it.unibo.goldhunt.view.api.ItemVisualRegistry;
import it.unibo.goldhunt.view.api.UIFactory;

/**
 * Default implementation of {@link ItemVisualRegistry}.
 * 
 * <p>
 * This class provides a registry of visual representation for all 
 * game items.
 * The registry is immutable after construction.
 */
public final class ItemRegistry implements ItemVisualRegistry {

    private final Map<String, ItemMap> iteMap;

    /**
     * Creates a new {@code ItemRegistry} using the provided {@link UIFactory}
     * to load item icons.
     * 
     * @param factori the UI factory used to load graphical resources.
     * @throws IllegalArgumentException if {@code factori} is {@code null}.
     */
    public ItemRegistry(final UIFactory factori) {

        if (factori == null) {
            throw new IllegalArgumentException();
        }

        final Map<String, ItemMap> factoriMap = new HashMap<>();

        factoriMap.put("M", new ItemMap("M", "Map, reveals traps nearby", 
        factori.loadIcon("map.png")));

        factoriMap.put("D", new ItemMap("D", "Dynamite, disarm and reevals cells nearby", 
        factori.loadIcon("dynamite.png")));

        factoriMap.put("G", new ItemMap("G", "Gold, game value", 
        factori.loadIcon("gold.png")));

        factoriMap.put("X", new ItemMap("X", "GoldX3, game value x3", 
        factori.loadIcon("goldX3.png")));

        factoriMap.put("L", new ItemMap("L", "Life, useful to survive the game", 
        factori.loadIcon("life.png")));

        factoriMap.put("C", new ItemMap("C", "Lucky Clover, doubles the gold", 
        factori.loadIcon("luckyClover.png")));

        factoriMap.put("P", new ItemMap("P", "Pickaxe, disarms and reveals a row or a column", 
        factori.loadIcon("pickaxe.png")));

        factoriMap.put("S", new ItemMap("S", "Shield, protects the player", 
        factori.loadIcon("shield.png")));

        factoriMap.put("T", new ItemMap("T", "Trap, decreases the lives by 1", 
        factori.loadIcon("trap.png")));

        this.iteMap = Collections.unmodifiableMap(factoriMap);
    }

    @Override
    public String getGlyph(final String getContentID) {
        return getItem(getContentID).glyph;
    }

    @Override
    public String getItemName(final String contentID) {
        return getItem(contentID).itemName;
    }

    @Override
    public Icon getIcon(final String id) {
        return getItem(id).icon;
    }

    @Override
    public Set<String> getAllItemsID() {
        return iteMap.keySet();
    }

    private ItemMap getItem(final String itemID) {
        if (itemID == null || !iteMap.containsKey(itemID)) {
            throw new IllegalArgumentException();
        }
        return iteMap.get(itemID);
    }

    // CLASSE INTERNA SPOSTATA IN FONDO
    private static class ItemMap {

        private final String glyph;
        private final String itemName;
        private final Icon icon;

        ItemMap(final String glyph, final String itemName, final Icon icon) {
            this.glyph = glyph;
            this.icon = icon;
            this.itemName = itemName;
        }
    }
}
