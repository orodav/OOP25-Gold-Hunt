package it.unibo.goldhunt.view.api;

import it.unibo.goldhunt.view.viewmodel.GameViewState;

/**
 * Represents the graphical user interface responsible for rendering the GameViewState.
 */
public interface GameView {

    void attach(GameController controller);

    void render(GameViewState state);

    /**
     * metodo presente solo se javaFX/swing start/launch 
     * viene gestito altrove
     */
    void show();
}
