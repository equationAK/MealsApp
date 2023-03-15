package GUI;

import javax.swing.*;

public class infoFrame {
    public JPanel info;
    public JButton closeButton;
    private JLabel image;

    private void createUIComponents() {
        // TODO: place custom component creation code here
        image=new JLabel(new ImageIcon("src/media/java.jpg"));
    }
}
