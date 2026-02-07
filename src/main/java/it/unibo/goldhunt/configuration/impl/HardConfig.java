package it.unibo.goldhunt.configuration.impl;

import java.util.HashMap;
import java.util.Map;

import it.unibo.goldhunt.configuration.api.LevelConfig;

public class HardConfig implements LevelConfig {

    private static final int BOARD_SIZE = 22;

    private static final int TRAP_COUNT = 99;

    private static final int DYNAMITE_COUNT = 4;
    private static final int GOLD_COUNT = 24;
    private static final int GOLDX3_COUNT = 4;
    private static final int LIFES_COUNT = 5;
    private static final int LUCKY_CLOVER_COUNT = 3;
    private static final int CHART_COUNT = 3;
    private static final int PICKAXE_COUNT = 3;
    private static final int SHIELD_COUNT = 3;

    public HardConfig() {
        getItemConfig();
    }

    @Override
    public Map<String, Integer> getItemConfig() {

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

    @Override 
    public int getTrapCount() {
        return TRAP_COUNT;
    }

    @Override
    public int getBoardSize() {
        return BOARD_SIZE;
    }

}
