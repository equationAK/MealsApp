package GUI;

import events.Interaction;
import objects.Meal;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

public class ResultsByName {
    public JPanel resultsbyname;
    public JList resultsbynamelist;
    private JButton saveButton;
    public JButton closeButton;
    private JTextField categoryField;
    private JTextArea RecipetextArea;
    public JTextField nameField;
    private JLabel video;
    private JLabel rightImage;
    private JLabel topLabelforinfo;
    private JTextField areaTextField;
    private int meal_id;
    private String url;
    private Meal selectedMeal;
    private boolean isAMealActive=false;


    public ResultsByName() {

        Interaction interaction = Interaction.getInteractionInstance();

        saveButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                interaction.insertMealInDB(selectedMeal);
            }
        });


        // Action Listener for each of the Meal Returned from the search
        resultsbynamelist.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {

                topLabelforinfo.setText("Select a meal from the list");
                selectedMeal = interaction.mealByUsersChoice(resultsbynamelist.getSelectedValue().toString());
                meal_id = selectedMeal.getMeal_Id();
                nameField.setText(selectedMeal.getMeal_Name());
                categoryField.setText(selectedMeal.getMeal_Category());
                RecipetextArea.setText(selectedMeal.getMeal_Instructions());
                areaTextField.setText(selectedMeal.getMeal_Area());
                url= selectedMeal.getMeal_Youtube_Url();
                isAMealActive=true;

                //If we want to change the image photo we activate the following code.

                /*
                try {
                    rightImage.setIcon(new ImageIcon(new URL(meal.getMeal_Photo_Url())));
                } catch (MalformedURLException ex) {
                throw new RuntimeException(ex);
                }
                */
            }
        });

        //Function to create a Hyperlink to YouTube

        video.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                if(isAMealActive && interaction.isValidUrl(url)){
                    try {
                        URL youTubeUrl=new URL(url);
                        Desktop.getDesktop().browse(youTubeUrl.toURI());
                    } catch (IOException | URISyntaxException ex) {
                        throw new RuntimeException(ex);
                    }
                }

            }
        });
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        video=new JLabel(new ImageIcon("src/media/youtube.png"));
        rightImage= new JLabel(new ImageIcon("src/media/resultsByNameRightImage.jpg"));
    }
}
