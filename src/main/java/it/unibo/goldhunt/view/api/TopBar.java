package it.unibo.goldhunt.view.api;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public final class TopBar {

    private final JPanel root;
    private final JButton backButton;
    private final JLabel title;

    public TopBar(final JPanel root, final JButton backButton, JLabel title) {
        this.root = root;
        this.backButton = backButton;
        this.title = title;
    }

    public JPanel getRoot() {
        return this.root;
    }

    
}
