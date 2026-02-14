package it.unibo.goldhunt.view.swing.main;

import java.awt.CardLayout;
import java.util.Objects;

import javax.swing.JPanel;

import it.unibo.goldhunt.view.api.ItemVisualRegistry;
import it.unibo.goldhunt.view.api.UIFactory;
import it.unibo.goldhunt.view.swing.screens.DifficultyPanel;
import it.unibo.goldhunt.view.swing.screens.EndPanel;
import it.unibo.goldhunt.view.swing.screens.MenuPanel;
import it.unibo.goldhunt.view.swing.screens.PlayingPanel;
import it.unibo.goldhunt.view.swing.screens.ShopPanel;
import it.unibo.goldhunt.view.viewstate.ScreenType;

/**
 * This class implements a root Swing container that hosts
 * all screens using a {@link CardLayout}. 
 */
public class MainFrame {

    private final JPanel root;
    private final CardLayout layout;

    private final MenuPanel menuPanel;
    private final DifficultyPanel difficultyPanel;
    private final PlayingPanel playingPanel;
    private final ShopPanel shopPanel;
    private final EndPanel endPanel;

    /**
     * Builds the main Swing container and register all screens in a CardLayout.
     * 
     * @param factory UI factory used to create components
     * @param itemRegistry registry for item visuals
     * @throws NullPointerException if any argument is null
     */
    public MainFrame(UIFactory factory, ItemVisualRegistry itemRegistry) {

        Objects.requireNonNull(factory);
        Objects.requireNonNull(itemRegistry);

        this.layout = new CardLayout();
        this.root = factory.createPanel(this.layout);

        this.menuPanel = new MenuPanel(factory);
        this.difficultyPanel = new DifficultyPanel(factory);
        this.playingPanel = new PlayingPanel(factory, itemRegistry);
        this.endPanel = new EndPanel(factory);
        this.shopPanel = new ShopPanel();

        this.root.add(this.menuPanel, ScreenType.MENU.name());
        this.root.add(this.difficultyPanel, ScreenType.DIFFICULTY.name());
        this.root.add(this.playingPanel, ScreenType.PLAYING.name());
        this.root.add(this.endPanel, ScreenType.END.name());
        this.root.add(this.shopPanel, ScreenType.SHOP.name());

        show(ScreenType.MENU);
    }

    /**
     * Returns the root panel to be set as the content pane of the JFrame.
     * 
     * @return the root container panel
     */
    public JPanel getMainPanel() {
        return this.root;
    }

    /**
     * Shows the requested screen.
     * 
     * @param screen the screen to display
     */
    public void show(final ScreenType screen) {
        Objects.requireNonNull(screen);
        this.layout.show(this.root, screen.name());
    }

    public MenuPanel getMenuPanel() {
        return this.menuPanel;
    }

    public DifficultyPanel getDifficultyPanel() {
        return this.difficultyPanel;
    }

    public PlayingPanel getPlayingPanel() {
        return this.playingPanel;
    }

    public EndPanel getEndPanel() {
        return this.endPanel;
    }

    public ShopPanel getShopPanel() {
        return this.shopPanel;
    }
}

