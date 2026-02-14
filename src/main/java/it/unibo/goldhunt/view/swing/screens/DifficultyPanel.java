package it.unibo.goldhunt.view.swing.screens;

import java.awt.BorderLayout;
import java.util.Objects;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import it.unibo.goldhunt.configuration.api.Difficulty;
import it.unibo.goldhunt.view.api.UIFactory;

/**
 * This class implements the difficulty selection screen.
 */
public final class DifficultyPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    private static final int VERTICAL_GAP_LARGE = 30;
    private static final int VERTICAL_GAP_MEDIUM = 15;

    /**
     * Default no-operation listener to avoid null checks.
     */
    private static final Listener NO_OP_LISTENER = new Listener() {

        @Override 
        public void onDifficultySelected(final Difficulty difficulty) { }

        @Override 
        public void onBackToMenu() { }
    };

    private transient Listener listener = NO_OP_LISTENER;

    /**
     * Builds the difficulty selection panel.
     *
     * @param factory the UI factory used to create components
     * @throws NullPointerException if {@code factory} is null
     */
    public DifficultyPanel(final UIFactory factory) {

        super(new BorderLayout());
        Objects.requireNonNull(factory);

        final JPanel center = factory.createPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));

        final JLabel title = factory.createStandardLabel("SELECT DIFFICULTY");
        title.setAlignmentX(CENTER_ALIGNMENT);
        title.setHorizontalAlignment(SwingConstants.CENTER);

        final JButton easy = factory.createButton("EASY");
        final JButton medium = factory.createButton("MEDIUM");
        final JButton hard = factory.createButton("HARD");
        final JButton backToMenu = factory.createButton("MENU");

        easy.setAlignmentX(CENTER_ALIGNMENT);
        medium.setAlignmentX(CENTER_ALIGNMENT);
        hard.setAlignmentX(CENTER_ALIGNMENT);
        backToMenu.setAlignmentX(CENTER_ALIGNMENT);

        easy.addActionListener(e ->
            this.listener.onDifficultySelected(Difficulty.EASY)
        );

        medium.addActionListener(e ->
            this.listener.onDifficultySelected(Difficulty.MEDIUM)
        );

        hard.addActionListener(e ->
            this.listener.onDifficultySelected(Difficulty.HARD)
        );

        backToMenu.addActionListener(e ->
            this.listener.onBackToMenu()
        );

        center.add(Box.createVerticalGlue());
        center.add(title);
        center.add(Box.createVerticalStrut(VERTICAL_GAP_LARGE));
        center.add(easy);
        center.add(Box.createVerticalStrut(VERTICAL_GAP_MEDIUM));
        center.add(medium);
        center.add(Box.createVerticalStrut(VERTICAL_GAP_MEDIUM));
        center.add(hard);
        center.add(Box.createVerticalStrut(VERTICAL_GAP_LARGE));
        center.add(backToMenu);
        center.add(Box.createVerticalGlue());

        add(center, BorderLayout.CENTER);
    }

    /**
     * Registers the listener that will receive difficulty events.
     *
     * @param listener the listener to register
     * @throws NullPointerException if {@code listener} is null
     */
    public void setListener(final Listener listener) {
        this.listener = Objects.requireNonNull(listener, "listener can't be null");
    }

    /**
     * Listener for difficulty screen interactions.
     */
    public interface Listener {

        /**
         * Invoked when a difficulty is selected.
         *
         * @param difficulty the selected difficulty
         */
        void onDifficultySelected(Difficulty difficulty);

        /**
         * Invoked when the user wants to return to the main menu.
         */
        void onBackToMenu();
    }
}
