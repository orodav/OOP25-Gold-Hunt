package it.unibo.goldhunt.view.swing.screens;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Window;
import java.util.Objects;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import it.unibo.goldhunt.view.api.UIFactory;

/**
 * This class implements the main menu screen.
 */
public final class MenuPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    /**
     * Listener for menu interactions.
     */
    public interface Listener {

        /**
         * Invoked when the user clicks the "Start Game" button.
         */
        void onStartGame();
    }

    /**
     * Default no-operation listener to avoid null checks.
     */
    private static final Listener NO_OP_LISTENER = new Listener() {
        @Override
        public void onStartGame() { }
    };

    private transient Listener listener = NO_OP_LISTENER;

    /**
     * Builds the menu panel.
     *
     * @param factory the UI factory used to create components
     * @throws NullPointerException if factory is null
     */
    public MenuPanel(final UIFactory factory) {
        super(new BorderLayout());
        Objects.requireNonNull(factory);

        final JPanel centerPanel = factory.createPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

        final JLabel title = factory.createTitleLabel("GOLD HUNT");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        final JButton startButton = factory.createButton("Start Game");
        final JButton instructionsButton = factory.createButton("How to play");
        final JButton exitButton = factory.createButton("Exit");

        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        instructionsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        startButton.addActionListener(e -> this.listener.onStartGame());

        instructionsButton.addActionListener(e ->
            JOptionPane.showMessageDialog(
                this,
                "Collect gold while avoiding traps\n"
                + "Left click: reveal a cell\n"
                + "Right click: place a flag\n"
                + "Use the shop to buy items",
                "How to play",
                JOptionPane.INFORMATION_MESSAGE
            )
        );

        exitButton.addActionListener(e -> {
            final Window window = SwingUtilities.getWindowAncestor(this);
            if (window != null) {
                window.dispose();
            }
            System.exit(0);
        });

        centerPanel.add(Box.createVerticalGlue());
        centerPanel.add(title);
        centerPanel.add(Box.createVerticalStrut(40));
        centerPanel.add(startButton);
        centerPanel.add(Box.createVerticalStrut(15));
        centerPanel.add(instructionsButton);
        centerPanel.add(Box.createVerticalStrut(15));
        centerPanel.add(exitButton);
        centerPanel.add(Box.createVerticalStrut(40));

        final JLabel credits = factory.createSecondaryLabel(
            "Azzurra Quattrini - David Orosanu - Luca Galassi - Sara Quirici"
        );
        credits.setAlignmentX(Component.CENTER_ALIGNMENT);

        centerPanel.add(credits);
        centerPanel.add(Box.createVerticalGlue());

        add(centerPanel, BorderLayout.CENTER);
    }

    /**
     * Registers the listener that will receive menu events.
     *
     * @param listener the listener to register
     * @throws NullPointerException if listener is null
     */
    public void setListener(final Listener listener) {
        this.listener = Objects.requireNonNull(listener, "listener can't be null");
    }
}
