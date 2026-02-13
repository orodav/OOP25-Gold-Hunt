package it.unibo.goldhunt.view.impl;

import java.awt.LayoutManager;
import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import it.unibo.goldhunt.view.api.UIFactory;

public class SwingUIFactory implements UIFactory {

    @Override
    public JFrame createFrame(final String title) {
        final JFrame frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        return frame;
    }

    @Override
    public JPanel createPanel() {
        return new JPanel();
    }

    @Override
    public JPanel createPanel(final LayoutManager manager) {
        return new JPanel(manager);
    }

    @Override
    public JLabel createStandardLabel(final String text) {
        return new JLabel(text);
    }

    @Override
    public JLabel createSecondaryLabel(final String text) {
        return new JLabel(text);
    }

    @Override
    public JLabel createTitleLabel(final String text) {
        return new JLabel(text);
    }

    @Override
    public JButton createButton(final String text) {
        return new JButton(text);
    }

    @Override
    public JButton createIconButton(String icon) {
        JButton b = new JButton(loadIcon(icon));
        b.setBorderPainted(false);
        b.setContentAreaFilled(false);
        b.setFocusPainted(false);
        return b;
    }

    @Override
    public Icon loadIcon(final String iconName) {
        final URL resource = getClass().getResource("/" + iconName);

        if (resource == null) {
            throw new IllegalArgumentException("Icon not found: " + iconName);
        }

        return new ImageIcon(resource);
    }

}
