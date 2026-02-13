package it.unibo.goldhunt.view.swing.components;

import java.awt.Color;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Objects;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.SwingUtilities;

import it.unibo.goldhunt.engine.api.Position;
import it.unibo.goldhunt.view.api.GameView;
import it.unibo.goldhunt.view.api.ItemVisualRegistry;
import it.unibo.goldhunt.view.viewstate.CellViewState;

/**
 * This class is a personalized Swing component and it
 * represents a single cell of the game board.
 */
public final class CellButton extends JButton {

    private final Position position;
    private GameView.Listener listener;
    private String lastStyleKey = "";
    private final ItemVisualRegistry registry;

    /**
     * {@code CellButton}'s constructor. It creates a
     * button associated to a specific board position.
     * 
     * @param position the cell's position in the board
     * @param registry the used item visual registry
     */
    public CellButton (final Position position, final ItemVisualRegistry registry) {
        this.position = Objects.requireNonNull(position);
        this.registry = Objects.requireNonNull(registry);

        setFocusable(false);
        setOpaque(true);
        setMargin(new Insets(0, 0, 0, 0));

        addMouseListener(new MouseAdapter() { 
            @Override
            public void mousePressed(final MouseEvent e) {
                if (listener == null) {
                    return;
                }
                if (SwingUtilities.isLeftMouseButton(e)) {
                    listener.onReveal(position);
                } else if (SwingUtilities.isRightMouseButton(e)) {
                    if ("cell.hidden".equals(lastStyleKey) || "cell.flagged".equals(lastStyleKey)) {
                    listener.onToggleFlag(position);
                    }
                }
            }
        });
    }

    /**
     * Sets the cell's listener for the user's interactions.
     * 
     * @param listener the listener to register
     */
    public void setListener(final GameView.Listener listener) {
        this.listener = listener;
    }

    /**
     * Returns the cell's position in the board.
     * 
     * @return the associated position
     */
    public Position getPosition() {
        return this.position;
    }

    /**
     * Renders the cell according to the provided view state.
     * 
     * @param state the current game view state
     * @throws NullPointerException if {@code state} is {@code null}
     */
    public void render(final CellViewState state) {
        Objects.requireNonNull(state);
        if (!state.pos().equals(this.position)) {
            return;
        }

        setText("");
        setIcon(null);
        setForeground(Color.BLACK);

        if ("cell.player".equals(state.styleKey())) {
            renderPlayer(state);
        }
        else if (!state.revealed()) {
            renderHidden(state);
        }
        else {
            renderRevealed(state);
        }

        applyStyle(state.styleKey());
        revalidate();
        repaint();
    }

    private void renderPlayer (final CellViewState state) {
        final String symbol = state.symbol();
        if ("T".equals(symbol)) {
            setIcon(registry.getIcon("T"));
            return;
        }
        if (registry.getAllItemsID().contains("PLAYER")) {
            setIcon(registry.getIcon("PLAYER"));
        }
    }
    
    private void renderHidden (final CellViewState state) {
        if (state.flagged()) {
            if (registry.getAllItemsID().contains("F")) {
                setIcon(registry.getIcon("F"));
            }
        }

        final String symbol = state.symbol();
        if ("E".equals(symbol) && registry.getAllItemsID().contains("E")) {
            setIcon(registry.getIcon("E"));
        }
    }

    private void renderRevealed(final CellViewState state) {
        final String symbol = state.symbol();

        if (symbol != null && registry.getAllItemsID().contains(symbol)) {
            setIcon(registry.getIcon(symbol));
            return;
        }

        if (state.adjacentTraps() > 0) {
            setText(String.valueOf(state.adjacentTraps()));
        }
    }

    private void applyStyle (final String styleKey) {
        Objects.requireNonNull(styleKey);
        if (styleKey.equals(lastStyleKey)) {
            return;
        }

        switch (styleKey) {
            case "cell.hidden" -> {
                setBackground(new Color(175, 134, 80));
                setBorder(BorderFactory.createRaisedBevelBorder());
            }

            case "cell.flagged" -> {
                setBackground(new Color(175, 134, 80));
                setBorder(BorderFactory.createRaisedBevelBorder());
            }

            case "cell.revealed" -> {
                setBackground(new Color(96, 96, 96));
                setBorder(BorderFactory.createLoweredBevelBorder());
            }

            case "cell.player" -> {
                setBackground(new Color(96, 96, 96));
                setBorder(BorderFactory.createLineBorder(Color.BLUE, 2));
            }

            default -> setBackground(Color.RED);

        }

        lastStyleKey = styleKey;
    }
}
