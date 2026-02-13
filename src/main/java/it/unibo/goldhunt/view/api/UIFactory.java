package it.unibo.goldhunt.view.api;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.LayoutManager;

/**
 * This interface models a factory of the graphical interface's components.
 * It permits the creation of graphic components
 * with the same aesthetic choices.
 */
public interface UIFactory {

    JFrame createFrame(String title);

    JPanel createPanel();

    JPanel createPanel(LayoutManager manager);

    JLabel createStandardLabel(String text);

    JLabel createSecondaryLabel(String text);

    JLabel createTitleLabel(String text);

    JButton createButton(String text);

    JButton createIconButton(String icon);

    Icon loadIcon(String iconName);

}
