package it.unibo.goldhunt.configuration.impl;

import it.unibo.goldhunt.configuration.api.Difficulty;
import it.unibo.goldhunt.configuration.api.LevelConfig;
import it.unibo.goldhunt.configuration.api.LevelConfigFactory;

public class LevelConfigFactoryImpl implements LevelConfigFactory{

    @Override
    public LevelConfig create(Difficulty difficulty) {
        return switch (difficulty) {
            case EASY -> new EasyConfig();
            case MEDIUM -> new MediumConfig();
            case HARD -> new HardConfig();
        };
    }
}
