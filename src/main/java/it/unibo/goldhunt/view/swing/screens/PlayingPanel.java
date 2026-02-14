package it.unibo.goldhunt.view.swing.screens;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.Objects;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

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

    private static final double INVENTORY_WIDTH = 0.20;
    private static final double LEGEND_WIDTH = 0.14;
    private static final double HUD_HEIGHT = 0.10;

    private static final int MIN_INVENTORY = 180;
    private static final int MAX_INVENTORY = 320;

    private static final int MIN_LEGEND = 160;
    private static final int MAX_LEGEND = 280;

    private static final int MIN_HUD = 60;
    private static final int MAX_HUD = 100;


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
    private final JScrollPane legendScroll;

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
        this.legendScroll = new JScrollPane(
            legendPanel,
            ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
            ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
        );
        this.legendScroll.setBorder(null);
        this.legendScroll.getVerticalScrollBar().setUnitIncrement(12);
        this.add(this.legendScroll, BorderLayout.EAST);

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

        this.addComponentListener(new ComponentAdapter() {

            @Override
            public void componentResized(final ComponentEvent e) {
                updateFixedAreasSizes();
            }
        });

        updateFixedAreasSizes();

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

        this.inventoryPanel.setListener(this.listener::onUseItem);
    }

    /**
     * Registers a listener for gameplay actions.
     *
     * @param listener the listener to notify
     * @throws NullPointerException if listener is null
     */
    public void setListener(final Listener listener) {
        this.listener = Objects.requireNonNull(listener);

        this.inventoryPanel.setListener(this.listener::onUseItem);

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

    private void updateFixedAreasSizes() {
        final int w = Math.max(1, getWidth());
        final int h = Math.max(1, getHeight());

        final int inventoryW = clamp((int) (w * INVENTORY_WIDTH), MIN_INVENTORY, MAX_INVENTORY);
        final int legendW = clamp((int) (w * LEGEND_WIDTH), MIN_LEGEND, MAX_LEGEND);
        final int hudH = clamp((int) (h * HUD_HEIGHT), MIN_HUD, MAX_HUD);

        this.inventoryPanel.setPreferredSize(new Dimension(inventoryW, 1));
        this.inventoryPanel.setMinimumSize(new Dimension(MIN_INVENTORY, 1));
        this.inventoryPanel.setMaximumSize(new Dimension(MAX_INVENTORY, Integer.MAX_VALUE));

        this.legendScroll.setPreferredSize(new Dimension(legendW, 1));
        this.legendScroll.setMinimumSize(new Dimension(MIN_LEGEND, 1));
        this.legendScroll.setMaximumSize(new Dimension(MAX_LEGEND, Integer.MAX_VALUE));

        this.hudPanel.setPreferredSize(new Dimension(1, hudH));
        this.hudPanel.setMinimumSize(new Dimension(1, MIN_HUD));
        this.hudPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, MAX_HUD));

        revalidate();
    }

    private static int clamp(final int v, final int min, final int max) {
        return Math.max(min, Math.min(max, v));
    }

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
}
