
package it.unibo.goldhunt.configuration.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

/**
 * Tests for {@link MediumConfig}.
 */
class MediumConfigTest {

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

    private MediumConfig config;

    @BeforeEach
    void setup() {
        config = new MediumConfig();
    }

    @Test
    void testBoardSize() {
        assertEquals(BOARD_SIZE, config.getBoardSize());
    }

    @Test
    void testTrapCount() {
        assertEquals(TRAP_COUNT, config.getTrapCount());
    }

    @Test 
    void testItemConfigNotNull() {
        assertNotNull(config.getItemConfig());
    }

    @Test
    void testItemQuantities() {
        final Map<String, Integer> items = config.getItemConfig();
        assertEquals(DYNAMITE_COUNT, items.getOrDefault("D", 0));
        assertEquals(GOLD_COUNT, items.getOrDefault("G", 0));
        assertEquals(GOLDX3_COUNT, items.getOrDefault("X", 0));
        assertEquals(LIFES_COUNT, items.getOrDefault("L", 0));
        assertEquals(LUCKY_CLOVER_COUNT, items.getOrDefault("C", 0));
        assertEquals(CHART_COUNT, items.getOrDefault("M", 0));
        assertEquals(PICKAXE_COUNT, items.getOrDefault("P", 0));
        assertEquals(SHIELD_COUNT, items.getOrDefault("S", 0));
    }
}
