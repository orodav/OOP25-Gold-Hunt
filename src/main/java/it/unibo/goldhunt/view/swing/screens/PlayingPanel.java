package it.unibo.goldhunt.view.swing.screens;

import java.awt.BorderLayout;
import java.awt.CardLayout;

import javax.swing.JPanel;

import it.unibo.goldhunt.view.api.ItemVisualRegistry;
import it.unibo.goldhunt.view.api.UIFactory;
import it.unibo.goldhunt.view.swing.components.BoardPanel;
import it.unibo.goldhunt.view.swing.components.HudPanel;
import it.unibo.goldhunt.view.swing.components.InventoryPanel;
import it.unibo.goldhunt.view.swing.components.LegendPanel;

public class PlayingPanel extends JPanel {

    private final ItemVisualRegistry itemRegistry;

    public PlayingPanel(UIFactory factory, JPanel mainPanel, CardLayout layout, ItemVisualRegistry itemRegistry) {

        this.itemRegistry = itemRegistry;

        setLayout(new BorderLayout());

        final var legendPanel = new LegendPanel(this.itemRegistry);
        legendPanel.setLayout(new BorderLayout());
        add(legendPanel, BorderLayout.EAST);

        final var boardPanel = new BoardPanel(this.itemRegistry);
        boardPanel.setLayout(new BorderLayout());
        add(boardPanel, BorderLayout.CENTER);

        final var inventoryPanel = new InventoryPanel();
        inventoryPanel.setLayout(new BorderLayout());
        add(inventoryPanel, BorderLayout.WEST);

        final var hudPanel = new HudPanel();
        hudPanel.setLayout(new BorderLayout());
        add(hudPanel, BorderLayout.SOUTH);

        final var backToMenu = factory.createButton("MENU");
        backToMenu.addActionListener(e -> {
            layout.show(mainPanel, "MENU");
        });
    }
}
