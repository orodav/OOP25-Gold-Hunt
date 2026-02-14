package it.unibo.goldhunt.view.swing.screens;

import java.awt.BorderLayout;
import java.util.Objects;

import javax.swing.JButton;
import javax.swing.JPanel;

import it.unibo.goldhunt.engine.api.Position;
import it.unibo.goldhunt.items.api.ItemTypes;
import it.unibo.goldhunt.view.api.GameView;
import it.unibo.goldhunt.view.api.ItemVisualRegistry;
import it.unibo.goldhunt.view.api.UIFactory;
import it.unibo.goldhunt.view.swing.components.BoardPanel;
import it.unibo.goldhunt.view.swing.components.HudPanel;
import it.unibo.goldhunt.view.swing.components.InventoryPanel;
import it.unibo.goldhunt.view.swing.components.LegendPanel;
import it.unibo.goldhunt.view.viewstate.GameViewState;

/**
 * This class implements the main gameplay screen.
 * This panel is responsible for rendering the active game state and
 * collecting user interactions during gameplay.
 */
public final class PlayingPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    /**
     * Listener for gameplay-related user actions.
     */
    public interface Listener {

        /**
         * Invoked when the user reveals a cell.
         * 
         * @param p the board position to reveal
         */
        void onReveal(Position p);

        /**
         * Invoked when the user toggles a flag on a cell.
         * 
         * @param p the board position
         */
        void onToggleFlag(Position p);

        /**
         * Invoked when the user uses an item from the inventory.
         * 
         * @param t the item type used
         */
        void onUseItem(ItemTypes t);

        /**
         * Invoked when the user requests to leave the current run
         * and return to the main menu.
         */
        void onLeaveToMenu();
    }

    /**
     * Default no-operation listener to avoid null checks.
     */
    private static final Listener NO_OP_LISTENER = new Listener() {

        @Override 
        public void onReveal(final Position p) { }

        @Override 
        public void onToggleFlag(final Position p) { }

        @Override 
        public void onUseItem(final ItemTypes t) { }

        @Override 
        public void onLeaveToMenu() { }
    };

    private transient Listener listener = NO_OP_LISTENER;

    private final BoardPanel boardPanel;
    private final InventoryPanel inventoryPanel;
    private final HudPanel hudPanel;

    /**
     * Creates the playing screen.
     *
     * @param factory UI factory
     * @param itemRegistry registry for item visuals (icons/glyphs)
     * @throws NullPointerException if any argument is null
     */
    public PlayingPanel(
            final UIFactory factory,
            final ItemVisualRegistry itemRegistry
    ) {
        super(new BorderLayout());

        Objects.requireNonNull(factory);
        Objects.requireNonNull(itemRegistry);

        final LegendPanel legendPanel = new LegendPanel(itemRegistry);
        this.add(legendPanel, BorderLayout.EAST);

        this.boardPanel = new BoardPanel(itemRegistry);
        this.add(this.boardPanel, BorderLayout.CENTER);

        this.inventoryPanel = new InventoryPanel();
        this.add(this.inventoryPanel, BorderLayout.WEST);

        final JPanel south = new JPanel(new BorderLayout());

        this.hudPanel = new HudPanel();
        south.add(this.hudPanel, BorderLayout.CENTER);

        final JButton backToMenu = factory.createButton("MENU");
        Objects.requireNonNull(backToMenu);

        backToMenu.addActionListener(e -> this.listener.onLeaveToMenu());
        south.add(backToMenu, BorderLayout.EAST);

        this.add(south, BorderLayout.SOUTH);

        this.boardPanel.setListener(new GameView.Listener() {

            @Override
            public void onReveal(final Position p) {
                listener.onReveal(p);
            }

            @Override
            public void onToggleFlag(final Position p) {
                listener.onToggleFlag(p);
            }

            @Override 
            public void onBuy(final ItemTypes t) { }

            @Override 
            public void onLeaveShop() { }

            @Override 
            public void onUseItem(final ItemTypes t) { }

            @Override 
            public void onStartGame() { }
        });

        this.inventoryPanel.setListener(type -> listener.onUseItem(type));
    }

    /**
     * Registers a listener for gameplay actions.
     *
     * @param listener the listener to notify
     * @throws NullPointerException if listener is null
     */
    public void setListener(final Listener listener) {
        this.listener = Objects.requireNonNull(listener);

        this.inventoryPanel.setListener(type -> this.listener.onUseItem(type));

        this.boardPanel.setListener(new GameView.Listener() {

            @Override
            public void onReveal(final Position p) {
                PlayingPanel.this.listener.onReveal(p);
            }

            @Override
            public void onToggleFlag(final Position p) {
                PlayingPanel.this.listener.onToggleFlag(p);
            }

            @Override 
            public void onBuy(final ItemTypes t) { }

            @Override 
            public void onLeaveShop() { }

            @Override 
            public void onUseItem(final ItemTypes t) { }

            @Override 
            public void onStartGame() { }
        });
    }

    /**
     * Renders the gameplay screen based on the given state.
     *
     * @param state the current view state
     * @throws NullPointerException if state is null
     */
    public void render(final GameViewState state) {
        Objects.requireNonNull(state, "state can't be null");

        this.boardPanel.render(state);
        this.hudPanel.render(state.hud());
        this.inventoryPanel.render(state.inventory());
    }

    /**
     * Returns the board panel component.
     * 
     * @return the board panel
     */
    public BoardPanel getBoardPanel() {
        return this.boardPanel;
    }

    /**
     * Returns the inventory panel component.
     * 
     * @return the inventory panel
     */
    public InventoryPanel getInventoryPanel() {
        return this.inventoryPanel;
    }

    /**
     * Returns the HUD panel component.
     * 
     * @return the HUD panel
     */
    public HudPanel getHudPanel() {
        return this.hudPanel;
    }
}
