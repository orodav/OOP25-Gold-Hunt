package it.unibo.goldhunt.view.swing.components;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.AbstractButton;
import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 * This class is an implementation of {@link Icon}.
 * It automatically scales an {@link Image} to fit inside the available
 * area of a Swing component. 
 */
public class ScaledIcon implements Icon {

    private static final int NOMINAL_SIZE = 16;

    private final BufferedImage image;
    private final int padding;

    /**
     * {@code ScaledIcon}'s constructor. It creates a scaled icon.
     * 
     * @param image the image to render
     * @param padding extra space (in pixels) kept between the image
     *     and the component edges.
     */
    public ScaledIcon(final Image image, final int padding) {
        this.image = toBufferedImage(image);
        this.padding = Math.max(0, padding);
    }

    /**
     * Paints the image scaled to the largest possible
     * centered square inside the component.
     */
    @Override
    public void paintIcon(final Component c, final Graphics g,
                            final int x, final int y) {
        if (image == null) {
            return;
        }

        final Insets insets = (c instanceof AbstractButton b)
            ? b.getInsets()
            : new Insets(0, 0, 0, 0);

        final int availW = Math.max(1, c.getWidth() - insets.left - insets.right - 2 * padding);
        final int availH = Math.max(1, c.getHeight() - insets.top - insets.bottom - 2 * padding);
        final int size = Math.min(availW, availH);

        final int drawX = insets.left + padding + (availW - size) / 2;
        final int drawY = insets.top + padding + (availH - size) / 2;

        final Graphics2D g2 = (Graphics2D) g.create();
        try {
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2.setRenderingHint(RenderingHints.KEY_RENDERING,
                    RenderingHints.VALUE_RENDER_QUALITY);
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            g2.drawImage(image, drawX, drawY, size, size, null);
        } finally {
            g2.dispose();
        }
    }

    /**
     * Returns a nominal width for layout managers.
     * The actual rendered size depends on the component.
     *
     * @return constant value (16)
     */
    @Override
    public int getIconWidth() {
        return NOMINAL_SIZE;
    }

    /**
     * Returns a nominal height for layout managers.
     * The actual rendered size depends on the component.
     *
     * @return constant value (16)
     */
    @Override
    public int getIconHeight() {
        return NOMINAL_SIZE;
    }

    private static BufferedImage toBufferedImage(final Image img) {
        if (img == null) {
            return null;
        }

        final Image loaded = new ImageIcon(img).getImage();
        final int width = loaded.getWidth(null);
        final int height = loaded.getHeight(null);
        if (width <= 0 || height <= 0) {
            return null;
        }

        final BufferedImage copy = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        final Graphics2D g2 = copy.createGraphics();

        try {
            g2.drawImage(loaded, 0, 0, null);
        } finally {
            g2.dispose();
        }

        return copy;
    }
}
