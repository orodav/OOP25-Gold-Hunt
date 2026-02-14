package it.unibo.goldhunt.view.swing.main;

import java.awt.CardLayout;
import java.util.Objects;

import javax.swing.JPanel;

import it.unibo.goldhunt.view.api.ItemVisualRegistry;
import it.unibo.goldhunt.view.api.UIFactory;
import it.unibo.goldhunt.view.swing.screens.DifficultyPanel;
import it.unibo.goldhunt.view.swing.screens.EndPanel;
import it.unibo.goldhunt.view.swing.screens.MenuPanel;
import it.unibo.goldhunt.view.swing.screens.PlayingPanel;
import it.unibo.goldhunt.view.swing.screens.ShopPanel;
import it.unibo.goldhunt.view.viewstate.ScreenType;

/**
 * Root Swing container that hosts all screens using a {@link CardLayout}.
 *
 * <p>
 * This class represents the main UI container of the application.
 * It registers screens and can show a specific screen,
 * but it does not contain wiring, business logic, or state transitions.
 */
public final class MainFrame {

}
