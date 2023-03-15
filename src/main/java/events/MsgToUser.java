package events;
import javax.swing.JOptionPane;
public class MsgToUser {

    // MessageBox to inform the user -- used both by BackEnd and the FrontEnd
    public static void infoBox(String infoMessage, String titleBar) {
        JOptionPane.showMessageDialog(null, infoMessage, "InfoBox: " + titleBar, JOptionPane.INFORMATION_MESSAGE);
    }
}
