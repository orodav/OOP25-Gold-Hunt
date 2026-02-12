package it.unibo.goldhunt.view.api;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public record TopBar(JPanel root, JButton backButton, JLabel title) {}
