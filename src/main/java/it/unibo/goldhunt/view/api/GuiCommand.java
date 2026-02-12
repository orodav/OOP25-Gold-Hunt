package it.unibo.goldhunt.view.api;

import it.unibo.goldhunt.configuration.api.Difficulty;
import it.unibo.goldhunt.engine.api.Direction;
import it.unibo.goldhunt.engine.api.Position;
import it.unibo.goldhunt.items.api.ItemTypes;

/**
 * Represents a user interaction issued from the GUI.
 * It will be handled by the GameController.
 */
public interface GuiCommand {

    /**
     * Requests to move the player to a specific position.
     */
    record MoveTo(Position pos) implements GuiCommand { }

    /**
     * Requests a directional move.
     */
    record Move(Direction dir) implements GuiCommand { }

    /**
     * Requests to reveal the cell at the given position.
     */
    record Reveal(Position pos) implements GuiCommand { }

    /**
     * Requests to toggle a flag on the cell at the given position.
     */
    record ToggleFlag(Position pos) implements GuiCommand { }

    /**
     * Requests to buy one unit of the given item type in the shop.
     */
    record Buy(ItemTypes type) implements GuiCommand { }

    /**
     * Requests to leave the shop.
     */
    record LeaveShop() implements GuiCommand { }

    /**
     * Requests to start a new game with the given difficulty.
     */
    record NewGame(Difficulty difficulty) implements GuiCommand { }

    /**
     * Requests to reset the current session.
     */
    record Reset() implements GuiCommand { }
}
