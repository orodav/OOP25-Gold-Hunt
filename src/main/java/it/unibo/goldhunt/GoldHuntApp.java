package it.unibo.goldhunt;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import it.unibo.goldhunt.configuration.api.Difficulty;
import it.unibo.goldhunt.root.GameFactory;
import it.unibo.goldhunt.root.GameSession;
import it.unibo.goldhunt.view.api.GameController;
import it.unibo.goldhunt.view.api.ItemVisualRegistry;
import it.unibo.goldhunt.view.api.UIFactory;
import it.unibo.goldhunt.view.impl.GameControllerImpl;
import it.unibo.goldhunt.view.impl.ItemRegistry;
import it.unibo.goldhunt.view.impl.SwingUIFactory;
import it.unibo.goldhunt.view.impl.ViewStateMapperImpl;
import it.unibo.goldhunt.view.swing.main.MainFrame;
import it.unibo.goldhunt.view.swing.main.SwingGameView;

public final class GoldHuntApp {

    /**
     * Utility class: no instances allowd.
     */
    private GoldHuntApp() { }

    /**
     * Starts the Swing application.
     * 
     * <p>
     * This method creates an initial game session, 
     * builds the controller and the Swing view,
     * shows the main window, and performs an initial render.
     */
    public static void start() {
        SwingUtilities.invokeLater(() -> {
            final UIFactory uiFactory = new SwingUIFactory();
            final GameFactory gameFactory = new GameFactory();
            final GameSession initialSession = gameFactory.newGame(Difficulty.EASY);

            final var mapper = new ViewStateMapperImpl();
            final GameController controller = new GameControllerImpl(
                gameFactory, initialSession, mapper
            );
            final ItemVisualRegistry itemRegistry = new ItemRegistry(uiFactory);
            final JLabel stateLabel = new JLabel();
            final MainFrame mainFrame = new MainFrame(uiFactory, itemRegistry, stateLabel);
            final SwingGameView view = new SwingGameView(mainFrame, controller);
            view.bind();

            final JFrame frame = uiFactory.createFrame("Gold Hunt");
            frame.setContentPane(view.component());
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            view.render(controller.state());
        });
    }
}
