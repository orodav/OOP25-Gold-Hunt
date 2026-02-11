package it.unibo.goldhunt.view.api;

import java.util.function.Consumer;

import it.unibo.goldhunt.engine.api.GameState;
import it.unibo.goldhunt.view.viewmodel.GameViewState;

/**
 * Acts as the UI-facing controller, handling GUI commands and publishing view state updates.
 */
public interface GameController {

    GameState state();

    void handle(GuiCommand command);

    void addListener(Consumer<GameViewState> listener);

    void removeListener(Consumer<GameViewState> listener);
}
