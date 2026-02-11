package it.unibo.goldhunt.view.impl;

import java.util.function.Consumer;
import it.unibo.goldhunt.engine.api.GameState;
import it.unibo.goldhunt.view.api.GameController;
import it.unibo.goldhunt.view.api.GuiCommand;
import it.unibo.goldhunt.view.viewmodel.GameViewState;

/**
 * Default Swing-oriented implementation of the UI-facing GameController.
 */
public final class GameControllerImpl implements GameController {

    @Override
    public GameState state() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'state'");
    }

    @Override
    public void handle(GuiCommand command) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'handle'");
    }

    @Override
    public void addListener(Consumer<GameViewState> listener) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addListener'");
    }

    @Override
    public void removeListener(Consumer<GameViewState> listener) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'removeListener'");
    }

}
