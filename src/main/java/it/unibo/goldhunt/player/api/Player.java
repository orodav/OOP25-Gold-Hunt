package main.java.it.unibo.goldhunt.player.api;

import it.unibo.goldhunt.engine.api.Position;

public interface Player {

    Position position();

    int livesCount();

    int goldCount();

    Inventory inventory();

    // setMultiplier();
}
