package GUI;

import events.Interaction;
import events.MsgToUser;
import objects.MealCounted;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.Vector;

public class Favorites {
    public JList favoritesList;
    private JButton showButton;
    private JTextField nameTextField;
    private JTextField categoryTextField;
    private JTextArea recipeTextArea;
    private JLabel youtubebutton;
    private JButton saveButton;
    private JButton editButton;
    public JButton closeButton;
    private JLabel centerImage;
    private JLabel mealImage;
    public JPanel favoritesPanel;
    private JLabel leftImage;
    private JTextField areaTextField;
    private JButton deleteAllButton;
    private JButton deleteButton;
    private String url;
    private String imagePath;
    private int meal_id;
    private boolean isAMealActive=false;
    private boolean editActive=false;

    private Vector<String> inCaseOfDelete = new Vector<>();


    public Favorites() {

        Interaction interaction = Interaction.getInteractionInstance();

        youtubebutton.addMouseListener(new MouseAdapter() {
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

        showButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                if(favoritesList.getSelectedValue() == null)
                    MsgToUser.infoBox("Select a Meal First","Warning");
                else if (favoritesList.getSelectedValue().equals("You don't have meals in your favorites yet"))
                    MsgToUser.infoBox("Your Favorites are empty!","Warning");
                else {
                    MealCounted meal = interaction.selectMealForFav((String) favoritesList.getSelectedValue());
                    //System.out.println(favoritesList.getSelectedValue()); // -- for debug
                    meal_id = meal.getMeal_Id();
                    nameTextField.setText(meal.getMeal_Name());
                    categoryTextField.setText(meal.getMeal_Category());
                    areaTextField.setText(meal.getMeal_Area());
                    recipeTextArea.setText(meal.getMeal_Instructions());
                    interaction.setCounterPlus1(meal_id);
                    isAMealActive=true;
                    url=meal.getMeal_Youtube_Url();

                    /*
                            -- Have to make the image size fixing
                    try {
                        mealImage.setIcon(new ImageIcon(new URL(meal.getMeal_Photo_Url())));
                    } catch (MalformedURLException ex) {
                        throw new RuntimeException(ex);
                    }
                    */

                }

            }
        });
        editButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(!isAMealActive){
                    MsgToUser.infoBox("Select a Meal from the list and click Show button","Select Something First");
                    super.mouseClicked(e);
                }
                nameTextField.setEditable(true);
                categoryTextField.setEditable(true);
                areaTextField.setEditable(true);
                recipeTextArea.setEditable(true);
                editActive=true;

            }
        });


        saveButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(editActive)
                {
                    nameTextField.setEditable(false);
                    categoryTextField.setEditable(false);
                    areaTextField.setEditable(false);
                    recipeTextArea.setEditable(false);
                    favoritesList.setListData(new Vector<>(interaction.selectAllReturnMealName()));
                    //Function to save to db
                    interaction.updateMeal(meal_id, nameTextField.getText(), categoryTextField.getText(), areaTextField.getText(), recipeTextArea.getText());
                    favoritesList.setListData(new Vector<>(interaction.selectAllReturnMealName()));
                }
            }
        });
        deleteButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!isAMealActive){
                    MsgToUser.infoBox("You have to select a Meal first","WARNING");
                }
                else if (favoritesList.getSelectedValue().equals("You don't have meals in your favorites yet")){
                    MsgToUser.infoBox("You don't have a meal in your favorites","WARNING");
                }
                else{
                    JFrame dlw= new JFrame("!! DELETE !!");
                    DeleteWindow delWindow=new DeleteWindow();
                    delWindow.msgDeleteWindow.setText("Are you sure you want to Delete the selected Meal");
                    dlw.setContentPane(delWindow.deleteWindow);
                    dlw.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    dlw.pack();
                    dlw.setLocationRelativeTo(null);
                    dlw.setVisible(true);

                    delWindow.deleteButton.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            interaction.deleteFromFav(meal_id);
                            //Data from BackEnd
                            List<String> returnMealName= interaction.selectAllReturnMealName();
                            //if the list is empty then inform the user ...
                            if (returnMealName.isEmpty()){
                                returnMealName.add("You don't have meals in your favorites yet");
                            }
                            Vector<String> vector= new Vector<String>();

                            for(String i : returnMealName){
                                vector.add(i);
                            }

                            //List activation and Window

                            favoritesList.setListData(vector);
                            clearWindow();
                            dlw.setVisible(false);
                        }
                    });

                    delWindow.closeButton.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            dlw.setVisible(false);
                        }
                    });
                }

            }
        });
        deleteAllButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JFrame dlw= new JFrame("!! DELETE !!");
                DeleteWindow delWindow=new DeleteWindow();
                delWindow.msgDeleteWindow.setText("Are you sure you want to Delete all meals");
                dlw.setContentPane(delWindow.deleteWindow);
                dlw.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                dlw.pack();
                dlw.setLocationRelativeTo(null);
                dlw.setVisible(true);

                delWindow.deleteButton.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        interaction.deleteFromFav();
                        inCaseOfDelete.add("You don't have meals in your favorites yet");
                        favoritesList.setListData(inCaseOfDelete);
                        clearWindow();
                        dlw.setVisible(false);

                    }
                });

                delWindow.closeButton.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        dlw.setVisible(false);
                    }
                });
            }
        });
    }

    private void createUIComponents() {
        centerImage=new JLabel(new ImageIcon("src/media/favoritesPanelCenterImage.jpg"));
        youtubebutton= new JLabel(new ImageIcon("src/media/youtube.png"));
        mealImage=new JLabel(new ImageIcon("src/media/rightStripes.jpg"));
        leftImage=new JLabel(new ImageIcon("src/media/leftStripes.jpg"));
    }

    public void clearWindow(){
        nameTextField.setText("");
        categoryTextField.setText("");
        areaTextField.setText("");
        recipeTextArea.setText("");
        isAMealActive=false;
        url=null;
    }
}
