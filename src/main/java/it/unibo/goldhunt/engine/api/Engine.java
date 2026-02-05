package it.unibo.goldhunt.engine.api;

import it.unibo.goldhunt.player.api.Player;

public interface Engine {

    /** This interface represents tha game's brain.
     * It contains the game actions and applies the game rules */

    Player player();

    Status status();

    ActionResult reveal(Position p);

    ActionResult toggleFlag(Position p);

    ActionResult move(Position target);
}
