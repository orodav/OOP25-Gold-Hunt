package it.unibo.goldhunt.view.swing.components;

import java.awt.GridLayout;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.swing.JPanel;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.goldhunt.engine.api.Position;
import it.unibo.goldhunt.view.api.GameView;
import it.unibo.goldhunt.view.api.ItemVisualRegistry;
import it.unibo.goldhunt.view.viewstate.CellViewState;
import it.unibo.goldhunt.view.viewstate.GameViewState;

/**
 * This class represents the {@link JPanel} responsible
 * for displaying the game board.
 */
public final class BoardPanel extends JPanel {
    private static final long serialVersionUID = 1L;

    @SuppressFBWarnings(
        value = "SE_TRANSIENT_FIELD_NOT_RESTORED",
        justification = "These components are not meant to be deserialized."
    )
    private transient Map<Position, CellButton> cellsByPos;
    private int currentSize = -1;
    private transient GameView.Listener listener;
    private final transient ItemVisualRegistry registry;

    /**
     * {@code BoardPanel}'s constructor. It creates a
     * {@code BoardPanel} using the given item visual registry.
     * 
     * @param registry the used item visual registry
     * @throws NullPointerException if {@code registry} is {@code null}
     */
    public BoardPanel(final ItemVisualRegistry registry) {
        this.registry = Objects.requireNonNull(registry);
        this.cellsByPos = new HashMap<>();
    }

    /**
     * Sets the board's listener for the user's interactions.
     * 
     * @param listener the listener to register
     */
    public void setListener(final GameView.Listener listener) {
        this.listener = listener;
        cellsByPos.values().forEach(b -> b.setListener(listener));
    }

    /**
     * Renders the board according to the provided view state.
     * 
     * @param state the current game view state
     * @throws NullPointerException if {@code state} is {@code null}
     */
    public void render(final GameViewState state) {
        Objects.requireNonNull(state);

        final int size = state.boardSize();
        if (size <= 0) {
            return;
        }

        if (size != currentSize) {
            rebuildGrid(size);
        }

        for (final CellViewState cellState : state.cells()) {
            final CellButton button = cellsByPos.get(cellState.pos());
            if (button != null) {
                button.render(cellState);
            }
        }

        repaint();
    }

    private void rebuildGrid(final int size) {
        removeAll();
        cellsByPos.clear();

        setLayout(new GridLayout(size, size, 0, 0));

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                final Position position = new Position(i, j);
                final CellButton cellButton = new CellButton(position, registry);
                if (listener != null) {
                    cellButton.setListener(listener);
                }
                cellsByPos.put(position, cellButton);
                add(cellButton);
            }
        }

        currentSize = size;
        revalidate();
        repaint();
    }

}
