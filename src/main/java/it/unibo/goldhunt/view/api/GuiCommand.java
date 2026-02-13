package it.unibo.goldhunt.view.api;

import java.util.Optional;

import it.unibo.goldhunt.engine.api.Position;
import it.unibo.goldhunt.items.api.ItemTypes;
import it.unibo.goldhunt.view.viewstate.GameViewState;

/**
 * Represents a user interaction issued from the GUI.
 * It will be handled by the GameController.
 * It assures a polymorphic dynamic implementation.
 */
@FunctionalInterface
public interface GuiCommand {

    /**
     * Applies this command to the given controller.
     * 
     * @param controller the controller handling the command
     * @return the updated view state
     */
    GameViewState apply(GameController controller);

    /**
     * Requests to move the player to a specific position.
     * 
     * @param pos the target position
     */
    record MoveTo(Position pos) implements GuiCommand {

        @Override
        public GameViewState apply(final GameController controller) {
            return controller.handleMoveTo(pos);
        }
    }

    /**
     * Requests to reveal the cell at the given position.
     * 
     * @param pos the target position
     */
    record Reveal(Position pos) implements GuiCommand {

        @Override
        public GameViewState apply(final GameController controller) {
            return controller.handleReveal(pos);
        }
    }

    /**
     * Requests to toggle a flag on the cell at the given position.
     * 
     * @param pos the target position
     */
    record ToggleFlag(Position pos) implements GuiCommand {

        @Override
        public GameViewState apply(final GameController controller) {
            return controller.handleToggleFlag(pos);
        }
    }

    /**
     * Requests to buy one unit of the given item type in the shop.
     * 
     * @param type the item type to buy
     */
    record Buy(ItemTypes type) implements GuiCommand {

        @Override
        public GameViewState apply(final GameController controller) {
            return controller.handleBuy(type);
        }
    }

    /**
     * Requests to use one unit of an item type.
     * This method optionally targets a position.
     * If the item does not require a target, 
     *      {@code target} should be {@link Optional#empty()}.
     * 
     * @param type the item type to use
     * @param target the optional target position
     */
    record UseItem(ItemTypes type, Optional<Position> target) implements GuiCommand {

        @Override
        public GameViewState apply(final GameController controller) {
            return controller.handleUseItem(type, target);
        }
    }

    /**
     * Requests to proceed with the current flow.
     * The meaning depends on the current phase.
     */
    record Continue() implements GuiCommand {

        @Override
        public GameViewState apply(final GameController controller) {
            return controller.handleContinue();
        }
    }

    /**
     * Requests to leave the current run and return to the main menu.
     * This action resets run-related data.
     */
    record LeaveToMenu() implements GuiCommand {

        @Override
        public GameViewState apply(final GameController controller) {
            return controller.handleLeaveToMenu();
        }
    }
}
