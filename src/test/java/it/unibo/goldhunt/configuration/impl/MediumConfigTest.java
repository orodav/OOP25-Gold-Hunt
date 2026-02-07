package it.unibo.goldhunt.configuration.impl;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import java.util.Map;
import org.junit.jupiter.api.Test;

class MediumConfigTest {

    private MediumConfig config;

    @BeforeEach
    void setup() {
        config = new MediumConfig();
    }

    @Test
    void testBoardSize() {
        assertEquals(16, config.getBoardSize());
    }

    @Test
    void testTrapCount() {
        assertEquals(40, config.getTrapCount());
    }

    @Test 
    void testItemConfigNotNull() {
        assertNotNull(config.getItemConfig());
    }

    @Test
    void testItemQuantities() {
        Map<String, Integer> items = config.getItemConfig();
        assertEquals(3, items.getOrDefault("D", 0));
        assertEquals(12, items.getOrDefault("G", 0));
        assertEquals(2, items.getOrDefault("X", 0));
        assertEquals(4, items.getOrDefault("L", 0));
        assertEquals(1, items.getOrDefault("C", 0));
        assertEquals(2, items.getOrDefault("M", 0));
        assertEquals(2, items.getOrDefault("P", 0));
        assertEquals(2, items.getOrDefault("S", 0));
    }
}
