package GUI;

import javax.swing.*;

public class DeleteWindow {
    public JButton deleteButton;
    public JButton closeButton;
    public JPanel deleteWindow;
    public JLabel msgDeleteWindow;
    private JLabel rightLabel;
    private JLabel leftLabel;

    private void createUIComponents() {
        // TODO: place custom component creation code here
        rightLabel=new JLabel(new ImageIcon("src/media/dlImage.jpg"));
        leftLabel =new JLabel(new ImageIcon("src/media/dlImage.jpg"));
    }
}
