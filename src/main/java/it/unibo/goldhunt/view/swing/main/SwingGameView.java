package it.unibo.goldhunt.view.swing.main;

import java.util.Objects;
import java.util.Optional;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;

import it.unibo.goldhunt.configuration.api.Difficulty;
import it.unibo.goldhunt.engine.api.Position;
import it.unibo.goldhunt.items.api.ItemTypes;
import it.unibo.goldhunt.view.api.GameController;
import it.unibo.goldhunt.view.api.GameView;
import it.unibo.goldhunt.view.api.GuiCommand;
import it.unibo.goldhunt.view.api.ShopView;
import it.unibo.goldhunt.view.swing.screens.DifficultyPanel;
import it.unibo.goldhunt.view.swing.screens.EndPanel;
import it.unibo.goldhunt.view.swing.screens.MenuPanel;
import it.unibo.goldhunt.view.swing.screens.PlayingPanel;
import it.unibo.goldhunt.view.swing.screens.ShopPanel;
import it.unibo.goldhunt.view.viewstate.GameViewState;

/**
 * Swing implementation of {@link GameView}.
 *
 * <p>
 * This class contains no business logic: it only renders immutable snapshots
 * and forwards user interactions to a registered {@link Listener}.
 */
public final class SwingGameView implements GameView {

    private final MainFrame mainFrame;
    private final GameController controller;

    private transient GameView.Listener listener = new GameView.Listener() {

        @Override
        public void onReveal(final Position p) { }

        @Override
        public void onToggleFlag(final Position p) { }

        @Override
        public void onBuy(final ItemTypes t) { }

        @Override
        public void onLeaveShop() { }

        @Override
        public void onUseItem(final ItemTypes t) { }

        @Override
        public void onStartGame() { }
    };

    public SwingGameView(final MainFrame mainFrame, final GameController controller) {
        this.mainFrame = Objects.requireNonNull(mainFrame, "mainFrame can't be null");
        this.controller = Objects.requireNonNull(controller, "controller can't be null");
    }

    /**
     * Called after construction to connect Swing panels events to the controller.
     */
    public void bind() {

        final MenuPanel menu = this.mainFrame.getMenuPanel();
        menu.setListener(new MenuPanel.Listener() {
            
            @Override
            public void onStartGame() {
                SwingGameView.this.listener.onStartGame();
            }

            @Override
            public void onExitRequested() {
                System.exit(0);
            }
        });

        final DifficultyPanel diff = this.mainFrame.getDifficultyPanel();
        diff.setListener(new DifficultyPanel.Listener() {

            @Override
            public void onDifficultySelected(final Difficulty difficulty) {
                apply(controller.handle(new GuiCommand.SetDifficulty(difficulty)));
            }

            @Override
            public void onBackToMenu() {
                apply(controller.handle(new GuiCommand.LeaveToMenu()));
            }
        });

        final PlayingPanel playing = this.mainFrame.getPlayingPanel();
        playing.setListener(new PlayingPanel.Listener() {

            @Override
            public void onReveal(final Position p) {
                SwingGameView.this.listener.onReveal(p);
            }

            @Override
            public void onToggleFlag(final Position p) {
                SwingGameView.this.listener.onToggleFlag(p);
            }

            @Override
            public void onUseItem(final ItemTypes item) {
                SwingGameView.this.listener.onUseItem(item);
            }

            @Override
            public void onLeaveToMenu() {
                apply(controller.handle(new GuiCommand.LeaveToMenu()));
            }
        });

        final ShopPanel shop = this.mainFrame.getShopPanel();
        shop.setListener(new ShopView.Listener() {

            @Override
            public void onBuy(final ItemTypes type) {
                SwingGameView.this.listener.onBuy(type);
            }

            @Override
            public void onLeaveShop() {
                SwingGameView.this.listener.onLeaveShop();
            }
        });

        final EndPanel end = this.mainFrame.getEndPanel();
        end.setListener(new EndPanel.Listener() {
            
            @Override
            public void onGoToShop() {
                apply(controller.handle(new GuiCommand.Continue()));
            }

            @Override
            public void onBackToMenu() {
                apply(controller.handle(new GuiCommand.LeaveToMenu()));
            }
        });


        setListener(new GameView.Listener() {

            @Override
            public void onReveal(final Position p) {
                apply(controller.handle(new GuiCommand.MoveTo(p)));
            }

            @Override
            public void onToggleFlag(final Position p) {
                apply(controller.handle(new GuiCommand.ToggleFlag(p)));
            }

            @Override
            public void onBuy(final ItemTypes t) {
                apply(controller.handle(new GuiCommand.Buy(t)));
            }

            @Override
            public void onLeaveShop() {
                apply(controller.handle(new GuiCommand.Continue()));
            }

            @Override
            public void onUseItem(final ItemTypes t) {
                apply(controller.handle(new GuiCommand.UseItem(t, Optional.empty())));
            }

            @Override
            public void onStartGame() {
                apply(controller.handle(new GuiCommand.StartGame()));
            }
        });
    }

    @Override
    public void render(final GameViewState state) {
        Objects.requireNonNull(state, "state can't be null");

        if (!SwingUtilities.isEventDispatchThread()) {
            SwingUtilities.invokeLater(() -> render(state));
            return;
        }
        this.mainFrame.show(state.screen());
        this.mainFrame.getPlayingPanel().render(state);
        state.shop().ifPresent(this.mainFrame.getShopPanel()::render);
        this.mainFrame.getEndPanel().render(state.levelState());
    }

    @Override
    public JComponent component() {
        return this.mainFrame.getMainPanel();
    }

    @Override
    public void setListener(final GameView.Listener listener) {
        this.listener = Objects.requireNonNull(listener, "listener can't be null");
    }

    private void apply(final GameViewState newState) {
        render(newState);
    }
}
