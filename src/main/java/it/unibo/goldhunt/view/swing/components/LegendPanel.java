package it.unibo.goldhunt.view.swing.components;

import java.awt.FlowLayout;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.plaf.DimensionUIResource;

import it.unibo.goldhunt.view.api.ItemVisualRegistry;

/**
 * A panel that displays the legend of all game items.
 * 
 * <p>
 * For each item provided by {@link ItemVisualRegistry},
 * the panel shows an icon and its name vertically.
 */
public final class LegendPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private static final int WIDTH = 150;
    private static final int ICON_SIZE = 25;
    private static final int HORIZONTAL_STRUT = 5;

    /**
     * Creates a legend panel using the given registry.
     * 
     * @param registry the item visual registry.
     */
    public LegendPanel(final ItemVisualRegistry registry) {
        super();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createTitledBorder("Legend"));
        setPreferredSize(new DimensionUIResource(WIDTH, 0));

        for (final String id : registry.getAllItemsID()) {

            final Icon icon = resizeIcon(registry.getIcon(id));
            final String tooltip = registry.getItemName(id);

            final JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
            row.setOpaque(false);

            row.setLayout(new BoxLayout(row, BoxLayout.X_AXIS));
            final JLabel iconLabel = (icon != null) ? new JLabel(icon) : new JLabel(registry.getGlyph(id));
            final JTextArea textArea = new JTextArea(tooltip);
            textArea.setLineWrap(true);
            textArea.setWrapStyleWord(true);
            textArea.setEditable(false);
            textArea.setFocusable(false);
            textArea.setOpaque(false);

            row.add(iconLabel);
            row.add(Box.createHorizontalStrut(HORIZONTAL_STRUT));
            row.add(textArea);
            add(row);
        }
    }

    /**
     * Resizes the given icon to a fixed state.
     * 
     * @param icon the given icon to resize.
     * @return the resized icon or {@code null} if the input is null.
     */
    private Icon resizeIcon(final Icon icon) {
        if (icon == null) {
            return null;
        }

        if (icon instanceof ImageIcon imageIcon) {
            final Image scale = imageIcon.getImage()
            .getScaledInstance(ICON_SIZE, ICON_SIZE, Image.SCALE_SMOOTH);
            return new ImageIcon(scale);
        }
        return icon;
    }

}
