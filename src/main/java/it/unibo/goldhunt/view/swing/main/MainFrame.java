package it.unibo.goldhunt.view.swing.main;

import java.awt.CardLayout;
import java.util.Objects;

import javax.swing.JLabel;
import javax.swing.JPanel;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
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
public final class MainFrame {

    private static final String EI_EXPOSE_REP = "EI_EXPOSE_REP";
    private static final String JUSTIFICATION_ROOT = "Root Swing container is intentionally exposed for JFrame composition";
    private static final String JUSTIFICATION_SCREENS = "Swing screen panels are intentionally exposed for controller wiring";

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
     * @param stateLabel label used to display screens
     * @throws NullPointerException if any argument is null
     */
    public MainFrame(final UIFactory factory, final ItemVisualRegistry itemRegistry, final JLabel stateLabel) {

        Objects.requireNonNull(factory);
        Objects.requireNonNull(itemRegistry);
        Objects.requireNonNull(stateLabel);

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
     * Returns the root container panel.
     * 
     * @return the root container panel
     */
    @SuppressFBWarnings(
        value = EI_EXPOSE_REP,
        justification = JUSTIFICATION_ROOT
    )
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

    /**
     * Returns menu panel.
     * 
     * @return {@link MenuPanel}
     */
    @SuppressFBWarnings(
        value = EI_EXPOSE_REP,
        justification = JUSTIFICATION_SCREENS
    )
    public MenuPanel getMenuPanel() {
        return this.menuPanel;
    }

    /**
     * Returns difficulty panel.
     * 
     * @return {@link DifficultyPanel}
     */
    @SuppressFBWarnings(
        value = EI_EXPOSE_REP,
        justification = JUSTIFICATION_SCREENS
    )
    public DifficultyPanel getDifficultyPanel() {
        return this.difficultyPanel;
    }

    /**
     * Returns playing panel.
     * 
     * @return {@link PlayingPanel}
     */
    @SuppressFBWarnings(
        value = EI_EXPOSE_REP,
        justification = JUSTIFICATION_SCREENS
    )
    public PlayingPanel getPlayingPanel() {
        return this.playingPanel;
    }

    /**
     * Returns end panel.
     * 
     * @return {@link EndPanel}
     */
    @SuppressFBWarnings(
        value = EI_EXPOSE_REP,
        justification = JUSTIFICATION_SCREENS
    )
    public EndPanel getEndPanel() {
        return this.endPanel;
    }

    /**
     * Returns shop panel.
     * 
     * @return {@link ShopPanel}
     */
    @SuppressFBWarnings(
        value = EI_EXPOSE_REP,
        justification = JUSTIFICATION_SCREENS
    )
    public ShopPanel getShopPanel() {
        return this.shopPanel;
    }
}
