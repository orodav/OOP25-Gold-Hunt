package it.unibo.goldhunt.view.api;

import java.util.Optional;

import it.unibo.goldhunt.configuration.api.Difficulty;
import it.unibo.goldhunt.engine.api.Direction;
import it.unibo.goldhunt.engine.api.Position;
import it.unibo.goldhunt.items.api.ItemTypes;
import it.unibo.goldhunt.view.viewstate.GameViewState;

/**
 * Acts as the UI-facing controller.
 * Handles {@link GuiCommand}s and publish immutable {@link GameViewState} snapshots.
 */
public interface GameController {

    /**
     * Returns the most recent immutable snapshot of the UI state.
     * 
     * @return the corrent GameViewState
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

    GameViewState handleMoveTo(Position pos);

    GameViewState handleMove(Direction dir);

    GameViewState handleReveal(Position pos);

    GameViewState handleToggleFlag(Position pos);

    GameViewState handleBuy(ItemTypes type);

    GameViewState handleUseItem(ItemTypes type, Optional<Position> target);

    GameViewState handleContinue();

    GameViewState handleLeaveToMenu();

    GameViewState handleNewGame(Difficulty difficulty);
}
