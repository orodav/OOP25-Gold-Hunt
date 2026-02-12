package it.unibo.goldhunt.view.api;

import java.util.Optional;

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
     * 
     * @param pos the target position
     */
    record MoveTo(Position pos) implements GuiCommand { }

    /**
     * Requests a directional move.
     * 
     * @param dir the target direction
     */
    record Move(Direction dir) implements GuiCommand { }

    /**
     * Requests to reveal the cell at the given position.
     * 
     * @param pos the target position
     */
    record Reveal(Position pos) implements GuiCommand { }

    /**
     * Requests to toggle a flag on the cell at the given position.
     * 
     * @param pos the target position
     */
    record ToggleFlag(Position pos) implements GuiCommand { }

    /**
     * Requests to buy one unit of the given item type in the shop.
     * 
     * @param type the item type to buy
     */
    record Buy(ItemTypes type) implements GuiCommand { }

    /**
     * Requests to use one unit of an item type.
     * This method optionally targets a position.
     * If the item does not require a target, 
     *      {@code target} should be {@link Optional#empty()}.
     * 
     * @param type the item type to use
     * @param target the optional target position
     */
    record UseItem(ItemTypes type, Optional<Position> target) implements GuiCommand { }

    /**
     * Requests to proceed with the current flow.
     * The meaning depends on the current phase.
     */
    record Countinue() implements GuiCommand { }

    /**
     * Requests to leave the current run and return to the main menu.
     * This action resets run-related data.
     */
    record LeaveToMenu() implements GuiCommand { }

    /**
     * Requests to start a new game with the given difficulty.
     * 
     * @param difficulty the selected difficulty
     */
    record NewGame(Difficulty difficulty) implements GuiCommand { }
}
