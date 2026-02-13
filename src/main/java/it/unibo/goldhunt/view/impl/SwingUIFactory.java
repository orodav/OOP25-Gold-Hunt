package it.unibo.goldhunt.view.impl;

import java.awt.LayoutManager;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import it.unibo.goldhunt.view.api.UIFactory;

/**
 * This class is the implementation of {@link UIFactory}.
 */
public final class SwingUIFactory implements UIFactory {

    private static final String NO_ICON_FOUND = "Icon not found in the classpath: ";

    private final Map<String, Icon> iconCache = new HashMap<>();

    /**
     * {@inheritDoc}
     */
    @Override
    public JFrame createFrame(final String title) {
        final JFrame frame = new JFrame(title);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);

        return frame;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JPanel createPanel() {
        return new JPanel();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JPanel createPanel(final LayoutManager manager) {
        return new JPanel(manager);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JLabel createStandardLabel(final String text) {
        return new JLabel(text);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JLabel createSecondaryLabel(final String text) {
        return new JLabel(text);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JLabel createTitleLabel(final String text) {
        return new JLabel(text);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JButton createButton(final String text) {
        return new JButton(text);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JButton createIconButton(final String iconName) {
        final JButton button = new JButton(loadIcon(iconName));

        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setFocusable(false);

        return button;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Icon loadIcon(final String iconName) {
        Objects.requireNonNull(iconName);

        final String path = iconName.startsWith("/") ? iconName : "/" + iconName;

        return iconCache.computeIfAbsent(path, p -> {
            final URL resource = getClass().getResource(p);
            if (resource == null) {
                throw new IllegalArgumentException(NO_ICON_FOUND + p);
            }
            return new ImageIcon(resource);
        });
    }

}
