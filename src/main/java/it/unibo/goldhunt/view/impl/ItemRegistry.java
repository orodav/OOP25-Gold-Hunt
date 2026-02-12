package it.unibo.goldhunt.view.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.swing.Icon;

import it.unibo.goldhunt.view.api.ItemVisualRegistry;
import it.unibo.goldhunt.view.api.UIFactory;

public class ItemRegistry implements ItemVisualRegistry {

    private final UIFactory factori = new SwingUIFactory();

    private final Map<String, String> glyphMap = new HashMap<>();
    private final Map<String, String> tooltipMap = new HashMap<>();
    private final Map<String, String> stylekeyMap = new HashMap<>();
    private final Map<String, Icon> iconMap = new HashMap<>();

    public ItemRegistry() {
        glyphMap.put("M", "M");
        tooltipMap.put("M", "Chart");
        stylekeyMap.put("M", "chart-style");
        iconMap.put("M", factori.loadIcon("map.png"));
        
        glyphMap.put("D", "D");
        tooltipMap.put("D", "Dynamite");
        stylekeyMap.put("D", "dynamite-style");
        iconMap.put("D", factori.loadIcon("dynamite.png"));

        glyphMap.put("G", "G");
        tooltipMap.put("G", "Gold");
        stylekeyMap.put("G", "gold-style");
        iconMap.put("G", factori.loadIcon("gold.png"));

        glyphMap.put("L", "L");
        tooltipMap.put("L", "Life");
        stylekeyMap.put("L", "life-style");
        iconMap.put("L", factori.loadIcon("life.png"));

        glyphMap.put("C", "C");
        tooltipMap.put("C", "Lucky Clover");
        stylekeyMap.put("C", "clover-style");
        iconMap.put("C", factori.loadIcon("luckyClover.png"));

        glyphMap.put("P", "P");
        tooltipMap.put("P", "Pickaxe");
        stylekeyMap.put("P", "pickaxe-style");
        iconMap.put("P", factori.loadIcon("pickaxe.png"));

        glyphMap.put("S", "S");
        tooltipMap.put("S", "Shield");
        stylekeyMap.put("S", "shield-style");
        iconMap.put("S", factori.loadIcon("shield.png"));

        glyphMap.put("X", "X");
        tooltipMap.put("X", "Gold x3");
        stylekeyMap.put("X", "goldx3-style");
        iconMap.put("X", factori.loadIcon("goldX3.png"));

        glyphMap.put("T", "T");
        tooltipMap.put("T", "Trap");
        stylekeyMap.put("T", "trap-style");
        iconMap.put("T", factori.loadIcon("trap.png"));
    }

    @Override
    public String getGlyph(String getContentID) {
        return glyphMap.get(getContentID);
    }

    @Override
    public String getTooltip(String contentID) {
        return tooltipMap.get(contentID);
    }

    @Override
    public String getStyleKey(String contentID) {
        return stylekeyMap.get(contentID);
    }

    @Override
    public Icon getIcon(String id) {
        return iconMap.get(id);
    }

    public Set<String> getAllItemsID(){
        return glyphMap.keySet();
    }
}
