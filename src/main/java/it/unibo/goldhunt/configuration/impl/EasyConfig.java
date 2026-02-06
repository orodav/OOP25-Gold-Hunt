//SARA
package it.unibo.goldhunt.configuration.impl;

import java.util.HashMap;
import java.util.Map;
import it.unibo.goldhunt.configuration.api.LevelConfig;

/**
 * This class is one of the implementations of LevelConfig and represents 
 * the configuration for the EASY difficulty level.
 */
public class EasyConfig implements LevelConfig {

    private static final int BOARD_SIZE = 9;

    private static final int TRAP_COUNT = 10;

    private static final int DYNAMITE_COUNT = 2;
    private static final int GOLD_COUNT = 5;
    private static final int GOLDX3_COUNT = 1;
    private static final int LIFES_COUNT = 3;
    private static final int LUCKY_CLOVER_COUNT = 1;
    private static final int CHART_COUNT = 0;
    private static final int PICKAXE_COUNT = 1;
    private static final int SHIELD_COUNT = 1;

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, Integer> getItemConfig() {
        
        Map<String, Integer> easyData = new HashMap<>();
        
        easyData.put("D", DYNAMITE_COUNT);
        easyData.put("G", GOLD_COUNT);
        easyData.put("X", GOLDX3_COUNT);
        easyData.put("L", LIFES_COUNT);
        easyData.put("C", LUCKY_CLOVER_COUNT);
        easyData.put("M", CHART_COUNT);
        easyData.put("P", PICKAXE_COUNT);
        easyData.put("S", SHIELD_COUNT);
        
        return easyData;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getTrapCount() {
        return TRAP_COUNT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getBoardSize() {
        return BOARD_SIZE;
    }
}
