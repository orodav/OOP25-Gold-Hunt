package it.unibo.goldhunt.view.api;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.LayoutManager;

/**
 * This interface models a factory for the creation
 * of the UI'S graphical components, to create them
 * with a consistent look & feel across the application.
 */
public interface UIFactory {

    /**
     * Creates a {@link JFrame} with its title.
     * 
     * @param title the frame's title
     * @return the created {@link JFrame}
     */
    JFrame createFrame(String title);

    /**
     * Creates a {@link JPanel} with no {@link LayoutManager}.
     * 
     * @return the created {@link JPanel}
     */
    JPanel createPanel();

    /**
     * Creates a {@link JPanel} with an associated {@link LayoutManager}.
     * 
     * @param manager the layout manager
     * @return the created {@link JPanel}
     */
    JPanel createPanel(LayoutManager manager);

    /**
     * Creates a standard {@link JLabel}.
     * 
     * @param text the label text
     * @return the created {@link JLabel}
     */
    JLabel createStandardLabel(String text);

    /**
     * Creates a {@link JLabel} for secondary and smaller labels.
     * 
     * @param text the label text
     * @return the created {@link JLabel}
     */
    JLabel createSecondaryLabel(String text);

    /**
     * Creates a {@link JLabel} for bigger labels or titles.
     * 
     * @param text the label text
     * @return the created {@link JLabel}
     */
    JLabel createTitleLabel(String text);

    /**
     * Creates a {@link JButton} with text.
     * 
     * @param text the button's text
     * @return the created {@link JButton}
     */
    JButton createButton(String text);

    /**
     * Creates a {@link JButton} with an icon.
     * 
     * @param icon the image icon
     * @return the created {@link JButton}
     */
    JButton createIconButton(String icon);

    /**
     * Loads an icon resource.
     * 
     * @param iconName the icon identifier
     * @return the loaded {@link Icon}
     * @throws NullPointerException if {@code iconName} is {@code null}
     * @throws IllegalArgumentException if there is icon corresponding to {@code iconName}
     */
    Icon loadIcon(String iconName);

}
