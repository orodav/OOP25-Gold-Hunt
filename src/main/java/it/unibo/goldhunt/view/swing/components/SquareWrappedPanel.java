package it.unibo.goldhunt.view.swing.components;

import javax.swing.JPanel;

/**
 * This class models a wrapper panel that forces its content
 * to be displayed inside a centered square area.
 */
public class SquareWrappedPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    /**
     * {@code SquareWrappedPanel}'s constructor.
     * It creates a panel with no layout manager.
     */
    public SquareWrappedPanel() {
        super(null);
    }

    /**
     * Resized the first contained component and makes it
     * occupy the largest centered square inside the panel.
     */
    @Override
    public void doLayout() {
        if (getComponentCount() == 0) {
            return;
        }

        final var panel = getComponent(0);

        final int width = getWidth();
        final int height = getHeight();
        final int maxSize = Math.min(width, height);
        final int x = (width - maxSize) / 2;
        final int y = (height - maxSize) / 2;

        panel.setBounds(x, y, maxSize, maxSize);
    }

    /**
     * Sets the wrapper's content.
     * 
     * @param content the panel to wrap
     */
    public void setContent(final JPanel content) {
        removeAll();
        add(content);
        revalidate();
        repaint();
    }

}
