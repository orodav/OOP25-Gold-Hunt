package it.unibo.goldhunt.view.swing.screens;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import it.unibo.goldhunt.view.api.UIFactory;
import it.unibo.goldhunt.view.viewstate.GameViewState;

public class EndPanel extends JPanel {

    private final JLabel stateLabel;
    
    public EndPanel(UIFactory factory, JPanel mainPanel, CardLayout layout, JLabel stateLabel) {

        this.stateLabel = factory.createTitleLabel("");
        stateLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        stateLabel.setHorizontalAlignment(SwingConstants.CENTER);

        setLayout(new BorderLayout());

        JPanel center = factory.createPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));


        JButton shopButton = factory.createButton("SHOP");
        shopButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        shopButton.addActionListener(e -> {
            layout.show(mainPanel, "SHOP");
        });

        JButton backToMenu = factory.createButton("MENU");
        backToMenu.setAlignmentX(Component.CENTER_ALIGNMENT);
        backToMenu.addActionListener(e -> {
            layout.show(mainPanel, "MENU");
        });

        center.add(Box.createVerticalGlue());
        center.add(stateLabel);
        center.add(Box.createVerticalStrut(30));
        center.add(shopButton);
        center.add(Box.createVerticalStrut(15));
        center.add(backToMenu);
        center.add(Box.createVerticalGlue());

        add(center, BorderLayout.CENTER);
    }

    public void render (GameViewState viewState) {
        switch (viewState.levelState()) {
            case WON -> stateLabel.setText("YOU WON");
            case LOSS -> stateLabel.setText("GAME OVER");
            default -> stateLabel.setText("");
        }
    }
}
