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

    JFrame createFrame();

    JPanel createPanel(LayoutManager manager);

    JLabel createLabel(String text);

    JButton createButton(String text);

    TopBar createTopBar(String title);

    Icon loadIcon(String iconName);

}
