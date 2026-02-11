
package it.unibo.goldhunt.configuration.api;

/**
 * Factory interface used to create {@link LevelConfig} instances
 * based on the selected {@link Difficulty}. 
 */
@FunctionalInterface
public interface LevelConfigFactory {

    /**
     * Creates a {@link LevelConfig} for the given difficulty.
     * 
     * @param difficulty the selected difficulty level
     * @return the corresponding level configuration
     * @throws NullPointerException if difficulty is null
     */
    LevelConfig create(Difficulty difficulty);
}
