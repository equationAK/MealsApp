package GUI;

import events.Interaction;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class exitWindow {
    private JButton exitButton;
    public JButton cancelButton;
    public JPanel exitWindowPanel;

    public exitWindow() {
    exitButton.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            // the following 2 lines drop all tables of the DB
            //Interaction interaction = Interaction.getInteractionInstance();
            //interaction.dropTables();
            System.exit(0);
        }
    });
    }
}
