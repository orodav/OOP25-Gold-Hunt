package it.unibo.goldhunt.player.api;

import it.unibo.goldhunt.engine.api.Position;

public interface Player {

    Position position();

    int livesCount();

    int goldCount();

    Inventory inventory();

    /**
     * Returns a new Player equal to this one, but with the given inventory.
     * 
     * @param inventory the new inventory
     * @return a new Player instance
     * @throws IllegalArgumentException if {@code inventory} is null
     */
    PlayerOperations withInventory(Inventory inventory);

}
