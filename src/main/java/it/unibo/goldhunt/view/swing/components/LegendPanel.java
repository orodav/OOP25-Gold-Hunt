package it.unibo.goldhunt.view.swing.components;

import java.awt.FlowLayout;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.plaf.DimensionUIResource;

import it.unibo.goldhunt.view.api.ItemVisualRegistry;

public final class LegendPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private static final int WIDTH = 150;
    private static final int ICON_SIZE = 25;

    public LegendPanel(final ItemVisualRegistry registry) {
        super();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createTitledBorder("Legend"));
        setPreferredSize(new DimensionUIResource(WIDTH, 0));

        for (final String id : registry.getAllItemsID()) {

            final Icon icon = resizeIcon(registry.getIcon(id));
            final String tooltip = registry.getItemName(id);

            final JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 2));
            row.setOpaque(false);

            final JLabel iconLabel = (icon != null) ? new JLabel(icon) : new JLabel(registry.getGlyph(id));
            final JLabel tooltipLabel = new JLabel(tooltip);

            row.add(iconLabel);
            row.add(tooltipLabel);
            add(row);
        }
    }

    private Icon resizeIcon(final Icon icon) {
        if (icon == null) {
            return null;
        }

        if (icon instanceof ImageIcon imageIcon) {
            Image scale = imageIcon.getImage()
            .getScaledInstance(ICON_SIZE, ICON_SIZE, Image.SCALE_SMOOTH);
            return new ImageIcon(scale);
        }
        return icon;
    }

}
