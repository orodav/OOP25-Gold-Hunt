//SARA
package it.unibo.goldhunt.configuration.impl;

import java.util.HashMap;
import java.util.Map;
import it.unibo.goldhunt.configuration.api.LevelConfig;

/**
 * This class is one of the implementations of {@link LevelConfig} 
 * for HARD difficulty: 22x22 board, 99 traps, specific item quantities. 
 */
public class HardConfig implements LevelConfig {

    private static final int BOARD_SIZE = 22;

    private static final int TRAP_COUNT = 99;

    private static final int DYNAMITE_COUNT = 4;
    private static final int GOLD_COUNT = 24;
    private static final int GOLDX3_COUNT = 4;
    private static final int LIFES_COUNT = 5;
    private static final int LUCKY_CLOVER_COUNT = 1;
    private static final int CHART_COUNT = 3;
    private static final int PICKAXE_COUNT = 3;
    private static final int SHIELD_COUNT = 3;

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, Integer> getItemConfig() {

        Map<String, Integer> hardData = new HashMap<>();

        hardData.put("D", DYNAMITE_COUNT);
        hardData.put("G", GOLD_COUNT);
        hardData.put("X", GOLDX3_COUNT);
        hardData.put("L", LIFES_COUNT);
        hardData.put("C", LUCKY_CLOVER_COUNT);
        hardData.put("M", CHART_COUNT);
        hardData.put("P", PICKAXE_COUNT);
        hardData.put("S", SHIELD_COUNT);

        return hardData;
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

