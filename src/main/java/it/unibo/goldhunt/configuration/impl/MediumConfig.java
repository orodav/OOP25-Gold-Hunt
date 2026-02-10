//SARA
package it.unibo.goldhunt.configuration.impl;

import java.util.HashMap;
import java.util.Map;
import it.unibo.goldhunt.configuration.api.LevelConfig;

/**
 * This class is one of the implementations of {@link LevelConfig} 
 * for MEDIUM difficulty: 16x16 board, 40 traps, specific item quantities. 
 */
public class MediumConfig implements LevelConfig {

    private static final int BOARD_SIZE = 16;

    private static final int TRAP_COUNT = 40;

    private static final int DYNAMITE_COUNT = 3;
    private static final int GOLD_COUNT = 12;
    private static final int GOLDX3_COUNT = 2;
    private static final int LIFES_COUNT = 4;
    private static final int LUCKY_CLOVER_COUNT = 1;
    private static final int CHART_COUNT = 2;
    private static final int PICKAXE_COUNT = 2;
    private static final int SHIELD_COUNT = 2;
    
    /**
     * {@inheritDoc}
     */
    @Override
    public final Map<String, Integer> getItemConfig() {
        
        Map<String, Integer> mediumData = new HashMap<>();

        mediumData.put("D", DYNAMITE_COUNT);
        mediumData.put("G", GOLD_COUNT);
        mediumData.put("X", GOLDX3_COUNT);
        mediumData.put("L", LIFES_COUNT);
        mediumData.put("C", LUCKY_CLOVER_COUNT);
        mediumData.put("M", CHART_COUNT);
        mediumData.put("P", PICKAXE_COUNT);
        mediumData.put("S", SHIELD_COUNT);

        return mediumData;
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
    public final int getBoardSize() {
        return BOARD_SIZE;
    }
}
