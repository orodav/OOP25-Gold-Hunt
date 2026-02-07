package it.unibo.goldhunt.configuration.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.goldhunt.configuration.api.Difficulty;
import it.unibo.goldhunt.configuration.api.LevelConfig;
import it.unibo.goldhunt.configuration.api.LevelConfigFactory;

public class LevelConfigFactoryImplTest {

    private LevelConfigFactory factory;

    @BeforeEach
    void setup() {
        factory = new LevelConfigFactoryImpl();
    }

    @Test
    void testCreateEasyConfig() {
        LevelConfig config = factory.create(Difficulty.EASY);
        assertTrue(config instanceof EasyConfig);  
    }

    @Test
    void testCreateMediumConfig() {
        LevelConfig config = factory.create(Difficulty.MEDIUM);
        assertTrue(config instanceof MediumConfig);
    }

    @Test
    void testCreateHardConfig() {
        LevelConfig config = factory.create(Difficulty.HARD);
        assertTrue(config instanceof HardConfig);
    }

    @Test
    void testCreateWithNullThrowsEcxeption() {
        assertThrows(NullPointerException.class, () -> factory.create(null));
    }
}