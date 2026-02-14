package it.unibo.goldhunt.view.swing.components;

import java.awt.Color;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Objects;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
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

    private static final int NOT_REVEALED_R = 175;
    private static final int NOT_REVEALED_G = 134;
    private static final int NOT_REVEALED_B = 80;
    private static final int REVEALED_RGB = 96;
    private static final String EXIT = "E";
    private static final String PLAYER = "Q";
    private static final String FLAG = "F";
    private static final String STYLE_HIDDEN = "cell.hidden";
    private static final String STYLE_FLAGGED = "cell.flagged";
    private static final String STYLE_REVEALED = "cell.revealed";
    private static final String STYLE_PLAYER = "cell.player";
    private static final int ICON_PADDING = 2;

    private static final long serialVersionUID = 1L;

    private final transient Position position;
    private transient GameView.Listener listener;
    private String lastStyleKey = "";
    private final transient ItemVisualRegistry registry;
    private boolean lastRevealed;

    /**
     * {@code CellButton}'s constructor. It creates a
     * button associated to a specific board position.
     * 
     * @param position the cell's position in the board
     * @param registry the used item visual registry
     */
    public CellButton(final Position position, final ItemVisualRegistry registry) {
        this.position = Objects.requireNonNull(position);
        this.registry = Objects.requireNonNull(registry);

        setFocusable(false);
        setOpaque(true);
        setMargin(new Insets(0, 0, 0, 0));

        setHorizontalAlignment(CENTER);
        setVerticalAlignment(CENTER);
        setHorizontalTextPosition(CENTER);
        setVerticalTextPosition(CENTER);

        addActionListener(e -> {
            if (listener != null) {
                listener.onReveal(position);
            }
        });

        addMouseListener(new MouseAdapter() { 

            private void handleRightClick(final MouseEvent e) {
                if (listener == null) {
                    return;
                }
                if (!lastRevealed) {
                    listener.onToggleFlag(position);
                    e.consume();
                }
            }

            @Override
            public void mousePressed(final MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e) || e.isPopupTrigger()) {
                    handleRightClick(e);
                }
            }

            @Override
            public void mouseReleased(final MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e) || e.isPopupTrigger()) {
                    handleRightClick(e);
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

        if (STYLE_PLAYER.equals(state.styleKey())) {
            renderPlayer();
        } else if (!state.revealed()) {
            renderHidden(state);
        } else {
            renderRevealed(state);
        }

        this.lastRevealed = state.revealed();
        applyStyle(state.styleKey());
        repaint();
    }

    private void setScaledIconFromRegistry(final String id) {
    if (id == null) {
        setIcon(null);
        return;
    }

    if (!registry.getAllItemsID().contains(id)) {
        setIcon(null);
        return;
    }

    try {
        final var icon = registry.getIcon(id); // ora è "safe" perché l'id esiste
        if (icon instanceof ImageIcon ii && ii.getImage() != null) {
            setIcon(new ScaledIcon(ii.getImage(), ICON_PADDING));
        } else {
            setIcon(icon);
        }
    } catch (final IllegalArgumentException e) {
        // ulteriore sicurezza: se il registry cambia comportamento o c'è mismatch
        setIcon(null);
    }
}

    private void renderPlayer() {
        setScaledIconFromRegistry(PLAYER);
    }

    private void renderHidden(final CellViewState state) {
        final String symbol = state.symbol();

        if (EXIT.equals(symbol)) {
            setScaledIconFromRegistry(EXIT);
            return;
        }

        if (state.flagged()) {
            setScaledIconFromRegistry(FLAG);
        }
    }

    private void renderRevealed(final CellViewState state) {
        final String symbol = state.symbol();

        if (symbol != null) {
            setScaledIconFromRegistry(symbol);
            if (getIcon() != null) {
                return;
            }
        }

        if (state.adjacentTraps() > 0) {
            setText(String.valueOf(state.adjacentTraps()));
        }
    }

    private void applyStyle(final String styleKey) {
        Objects.requireNonNull(styleKey);

        if (styleKey.equals(lastStyleKey)) {
            return;
        }

        switch (styleKey) {
            case STYLE_HIDDEN, STYLE_FLAGGED -> {
                setBackground(new Color(NOT_REVEALED_R, NOT_REVEALED_G, NOT_REVEALED_B));
                setBorder(BorderFactory.createRaisedBevelBorder());
            }

            case STYLE_REVEALED -> {
                setBackground(new Color(REVEALED_RGB, REVEALED_RGB, REVEALED_RGB));
                setBorder(BorderFactory.createLoweredBevelBorder());
            }

            case STYLE_PLAYER -> {
                setBackground(new Color(REVEALED_RGB, REVEALED_RGB, REVEALED_RGB));
                setBorder(BorderFactory.createLineBorder(Color.BLUE, 2));
            }

            default -> setBackground(Color.RED);
        }

        lastStyleKey = styleKey;

    }

}
