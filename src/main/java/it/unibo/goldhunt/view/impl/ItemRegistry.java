package it.unibo.goldhunt.view.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.swing.Icon;

import it.unibo.goldhunt.view.api.ItemVisualRegistry;
import it.unibo.goldhunt.view.api.UIFactory;

public class ItemRegistry implements ItemVisualRegistry {

    private final Map<String, ItemMap> iteMap;

    private static class ItemMap {

        final String glyph;
        final String itemName;
        final Icon icon;

        ItemMap(final String glyph, final String itemName, final Icon icon ){
            this.glyph = glyph;
            this.icon = icon;
            this.itemName = itemName;
        }
    }

    public ItemRegistry(final UIFactory factori) {

        if (factori == null) {
            throw new IllegalArgumentException();
        }

        Map<String, ItemMap> factoriMap = new HashMap<>();

        factoriMap.put("M", new ItemMap("M", "Map", 
        factori.loadIcon("map.png")));

        factoriMap.put("D", new ItemMap("D", "Dynamite", 
        factori.loadIcon("dynamite.png")));

        factoriMap.put("G", new ItemMap("G", "Gold", 
        factori.loadIcon("gold.png")));

        factoriMap.put("X", new ItemMap("X", "GoldX3", 
        factori.loadIcon("goldX3.png")));

        factoriMap.put("L", new ItemMap("L", "Life", 
        factori.loadIcon("life.png")));

        factoriMap.put("C", new ItemMap("C", "Lucky Clover", 
        factori.loadIcon("luckyClover.png")));

        factoriMap.put("P", new ItemMap("P", "Pickaxe", 
        factori.loadIcon("pickaxe.png")));

        factoriMap.put("S", new ItemMap("S", "Shield", 
        factori.loadIcon("shield.png")));

        factoriMap.put("T", new ItemMap("T", "Trap", 
        factori.loadIcon("trap.png")));

        this.iteMap = Collections.unmodifiableMap(factoriMap);
    }  

    private ItemMap getItem(final String itemID) {
        if (itemID == null || !iteMap.containsKey(itemID)) {
            throw new IllegalArgumentException();
        }
        return iteMap.get(itemID);
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
}