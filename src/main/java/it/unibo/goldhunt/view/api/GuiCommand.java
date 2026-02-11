package it.unibo.goldhunt.view.api;

import it.unibo.goldhunt.configuration.api.Difficulty;
import it.unibo.goldhunt.engine.api.Direction;
import it.unibo.goldhunt.engine.api.Position;
import it.unibo.goldhunt.items.api.ItemTypes;

/**
 * Represents a user interacion issued from the GUI and handled
 * by the GameController
 */
public interface GuiCommand {

    /**
     * Move the player to a specific position.
     */
    record MoveTo(Position pos) implements GuiCommand {}
    
    /**
     * Represents a directional move.
     */
    record Move(Direction dir) implements GuiCommand {}

    /**
     * Reveal the cell at the given position.
     */
    record Reveal(Position pos) implements GuiCommand {}

    /**
     * Toggle a flag on the cell at the given position.
     */
    record ToggleFlag(Position pos) implements GuiCommand {}

    /**
     * Buy one unit of the given item type in the shop.
     */
    record Buy(ItemTypes type) implements GuiCommand {}

    /**
     * Leaves the shop.
     */
    record LeaveShop() implements GuiCommand {}

    /**
     * Start a new game with the given difficulty.
     */
    record NewGame(Difficulty difficulty) implements GuiCommand {}

    /**
     * Resets the current session.
     */
    record Reset() implements GuiCommand {}
}
