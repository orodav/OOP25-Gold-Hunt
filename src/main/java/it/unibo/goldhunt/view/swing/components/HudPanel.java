package it.unibo.goldhunt.view.swing.components;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.Objects;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import it.unibo.goldhunt.view.api.HudView;
import it.unibo.goldhunt.view.viewstate.HudViewState;

/**
 * Swing HUD panel: displays basic player/session info (level, lives, gold).
 */
public final class HudPanel extends JPanel implements HudView {

    private static final int SIX = 6; 
    private static final long serialVersionUID = 1L;
    private final JLabel levelLabel;
    private final JLabel livesLabel;
    private final JLabel goldLabel;

    /**
     * Creates the HUD panel UI component.
     * 
     * <p>
     * The panel uses a {@link BorderLayout} and displays core game statistics.
     * Labels are initialized with placeholder values and are meant to be
     * dynamically updated according to the current game state.
     */
    public HudPanel() {
        super(new BorderLayout());
        this.setBorder(new EmptyBorder(SIX, 8, SIX, 8));
        final JPanel stats = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0));
        this.levelLabel = new JLabel("Level: -");
        this.livesLabel = new JLabel("Lives: -");
        this.goldLabel = new JLabel("Gold: 0");
        stats.add(levelLabel);
        stats.add(livesLabel);
        stats.add(goldLabel);
        this.add(stats, BorderLayout.WEST);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void render(final HudViewState state) {
        Objects.requireNonNull(state, "state can't be null");
        this.levelLabel.setText("Level: " + state.levelNumber());
        this.livesLabel.setText("Lives: " + state.lives());
        this.goldLabel.setText("Gold: " + state.gold());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JComponent component() {
        return this;
    }
}
