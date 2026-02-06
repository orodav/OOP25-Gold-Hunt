//SARA
package it.unibo.goldhunt.configuration.api;

/**
 * Factory interface used to create {@link LevelConfig} instances
 * based on the selected {@link Difficulty}. 
 */
public interface LevelConfigFactory {
    
    /**
     * Creates a {@link LevelConfig} for the given difficulty.
     * 
     * @param difficulty the selected difficulty level
     * @return the corresponding level configuration
     */
    LevelConfig create(Difficulty difficulty);
}

