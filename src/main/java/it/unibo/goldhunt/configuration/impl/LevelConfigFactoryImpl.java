//SARA
package it.unibo.goldhunt.configuration.impl;

import it.unibo.goldhunt.configuration.api.Difficulty;
import it.unibo.goldhunt.configuration.api.LevelConfig;
import it.unibo.goldhunt.configuration.api.LevelConfigFactory;

/**
 * This class is the implementation of LevelConfigFactory.
 */
public class LevelConfigFactoryImpl implements LevelConfigFactory{

    /**
     * {@inheritDoc}
     */
    @Override
    public LevelConfig create(Difficulty difficulty) {
        return switch (difficulty) {
            case EASY -> new EasyConfig();
            case MEDIUM -> new MediumConfig();
            case HARD -> new HardConfig();
        };
    }
}
