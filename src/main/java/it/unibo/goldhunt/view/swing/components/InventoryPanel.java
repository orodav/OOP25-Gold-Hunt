package it.unibo.goldhunt.view.swing.components;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.Objects;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;

import it.unibo.goldhunt.view.api.InventoryView;
import it.unibo.goldhunt.view.viewstate.InventoryItemViewState;
import it.unibo.goldhunt.view.viewstate.InventoryViewState;

/**
 * Swing implementation of {@link InventoryView}.
 */
public final class InventoryPanel extends JPanel implements InventoryView {

    private static final long serialVersionUID = 1L;

    private static final int FIVE = 5;
    private static final int FOUR = 4;
    private final JPanel root;
    private final JPanel itemsPanel;
    private transient Listener listener = type -> { };

    /**
     * Creates the inventory panel UI component.
     * 
     * <p>
     * The panel consists of a root {@link JPanel} with a {@link BorderLayout}
     * and titled border ("Inventory").
     * Items are displayed inside a scrollable grid layout to support a
     * variable number of inventory elements.
     */
    public InventoryPanel() {
        this.root = new JPanel(new BorderLayout());
        this.root.setBorder(new TitledBorder("Inventory"));
        this.itemsPanel = new JPanel(new GridLayout(0, FOUR, FIVE, FIVE));
        final JScrollPane scroll = new JScrollPane(itemsPanel);
        scroll.setBorder(null);
        this.root.add(scroll, BorderLayout.CENTER);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void render(final InventoryViewState state) {
        Objects.requireNonNull(state, "state can't be null");
        itemsPanel.removeAll();
        for (final InventoryItemViewState item : state.items()) {
            itemsPanel.add(buildButton(item));
        }
        itemsPanel.revalidate();
        itemsPanel.repaint();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JComponent component() {
        final JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.add(root, BorderLayout.CENTER);
        return wrapper;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setListener(final Listener listener) {
        this.listener = Objects.requireNonNull(listener, "listener can't be null");
    }

    private JButton buildButton(final InventoryItemViewState item) {
        final String label = item.name() + " x" + item.quantity();
        final JButton button = new JButton(label);
        button.setFocusable(false);
        button.setEnabled(item.usable());
        button.addActionListener(e -> {
            if (item.usable()) {
                listener.onUseItem(item.type());
            }
        });
        return button;
    }
}
