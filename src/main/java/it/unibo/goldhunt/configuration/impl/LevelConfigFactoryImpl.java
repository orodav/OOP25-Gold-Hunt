
package it.unibo.goldhunt.configuration.impl;

import java.util.Objects;

import it.unibo.goldhunt.configuration.api.Difficulty;
import it.unibo.goldhunt.configuration.api.LevelConfig;
import it.unibo.goldhunt.configuration.api.LevelConfigFactory;

/**
 * This class is the implementation of {@link LevelConfigFactory} 
 * using switch expression to create specific {@link LevelConfig}
 * implementations based on difficulty.
 */
public class LevelConfigFactoryImpl implements LevelConfigFactory {

    /**
     * {@inheritDoc}
     */
    @Override
    public LevelConfig create(final Difficulty difficulty) {
        return switch (Objects.requireNonNull(difficulty)) {
            case EASY -> new EasyConfig();
            case MEDIUM -> new MediumConfig();
            case HARD -> new HardConfig();
        };
    }
}
