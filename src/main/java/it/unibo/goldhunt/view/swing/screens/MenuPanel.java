package it.unibo.goldhunt.view.swing.screens;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Window;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import it.unibo.goldhunt.view.api.UIFactory;

public class MenuPanel extends JPanel {

    public MenuPanel(UIFactory factory, CardLayout layout, JPanel mainPanel) {

        super(new BorderLayout());

        JPanel centerPanel = factory.createPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

        JLabel title = factory.createTitleLabel("GOLD HUNT");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        centerPanel.add(Box.createVerticalStrut(40));
        centerPanel.add(title);
        centerPanel.add(Box.createVerticalStrut(40));

        JButton startButton = factory.createButton("Start Game");
        startButton.addActionListener(e -> {
            layout.show(mainPanel, "DIFFICULTY");
        });

        JButton instructionsButton = factory.createButton("How to play");
        instructionsButton.addActionListener(e -> JOptionPane.showMessageDialog(
            this, 
            "Collect gold while avoiding traps\n" +
            "Left click: reveal a cell\n" +
            "Right click: place a flag\n" +
            "Use the shop to buy items",
            "How to play",
            JOptionPane.INFORMATION_MESSAGE)
        );

        JButton exitButton = factory.createButton("Exit");
        exitButton.addActionListener(e -> {
            Window window = SwingUtilities.getWindowAncestor(exitButton);
            if (window != null) {
                window.dispose();
            }
            System.exit(0);
        });

        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        instructionsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        centerPanel.add(startButton);
        centerPanel.add(Box.createVerticalStrut(15));
        centerPanel.add(instructionsButton);
        centerPanel.add(Box.createVerticalStrut(15));
        centerPanel.add(exitButton);

        centerPanel.add(Box.createVerticalStrut(40));

        JLabel credits = factory.createSecondaryLabel(
                "Azzurra Quattrini - David Orosanu - Luca Galassi - Sara Quirici"
        );
        credits.setAlignmentX(Component.CENTER_ALIGNMENT);

        centerPanel.add(credits);

        add(centerPanel, BorderLayout.CENTER);
    }
}
