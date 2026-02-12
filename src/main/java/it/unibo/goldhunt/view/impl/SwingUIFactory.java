package it.unibo.goldhunt.view.impl;

import java.awt.LayoutManager;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import it.unibo.goldhunt.view.api.TopBar;
import it.unibo.goldhunt.view.api.UIFactory;

public class SwingUIFactory implements UIFactory {

    @Override
    public JFrame createFrame(final String title) {
        return new JFrame(title);
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
    public TopBar createTopBar(final String title) {
        throw new UnsupportedOperationException("Unimplemented method 'loadIcon'");
    }

    @Override
    public Icon loadIcon(final String iconName) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'loadIcon'");
    }

}
