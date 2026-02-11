package it.unibo.goldhunt.view.api;

import java.util.function.Consumer;

import it.unibo.goldhunt.engine.api.GameState;
import it.unibo.goldhunt.view.viewmodel.GameViewState;

/**
 * Acts as the UI-facing controller, handling GUI commands 
 * and publishing view state updates.
 */
public interface GameController {

    /**
     * Returns the most recent immutable snapshot of the UI state.
     * 
     * @return the current {@link GameViewState}
     */
    GameState state();

    /**
     * Handles a GUI command
     * and triggers a new view state if needed.
     * 
     * @param command the GUI command
     * @throws NullPointerException if {@code command} is {@code null}
     */
    void handle(GuiCommand command);

    /**
     * Adds a listener that will be notified when the view state changes.
     * 
     * @param listener the listener
     * @throws NullPointerException if {@code listener} is {@code null}
     */
    void addListener(Consumer<GameViewState> listener);

    /**
     * Removes a previously registered listener.
     * 
     * @param listener the listener
     * @throws NullPointerException if {@code listener} is {@code null}
     */
    void removeListener(Consumer<GameViewState> listener);
}
