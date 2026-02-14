package it.unibo.goldhunt.view.swing.screens;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.Objects;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;

import it.unibo.goldhunt.items.api.ItemTypes;
import it.unibo.goldhunt.view.api.ShopView;
import it.unibo.goldhunt.view.viewstate.ShopItemViewState;
import it.unibo.goldhunt.view.viewstate.ShopViewState;

/**
 * Swing implementation of {@link ShopView}.
 * 
 * <p>
 * Displays the current shop state, including the number of remaining
 * purchases and the list of available items.
 * The panel does not contain business logic, it delegates user
 * interactrions to the listener.
 */
public final class ShopPanel extends JPanel implements ShopView {

    private static final long serialVersionUID = 1L;
    private static final Listener NO_OP_LISTENER = new Listener() {

        /**
         * {@inheritDoc}
         */
        @Override public void onBuy(final ItemTypes type) { }

        /**
         * {@inheritDoc}
         */
        @Override public void onLeaveShop() { }
    };

    private transient Listener listener = NO_OP_LISTENER;

    private final JLabel remainingLabel;
    private final JPanel itemsPanel;
    private final JButton leaveButton;

    /**
     * Creates the shop panel UI component.
     */
    public ShopPanel() {
        super(new BorderLayout());
        this.setBorder(new TitledBorder("Shop"));

        final JPanel topBar = new JPanel(new BorderLayout());
        this.remainingLabel = new JLabel("Remaining purchases: 0");
        final JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        this.leaveButton = new JButton("Leave shop");
        this.leaveButton.setFocusable(false);
        this.leaveButton.addActionListener(e -> this.listener.onLeaveShop());
        right.add(this.leaveButton);
        topBar.add(this.remainingLabel, BorderLayout.WEST);
        topBar.add(right, BorderLayout.EAST);

        this.itemsPanel = new JPanel(new GridLayout(0, 3, 8, 8));
        final JScrollPane scroll = new JScrollPane(this.itemsPanel);
        scroll.setBorder(null);
        this.add(topBar, BorderLayout.NORTH);
        this.add(scroll, BorderLayout.CENTER);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void render(final ShopViewState state) {
        Objects.requireNonNull(state, "state can't be null");

        this.remainingLabel.setText("Remaining purchases: " + state.remainingPurchases());
        this.itemsPanel.removeAll();
        for (final ShopItemViewState item : state.items()) {
            this.itemsPanel.add(buildBuyButton(item));
        }
        this.itemsPanel.revalidate();
        this.itemsPanel.repaint();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JComponent component() {
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setListener(final Listener listener) {
        this.listener = Objects.requireNonNull(listener, "listener can't be null");
    }

    private JButton buildBuyButton(final ShopItemViewState item) {
        final String label = item.name() + " /" + item.price() + "g";
        final JButton b = new JButton(label);
        b.setFocusable(false);
        b.setEnabled(item.enabled());
        if (!item.enabled()) {
            b.setToolTipText(!item.affordable() ? "Not enough gold" : "No remaining purchases");
        } else {
            b.setToolTipText(null);
        }
        b.addActionListener(e -> {
            if (item.enabled()) {
                this.listener.onBuy(item.type());
            }
        });
        return b;
    }
}
