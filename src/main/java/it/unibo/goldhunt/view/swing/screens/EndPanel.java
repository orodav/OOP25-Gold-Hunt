package it.unibo.goldhunt.view.swing.screens;

import java.awt.BorderLayout;
import java.awt.Component;
import java.util.Objects;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import it.unibo.goldhunt.engine.api.LevelState;
import it.unibo.goldhunt.view.api.UIFactory;

/**
 * This class implements the end screen.
 */
public class EndPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    public interface Listener {

        /**
         * Invoked when the user wants to continue.
         */
        void onGoToShop();

        /**
         * Invoked when the user wants to return to the main menu.
         */
        void onBackToMenu();
    }

    private static final Listener NO_OP_LISTENER = new Listener() {
        @Override
        public void onGoToShop() {}
        @Override
        public void onBackToMenu() {}
    };

    private transient Listener listener = NO_OP_LISTENER;

    private final JLabel stateLabel;
    
    /**
     * Builds the end panel.
     * 
     * @param factory the UI factory used to create components
     * @throws NullPointerException if factory is null
     */
    public EndPanel(UIFactory factory) {

        super(new BorderLayout());
        Objects.requireNonNull(factory);

        final JPanel center = factory.createPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));

        this.stateLabel = factory.createTitleLabel("");
        this.stateLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.stateLabel.setHorizontalAlignment(SwingConstants.CENTER);

        final JButton shopButton = factory.createButton("SHOP");
        final JButton menuButton = factory.createButton("MENU");

        shopButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        menuButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        shopButton.addActionListener(e -> this.listener.onGoToShop());
        menuButton.addActionListener(e -> this.listener.onBackToMenu());

        center.add(Box.createVerticalGlue());
        center.add(this.stateLabel);
        center.add(Box.createVerticalStrut(30));
        center.add(shopButton);
        center.add(Box.createVerticalStrut(15));
        center.add(menuButton);
        center.add(Box.createVerticalGlue());

        add(center, BorderLayout.CENTER);
    }
        
    /**
     * Renders the end screen.
     * 
     * @param levelState the current level state
     * @throws NullPointerException if levelState is null
     */
    public void render (final LevelState levelState) {
        Objects.requireNonNull(levelState);

        switch (levelState) {
            case WON -> stateLabel.setText("YOU WON");
            case LOSS -> stateLabel.setText("GAME OVER");
            default -> stateLabel.setText("");
        }
    }

    /**
     * Registers the listener for end screen actions.
     * 
     * @param listener the listener to register
     * @throws NullPointerException if listener is null
     */
    public void setListener(final Listener listener) {
        this.listener = Objects.requireNonNull(listener);
    }
}
