package it.unibo.goldhunt.view.api;

import javax.swing.JComponent;

import it.unibo.goldhunt.engine.api.Position;
import it.unibo.goldhunt.items.api.ItemTypes;
import it.unibo.goldhunt.view.viewstate.GameViewState;

/**
 * Root view interface of the game UI.
 * 
 * <p>
 * A {@code GameView} is responsible for rendering the entire
 * {@link GameViewState} and forwarding user interactions
 * to a registered {@link Listener}.
 */
public interface GameView {

    /**
     * Renders the UI based on the provided immutable snapshot.
     * 
     * @param state the current game view state
     * @throws NullPointerException if state is null
     */
    void render(GameViewState state);

    /**
     * Returns the root Swing component representing this view.
     * 
     * @return the root UI component
     */
    JComponent component();

    /**
     * Registers a listener to receive user interaction events.
     * 
     * @param listener the listener to notify
     * @throws NullPointerException if listener is null
     */
    void setListener(Listener listener);

    /**
     * Listener for user-generated UI events.
     */
    interface Listener {

        /**
         * Called when the user requests to reveal a cell.
         * 
         * @param p the position to reveal
         */
        void onReveal(Position p);

        /**
         * Called when the user toggles a flag on a cell.
         * 
         * @param p the target position
         */
        void onToggleFlag(Position p);

        /**
         * Called when the user attempts to buy an item from the shop.
         * 
         * @param t the item type to buy
         */
        void onBuy(ItemTypes t);

        /**
         * Called when the user leaves the shop screen.
         */
        void onLeaveShop();

        /**
         * Called when the user attempts to use an item from the inventory.
         * 
         * @param t the item type to use
         */
        void onUseItem(ItemTypes t);

        /**
         * Called when the user starts a new game.
         */
        void onStartGame();
    }
}
