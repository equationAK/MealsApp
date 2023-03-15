package GUI;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import events.Interaction;

public class SearchbyCategory {
    public JPanel searchbycatpanel;
    private JTextArea textArea1;
    public JButton closeButton;
    public JList categorylist;
    private JLabel image;
    private String path="src/media/seafood.jpg";

    public URL url;
    public SearchbyCategory() {

        Interaction interaction = Interaction.getInteractionInstance();
        categorylist.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                List<String> categoryContents = interaction.categoriesContents(categorylist.getSelectedValue().toString());
                //categoryContents.forEach(j-> System.out.println(j)); // -- for debug
                String c = interaction.categoriesContentsByLine(categoryContents);
                //System.out.println(c); // -- for debug
                //Every time the list selection changes the text of the Results changes.
                textArea1.setText(c);
                //Every category has an image that you can preview
                String temp = interaction.categoryImageURL(categorylist.getSelectedValue().toString());
                try {
                    url=new URL(temp);
                } catch (MalformedURLException ex) {
                    throw new RuntimeException(ex);
                }
                try {
                    image.setIcon(new ImageIcon(new URL(interaction.categoryImageURL(
                            categorylist.getSelectedValue().toString()))));
                }
                catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        image=new JLabel(new ImageIcon(path));
    }
}
