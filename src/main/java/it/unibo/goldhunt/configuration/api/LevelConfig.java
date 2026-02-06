//SARA
package it.unibo.goldhunt.configuration.api;

import java.util.Map;

public interface LevelConfig {
    
    Map<String, Integer> getItemConfig();

    int getTrapCount();

    int getBoardSize();

}

