package it.unibo.goldhunt.view.swing.components;

import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.plaf.DimensionUIResource;

import it.unibo.goldhunt.view.api.ItemVisualRegistry;

public class LegendPanel extends JPanel {

    private static final int WIDTH = 150;

    public LegendPanel(ItemVisualRegistry registry) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createTitledBorder("Legend"));
        setPreferredSize(new DimensionUIResource(WIDTH, 0));

        for (String id : registry.getAllItemsID()) {
            
            Icon icon = registry.getIcon(id);
            String tooltip = registry.getItemName(id);

            JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 2));
            row.setOpaque(false);

            JLabel iconLabel = (icon != null) ? new JLabel(icon) : new JLabel(registry.getGlyph(id));
            final JLabel tooltipLabel = new JLabel(tooltip);

            row.add(iconLabel);
            row.add(tooltipLabel);
            add(row);
        }
    }

}
