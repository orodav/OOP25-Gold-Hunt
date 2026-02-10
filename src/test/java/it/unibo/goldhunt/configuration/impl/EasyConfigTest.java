package it.unibo.goldhunt.configuration.impl;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import java.util.Map;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link EasyConfig}.
 */
class EasyConfigTest {

    private EasyConfig config;

    @BeforeEach
    void setup() {
        config = new EasyConfig();
    }

    @Test
    void testBoardSize() {
        assertEquals(9, config.getBoardSize());
    }

    @Test
    void testTrapCount() {
        assertEquals(10, config.getTrapCount());
    }

    @Test 
    void testItemConfigNotNull() {
        assertNotNull(config.getItemConfig());
    }

    @Test
    void testItemQuantities() {
        Map<String, Integer> items = config.getItemConfig();
        assertEquals(2, items.getOrDefault("D", 0));
        assertEquals(5, items.getOrDefault("G", 0));
        assertEquals(1, items.getOrDefault("X", 0));
        assertEquals(3, items.getOrDefault("L", 0));
        assertEquals(1, items.getOrDefault("C", 0));
        assertEquals(0, items.getOrDefault("M", 0));
        assertEquals(1, items.getOrDefault("P", 0));
        assertEquals(1, items.getOrDefault("S", 0));
    }
}


