package it.unibo.goldhunt.view.swing.screens;

import java.awt.BorderLayout;
import java.awt.CardLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import it.unibo.goldhunt.view.api.GameController;
import it.unibo.goldhunt.view.api.UIFactory;

public class DifficultyPanel extends JPanel {

    public DifficultyPanel(GameController controller, UIFactory factory, CardLayout layout, JPanel mainPanel) {

        setLayout(new BorderLayout());

        final var center = factory.createPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));

        final var title = factory.createStandardLabel("SELECT DIFFICULTY");
        title.setAlignmentX(CENTER_ALIGNMENT);
        title.setHorizontalAlignment(SwingConstants.CENTER);

        final var easy = factory.createButton("EASY");
        final var medium = factory.createButton("MEDIUM");
        final var hard = factory.createButton("HARD");
        final var backToMenu = factory.createButton("MENU");

        easy.setAlignmentX(CENTER_ALIGNMENT);
        medium.setAlignmentX(CENTER_ALIGNMENT);
        hard.setAlignmentX(CENTER_ALIGNMENT);
        backToMenu.setAlignmentX(CENTER_ALIGNMENT);

        easy.addActionListener(e -> {
            //set difficulty
            layout.show(mainPanel, "PLAYING");
        });

        medium.addActionListener(e -> {
            //set difficulty
            layout.show(mainPanel, "PLAYING");
        });

        hard.addActionListener(e -> {
            //set difficulty
            layout.show(mainPanel, "PLAYING");
        });

        backToMenu.addActionListener(e -> {
            layout.show(mainPanel, "MENU");
        });
        
        center.add(Box.createVerticalGlue());
        center.add(title);
        center.add(Box.createVerticalStrut(30));
        center.add(easy);
        center.add(Box.createVerticalStrut(15));
        center.add(medium);
        center.add(Box.createVerticalStrut(15));
        center.add(hard);
        center.add(Box.createVerticalStrut(30));
        center.add(backToMenu);
        center.add(Box.createVerticalGlue());

        add(center, BorderLayout.CENTER);
    }
}
