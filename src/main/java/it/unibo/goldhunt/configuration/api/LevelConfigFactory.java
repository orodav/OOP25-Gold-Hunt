package it.unibo.goldhunt.configuration.api;

public interface LevelConfigFactory {
    LevelConfig create(Difficulty difficulty);
}
