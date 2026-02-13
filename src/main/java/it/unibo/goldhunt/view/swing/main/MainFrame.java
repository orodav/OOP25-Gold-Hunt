package it.unibo.goldhunt.view.swing.main;

import java.awt.CardLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import it.unibo.goldhunt.view.api.GameController;
import it.unibo.goldhunt.view.api.ItemVisualRegistry;
import it.unibo.goldhunt.view.api.UIFactory;
import it.unibo.goldhunt.view.swing.screens.DifficultyPanel;
import it.unibo.goldhunt.view.swing.screens.EndPanel;
import it.unibo.goldhunt.view.swing.screens.MenuPanel;
import it.unibo.goldhunt.view.swing.screens.PlayingPanel;
import it.unibo.goldhunt.view.swing.screens.ShopPanel;

public class MainFrame {

    private final JPanel mainPanel;
    private final CardLayout layout;

    public MainFrame(UIFactory factory, GameController controller, ItemVisualRegistry itemRegistry, JLabel stateLabel) {

        this.layout = new CardLayout();
        this.mainPanel = factory.createPanel(layout);

        var menuPanel = new MenuPanel(factory, layout, mainPanel);
        var difficultyPanel = new DifficultyPanel(controller, factory, layout, mainPanel);
        var playingPanel = new PlayingPanel(factory, mainPanel, layout, itemRegistry);
        var endPanel = new EndPanel(factory, mainPanel, layout, stateLabel);
        var shopPanel = new ShopPanel();

        mainPanel.add(menuPanel, "MENU");
        mainPanel.add(difficultyPanel, "DIFFICULTY");
        mainPanel.add(playingPanel, "PLAYING");
        mainPanel.add(endPanel, "END");
        mainPanel.add(shopPanel, "SHOP");
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
