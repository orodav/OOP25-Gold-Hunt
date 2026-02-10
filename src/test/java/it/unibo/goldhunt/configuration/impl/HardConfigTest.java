package it.unibo.goldhunt.configuration.impl;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import java.util.Map;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link HardConfig}.
 */
class HardConfigTest {

    private HardConfig config;

    @BeforeEach
    void setup() {
        config = new HardConfig();
    }

    @Test
    void testBoardSize() {
        assertEquals(22, config.getBoardSize());
    }

    @Test
    void testTrapCount() {
        assertEquals(99, config.getTrapCount());
    }

    @Test 
    void testItemConfigNotNull() {
        assertNotNull(config.getItemConfig());
    }

    @Test
    void testItemQuantities() {
        Map<String, Integer> items = config.getItemConfig();
        assertEquals(4, items.getOrDefault("D", 0));
        assertEquals(24, items.getOrDefault("G", 0));
        assertEquals(4, items.getOrDefault("X", 0));
        assertEquals(5, items.getOrDefault("L", 0));
        assertEquals(1, items.getOrDefault("C", 0));
        assertEquals(3, items.getOrDefault("M", 0));
        assertEquals(3, items.getOrDefault("P", 0));
        assertEquals(3, items.getOrDefault("S", 0));
    }
}

