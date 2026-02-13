package it.unibo.goldhunt.view.api;

import java.util.Optional;

import it.unibo.goldhunt.configuration.api.Difficulty;
import it.unibo.goldhunt.engine.api.Position;
import it.unibo.goldhunt.items.api.ItemTypes;
import it.unibo.goldhunt.view.viewstate.GameViewState;

/**
 * Acts as the UI-facing controller.
 * Handles {@link GuiCommand}s and publish immutable {@link GameViewState} snapshots.
 * 
 * <p>
 * This controller acts as the boundary between the graphical interface
 * and the underlying game session logic.
 * It exclusively coordinates game flow and state transitions.
 */
public interface GameController {

    /**
     * Returns the most recent immutable snapshot of the UI state.
     * 
     * @return the current {@link GameViewState}
     */
    GameViewState state();

    /**
     * Handles a GUI command and returns the updated UI state snapshot.
     * 
     * @param command the command issued by the GUI
     * @return the updated {@link GameViewState}
     * @throws NullPointerException if {@code command} is {@code null}
     */
    GameViewState handle(GuiCommand command);

    /**
     * Handles a movement request toward the specified board position.
     * 
     * @param pos the target position
     * @return the updated {@link GameViewState}
     * @throws NullPointerException if {@code pos} is {@code null}
     */
    GameViewState handleMoveTo(Position pos);

    /**
     * Handles a reveal request on the specified board position.
     * 
     * @param pos the position to reveal
     * @return the updated {@link GameViewState}
     */
    GameViewState handleReveal(Position pos);

    /**
     * Handles a flag toggle request on the specified board position.
     * 
     * @param pos the position on which the flag should be toggled
     * @return the updated {@link GameViewState}
     * @throws NullPointerException if {@code pos} is {@code null}
     */
    GameViewState handleToggleFlag(Position pos);

    /**
     * Handles a shop purchase request for the specified item type.
     * 
     * @param type the item type to buy
     * @return the updated {@link GameViewState}
     * @throws NullPointerException if {@code type} is {@code null}
     */
    GameViewState handleBuy(ItemTypes type);

    /**
     * handles an item usage request.
     * 
     * <p>
     * If the item requires a target position, it is provided through
     * {@code target}. Otherwise {@code target} should be {@link Optional#empty()}
     * 
     * @param type the item type to use
     * @param target the target position
     * @return the updated {@link GameViewState}
     * @throws NullPointerException if {@code type} is {@code null}
     */
    GameViewState handleUseItem(ItemTypes type, Optional<Position> target);

    /**
     * Handles a "continue" request in the current screen context.
     * 
     * @return the updated {@link GameViewState}
     */
    GameViewState handleContinue();

    /**
     * Handles a request to leave the current run and return to the main menu.
     * 
     * <p>
     * This action resets run-related data such as inventory, gold, level progession.
     * 
     * @return the updated {@link GameViewState}
     */
    GameViewState handleLeaveToMenu();

    /**
     * Handles a request to set the current difficulty for the game run.
     * 
     * @param difficulty the difficulty selected
     * @return the updated {@link GameViewState}
     */
    GameViewState handleSetDifficulty(Difficulty difficulty);
}
