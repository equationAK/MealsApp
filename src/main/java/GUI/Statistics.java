package GUI;

import events.ExportToPdf;
import events.Interaction;
import events.MsgToUser;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;

public class Statistics {
    public JPanel statistics;
    public JTable table1;
    public JButton closeButton;
    private JButton exportToPdfButton;
    private JLabel rightImageLabel;
    private JLabel leftImageLabel;

    final Interaction interaction=Interaction.getInteractionInstance();

    public Statistics() {
    exportToPdfButton.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            if (interaction.mostViewedMeals().isEmpty()) {
                MsgToUser.infoBox("Your Favorites list is empty", "WARNING");
                super.mouseClicked(e);
            } else {
                try {
                    ExportToPdf.exportToPdf();
                    JFrame succ = new JFrame("Succeed");
                    ExportedMessage message = new ExportedMessage();
                    succ.setContentPane(message.msgok);
                    succ.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                    succ.pack();
                    // center the jframe on screen
                    succ.setLocationRelativeTo(null);
                    succ.setVisible(true);
                    message.OKButton.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            succ.setVisible(false);
                        }
                    });
                    System.out.println("PDF Exported");
                } catch (FileNotFoundException ex) {
                    System.out.println("Something is wrong");
                    throw new RuntimeException(ex);
                }
            }
        }
    });
}

    private void createUIComponents() {
        leftImageLabel=new JLabel(new ImageIcon("src/media/StatisticsLeft.jpg"));
        rightImageLabel=new JLabel(new ImageIcon("src/media/StatisticsRight.jpg"));
    }
}
