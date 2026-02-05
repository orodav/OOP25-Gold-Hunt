package it.unibo.goldhunt.configuration.api;

import java.util.Map;

import it.unibo.goldhunt.items.api.Item;

public interface LevelConfig {
    public Map<Class<? extends Item>, Integer> getDifficultyConfig();

    public int getBoardSize();
}
